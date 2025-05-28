package dinner_invitation.vertical_slicing.events

import dinner_invitation.vertical_slicing.common.Event

data class FriendUpdatedEvent(val friendId: String,
                              val email: String,
                              val firstName: String,
                              val lastName: String,
                              val phoneNumber : String) : Event