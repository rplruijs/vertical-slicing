package dinner_invitation.vertical_slicing.addfriend.integration

import dinner_invitation.vertical_slicing.addfriend.ExternalFriendAddedEvent
import dinner_invitation.vertical_slicing.common.support.BaseIntegrationTest
import dinner_invitation.vertical_slicing.common.support.StreamAssertions
import dinner_invitation.vertical_slicing.common.support.awaitUntilAssserted
import dinner_invitation.vertical_slicing.events.FriendAddedEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.junit.jupiter.api.Test

class RelationCreatedProcessorTest : BaseIntegrationTest() {
    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, ExternalFriendAddedEvent>

    @Autowired
    private lateinit var streamAssertions: StreamAssertions


    @Test
    fun `relation created processor test`() {
        val friendId = "1234567890"
        val email = "erik@gmail.com"
        val firstName = "Erik"
        val lastName = "Mertz"
        val phoneNumber = "06-247242432"

        awaitUntilAssserted {
            kafkaTemplate.executeInTransaction {
                it
                    .send("friend-added", ExternalFriendAddedEvent(friendId, email,firstName, lastName, phoneNumber))
                    .get()
            }

            streamAssertions.assertEvent(friendId) { it is FriendAddedEvent }
        }
    }
}