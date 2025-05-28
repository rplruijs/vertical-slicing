package dinner_invitation.vertical_slicing.events

import dinner_invitation.vertical_slicing.common.Event
import java.time.LocalDateTime

data class DinnerProposalStartedEvent (
    val dinnerProposalID: String,
    val dateTime: LocalDateTime,
    val friends: List<String>) : Event