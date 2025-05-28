package dinner_invitation.vertical_slicing.domain.commands.startcreditproposal

import dinner_invitation.vertical_slicing.common.Command
import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.time.LocalDateTime

data class StartDinnerProposalCommand(
    @TargetAggregateIdentifier override var aggregateId: String,
    val friends:List<String>,
    val dateTime: LocalDateTime,
): Command