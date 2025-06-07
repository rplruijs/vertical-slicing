package dinner_invitation.vertical_slicing.friends.internal

import dinner_invitation.vertical_slicing.events.FriendAddedEvent
import dinner_invitation.vertical_slicing.friends.AllFriendsQuery
import dinner_invitation.vertical_slicing.friends.FriendReadModelEntity
import dinner_invitation.vertical_slicing.friends.FriendsReadModel
import dinner_invitation.vertical_slicing.friends.GetFriendQuery
import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.axonframework.queryhandling.QueryUpdateEmitter
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentLinkedDeque

@Component
@ProcessingGroup("relations")
class FriendReadModelQueryHandler(val repository: FriendReadModelRepository,
                                        val queryUpdateEmitter: QueryUpdateEmitter) {

    val limitedDeque = ConcurrentLinkedDeque<FriendReadModelEntity>()

    @QueryHandler
    fun handle(query: AllFriendsQuery) : FriendsReadModel {
        val relations = repository.findAll()
        val set = relations.toMutableSet()
        set.addAll(limitedDeque)
        return FriendsReadModel(set.toList())
    }


    @QueryHandler
    fun handle(query: GetFriendQuery) : FriendReadModelEntity {
        return repository.findByFriendId(query.friendId)
    }

    @EventHandler
    fun on(event: FriendAddedEvent) {
        while (limitedDeque.size > 20) {
            limitedDeque.pollFirst()
        }
        val item =
            FriendReadModelEntity().apply {
                this.friendId = event.friendId
                this.email = event.email
                this.firstName = event.firstName
                this.lastName = event.lastName
                this.phoneNumber = event.phoneNumber
            }
        if (!limitedDeque.contains(item)) {
            limitedDeque.push(item)
        }

        emit(item)
    }

    private fun emit(item: FriendReadModelEntity) {
        val relations = repository.findAll()
        val relationSet = relations.toMutableSet()
        relationSet.add(item)
        val updatedModel = FriendsReadModel(relationSet.toList())

        queryUpdateEmitter.emit(AllFriendsQuery::class.java,
            {query: AllFriendsQuery -> true},
            updatedModel
        )
    }
}
