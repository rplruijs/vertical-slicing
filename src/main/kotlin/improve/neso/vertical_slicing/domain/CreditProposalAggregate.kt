package improve.neso.vertical_slicing.domain

import improve.neso.vertical_slicing.domain.commands.startcreditproposal.StartCreditProposalCommand
import improve.neso.vertical_slicing.events.CreditProposalStartedEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateCreationPolicy
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.modelling.command.CreationPolicy
import org.axonframework.spring.stereotype.Aggregate
import java.util.*

@Aggregate
class CreditProposalAggregate {

    @AggregateIdentifier var aggregateId: UUID? = null

    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    @CommandHandler
    fun handle(command: StartCreditProposalCommand) {
        AggregateLifecycle.apply(CreditProposalStartedEvent(
            aggregateId = command.aggregateId,
            relationNumber = command.relationNumber,
            organisationName = command.organisationName,
            template = command.template)
        )
    }

    @EventSourcingHandler
    fun on(event: CreditProposalStartedEvent) {
        aggregateId = event.aggregateId
    }
}