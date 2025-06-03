package dinner_invitation.vertical_slicing.addfriend

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AddFriendKafkaDebugResource(
    private var kafkaTemplate: KafkaTemplate<String, in ExternalFriendAddedEvent>,
) {

    @PostMapping("/fake/external/add-friend")
    fun addFriend(
        @RequestParam friendId: String,
        @RequestParam email: String,
        @RequestParam firstName: String,
        @RequestParam lastName: String,
        @RequestParam phoneNumber: String
    ) {
        kafkaTemplate.executeInTransaction {
            it
                .send("friend-added", ExternalFriendAddedEvent(friendId, email,firstName, lastName, phoneNumber))
                .get()
        }
    }
}
