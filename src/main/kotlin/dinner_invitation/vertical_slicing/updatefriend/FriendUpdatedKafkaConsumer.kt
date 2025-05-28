package dinner_invitation.vertical_slicing.updatefriend

import dinner_invitation.vertical_slicing.domain.commands.updatefriend.UpdateFriendCommand
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class FriendUpdatedKafkaConsumer(val commandGateway: CommandGateway) {
    @KafkaListener(topics = ["friend-updated"], groupId = "friend-group")
    fun handle(event: ExternalFriendUpdatedEvent) {
        commandGateway.send<UpdateFriendCommand>(
            UpdateFriendCommand(
                aggregateId = event.friendId,
                email = event.email,
                firstName = event.firstName,
                lastName = event.lastName,
                phoneNumber = event.phoneNumber
            )
        )
    }
}