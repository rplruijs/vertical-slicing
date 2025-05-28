package dinner_invitation.vertical_slicing.updatefriend

import dinner_invitation.vertical_slicing.common.Event

data class ExternalFriendUpdatedEvent (val friendId: String,
                                       val email: String,
                                       val firstName: String,
                                       val lastName: String,
                                       val phoneNumber: String) : Event