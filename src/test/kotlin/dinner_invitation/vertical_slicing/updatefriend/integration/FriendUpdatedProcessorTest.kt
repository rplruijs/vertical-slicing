package dinner_invitation.vertical_slicing.updatefriend.integration

import dinner_invitation.vertical_slicing.common.support.BaseIntegrationTest
import dinner_invitation.vertical_slicing.common.support.StreamAssertions
import dinner_invitation.vertical_slicing.common.support.awaitUntilAssserted
import dinner_invitation.vertical_slicing.domain.commands.addfriend.AddFriendCommand
import dinner_invitation.vertical_slicing.events.FriendUpdatedEvent
import dinner_invitation.vertical_slicing.updatefriend.ExternalFriendUpdatedEvent
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.junit.jupiter.api.Test


class FriendUpdatedProcessorTest : BaseIntegrationTest() {
    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, ExternalFriendUpdatedEvent>

    @Autowired
    private lateinit var commandGateway: CommandGateway

    @Autowired
    private lateinit var streamAssertions: StreamAssertions


    @Test
    fun `friend updated successfully`() {
        val friendId = "1234567890"
        val email = "erik@gmail.com"
        val firstName = "Erik"
        val lastName = "Mertz"
        val phoneNumber = "06-247242432"
        val command = AddFriendCommand(friendId, email, firstName, lastName, phoneNumber)
        commandGateway.sendAndWait<AddFriendCommand>(command)

        val updatedPhoneNumber = "06-56675765"
        awaitUntilAssserted {
            val result = kafkaTemplate.executeInTransaction {
                it
                    .send("relation-updated", ExternalFriendUpdatedEvent(friendId, email, firstName, lastName, updatedPhoneNumber))
                    .get()
            }

            streamAssertions.assertEvent(friendId) { it is FriendUpdatedEvent }
        }
    }


    @Test
    fun `friend not updated because nothing changed`() {
        val friendId = "1234567890"
        val email = "erik@gmail.com"
        val firstName = "Erik"
        val lastName = "Mertz"
        val phoneNumber = "06-247242432"
        val command = AddFriendCommand(friendId, email, firstName, lastName, phoneNumber)

        commandGateway.sendAndWait<AddFriendCommand>(command)

        awaitUntilAssserted {
            val result = kafkaTemplate.executeInTransaction {
                it
                    .send("relation-updated", ExternalFriendUpdatedEvent(friendId, email, firstName, lastName, phoneNumber))
                    .get()
            }

            streamAssertions.assertNoEvent(friendId) { it is FriendUpdatedEvent }
        }
    }
}
