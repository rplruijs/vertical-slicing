package dinner_invitation.vertical_slicing.friends

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

class AllFriendsQuery()
data class GetFriendQuery(val friendId: String)


@Entity
@Table(name = "friends")
class FriendReadModelEntity {

    @Column(name = "friendId")
    @Id
    lateinit var friendId: String

    @Column(name = "email")
    lateinit var email: String

    @Column(name = "firstName")
    lateinit var firstName: String

    @Column(name = "lastName")
    lateinit var lastName: String

    @Column(name = "phoneNumber")
    lateinit var phoneNumber: String
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FriendReadModelEntity

        if (friendId != other.friendId) return false
        if (email != other.email) return false
        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (phoneNumber != other.phoneNumber) return false

        return true
    }

    override fun hashCode(): Int {
        var result = friendId.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + phoneNumber.hashCode()
        return result
    }


}
data class FriendsReadModel(val data: List<FriendReadModelEntity>)

