package dinner_invitation.vertical_slicing.plandinner

import dinner_invitation.vertical_slicing.common.HtmlSnippet
import dinner_invitation.vertical_slicing.friends.AllFriendsQuery
import dinner_invitation.vertical_slicing.friends.FriendReadModelEntity
import dinner_invitation.vertical_slicing.friends.FriendsReadModel
import dinner_invitation.vertical_slicing.friends.GetFriendQuery
import jakarta.servlet.http.HttpServletRequest
import jakarta.websocket.server.PathParam
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerSentEvent
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import reactor.core.publisher.Flux

@Controller
@RequestMapping("/plan-dinner")
class PlanDinnerHtmxController(
    private val queryGateway: QueryGateway,
    private val engine: TemplateEngine
) {

    @GetMapping("/friends", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun friends() : Flux<ServerSentEvent<HtmlSnippet>> {
        val query = queryGateway.subscriptionQuery(
            AllFriendsQuery(),
            ResponseTypes.instanceOf(FriendsReadModel::class.java),
            ResponseTypes.instanceOf(FriendsReadModel::class.java)
        )

        return query.initialResult()
            .concatWith(query.updates())
            .map {
                ServerSentEvent.builder<String>()
                    .event("friends")
                    .data(availableFriendsComponent(it))
                    .build()
            }
    }

    @PostMapping("/select-friend/{friendId}")
    @ResponseBody
    fun selectFriend(@PathVariable friendId: String) : HtmlSnippet {
        val friend =
            queryGateway.query(GetFriendQuery(friendId), FriendReadModelEntity::class.java)
                .get()

        return selectedFriendsComponent(friend)
    }

    @PostMapping("/plan-dinner/submit")
    fun planDinner(
        @RequestParam dinnerName: String,
        @RequestParam dinnerDate: String,
        @RequestParam dinnerTime: String,
        @RequestParam(required = false) location: String?,
        @RequestParam(required = false) notes: String?,
        @RequestParam selectedFriendIds: List<String>,
        redirectAttributes: RedirectAttributes
    ): String {

        return "TODO"
    }



    private fun availableFriendsComponent(friendsReadModel: FriendsReadModel): HtmlSnippet {
        val context = Context()
        context.setVariable("friendsReadModel", friendsReadModel )
        return engine.process("plandinner/available-friends", context).replace(Regex("[\\r\\n]"), "")
    }

    private fun selectedFriendsComponent(friend : FriendReadModelEntity): HtmlSnippet {
        val context = Context()
        context.setVariable("friend", friend)
        return engine.process("plandinner/selected-friend", context)
    }
}
