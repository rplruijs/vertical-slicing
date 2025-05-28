package dinner_invitation.vertical_slicing.addfriend

data class ExternalFriendAddedEvent (
    val friendId: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String
)
