package dinner_invitation.vertical_slicing.domain

import dinner_invitation.vertical_slicing.domain.commands.startcreditproposal.StartDinnerProposalCommand
import dinner_invitation.vertical_slicing.events.DinnerProposalStartedEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateCreationPolicy
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.modelling.command.CreationPolicy
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class DinnerInvitationAggregate() {

    @AggregateIdentifier var aggregateId: String? = null

    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    @CommandHandler
    fun handle(command: StartDinnerProposalCommand) {
        AggregateLifecycle.apply(
            DinnerProposalStartedEvent(
                dinnerProposalID = command.aggregateId,
                dateTime = command.dateTime,
                friends = command.friends
            )
        )
    }

    @EventSourcingHandler
    fun on(event: DinnerProposalStartedEvent) {
        aggregateId = event.dinnerProposalID
    }
}