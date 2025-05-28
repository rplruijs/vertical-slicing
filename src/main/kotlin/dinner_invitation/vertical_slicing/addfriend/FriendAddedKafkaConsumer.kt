package dinner_invitation.vertical_slicing.addfriend


import dinner_invitation.vertical_slicing.domain.commands.addfriend.AddFriendCommand
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class FriendAddedKafkaConsumer(val commandGateway: CommandGateway) {
    @KafkaListener(topics = ["friend-added"], groupId = "friends-group")
    fun handle(event: ExternalFriendAddedEvent) {
        commandGateway.send<AddFriendCommand>(
            AddFriendCommand(
                aggregateId = event.friendId,
                email = event.email,
                firstName = event.firstName,
                lastName = event.lastName,
                phoneNumber = event.phoneNumber,
            ))
    }
}