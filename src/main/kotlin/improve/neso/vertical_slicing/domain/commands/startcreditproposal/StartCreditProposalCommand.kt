package improve.neso.vertical_slicing.domain.commands.startcreditproposal

import de.eventsourcingbook.cart.common.Command
import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

data class StartCreditProposalCommand(
    @TargetAggregateIdentifier override var aggregateId: UUID,
    val relationNumber:String,
    val organisationName:String,
    val template:String
): Command