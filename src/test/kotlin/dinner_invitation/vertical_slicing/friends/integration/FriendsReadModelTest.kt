package dinner_invitation.vertical_slicing.friends.integration

import dinner_invitation.vertical_slicing.common.support.BaseIntegrationTest
import dinner_invitation.vertical_slicing.domain.commands.addfriend.AddFriendCommand
import dinner_invitation.vertical_slicing.friends.AllFriendsQuery
import dinner_invitation.vertical_slicing.friends.FriendsReadModel
import org.assertj.core.api.Assertions.assertThat
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.queryhandling.QueryGateway
import org.springframework.beans.factory.annotation.Autowired
import org.junit.jupiter.api.Test
import java.util.UUID

class FriendsReadModelTest : BaseIntegrationTest() {
    @Autowired private lateinit var commandGateway: CommandGateway
    @Autowired private lateinit var queryGateway: QueryGateway

    @Test
    fun `relations read model test`() {
        val command = AddFriendCommand(UUID.randomUUID().toString(), "remcoruijsenaars@gmail.com", "Remco", "Ruijsenaars", "")
        commandGateway.sendAndWait<Any>(command)

        val readModel =
            queryGateway.query(AllFriendsQuery(), FriendsReadModel::class.java )

        assertThat(readModel.get()).isNotNull
        assertThat(readModel.get().data).hasSize(1)
        assertThat(readModel.get().data).first().matches {
            it.friendId == command.aggregateId
        }
    }
}