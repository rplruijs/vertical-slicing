package dinner_invitation.vertical_slicing.friends.internal

import dinner_invitation.vertical_slicing.events.FriendAddedEvent
import dinner_invitation.vertical_slicing.friends.FriendReadModelEntity
import org.axonframework.eventhandling.EventHandler
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component

interface FriendReadModelRepository: JpaRepository<FriendReadModelEntity, String> {
    fun findByFriendId(friendId: String): List<FriendReadModelEntity>
}

@Component
class FriendReadModelProjector(var repository: FriendReadModelRepository) {
    @EventHandler
    fun on(event: FriendAddedEvent) {
        repository.save<FriendReadModelEntity>(
            FriendReadModelEntity().apply {
                friendId = event.friendId
                email = event.email
                firstName = event.firstName
                lastName = event.lastName
                phoneNumber = event.phoneNumber
            },
        )
    }
}