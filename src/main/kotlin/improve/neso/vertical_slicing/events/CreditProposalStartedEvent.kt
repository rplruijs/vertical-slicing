package improve.neso.vertical_slicing.events

import de.eventsourcingbook.cart.common.Event
import java.util.*

data class CreditProposalStartedEvent (
    val aggregateId: UUID,
    val relationNumber: String,
    val organisationName: String,
    val template:String
): Event