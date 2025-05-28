package dinner_invitation.vertical_slicing.domain

import dinner_invitation.vertical_slicing.domain.commands.addfriend.AddFriendCommand
import dinner_invitation.vertical_slicing.domain.commands.updatefriend.UpdateFriendCommand
import dinner_invitation.vertical_slicing.events.FriendAddedEvent
import dinner_invitation.vertical_slicing.events.FriendUpdatedEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateCreationPolicy
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.modelling.command.CreationPolicy
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class FriendAggregate() {

    private lateinit var friend: Friend
    @AggregateIdentifier var aggregateId: String? = null

    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    @CommandHandler
    fun handle(command: AddFriendCommand) {
        AggregateLifecycle.apply(
            FriendAddedEvent(
                friendId = command.aggregateId,
                email = command.email,
                firstName = command.firstName,
                lastName = command.lastName,
                phoneNumber = command.phoneNumber
            )
        )
    }

    @CommandHandler
    fun handle(command: UpdateFriendCommand) {
        val updatedFriend = Friend(command.email, command.firstName, command.lastName, command.phoneNumber)
        if(friend != updatedFriend) {
            AggregateLifecycle.apply(
                FriendUpdatedEvent(
                    friendId = command.aggregateId,
                    email = command.email,
                    firstName = command.firstName,
                    lastName = command.lastName,
                    phoneNumber = command.phoneNumber
                )
            )
        }
    }

    @EventSourcingHandler
    fun on(event: FriendAddedEvent) {
        aggregateId = event.friendId
        friend = Friend(event.friendId, event.firstName, event.lastName, event.phoneNumber)
    }

    @EventSourcingHandler
    fun on(event: FriendUpdatedEvent) {
        friend = Friend(event.friendId, event.firstName, event.lastName, event.phoneNumber)
    }
}