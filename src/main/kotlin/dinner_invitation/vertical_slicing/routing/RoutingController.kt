package dinner_invitation.vertical_slicing.routing

import dinner_invitation.vertical_slicing.friends.AllFriendsQuery
import dinner_invitation.vertical_slicing.friends.FriendsReadModel
import org.axonframework.queryhandling.QueryGateway
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class RoutingController(val queryGateway: QueryGateway) {

    @GetMapping("/")
    fun landingPage(model: Model): String {
        return "index"
    }

    @GetMapping("/plan-dinner")
    fun planDinnerPage(model: Model): String {
        val friendsReadModel = queryGateway.query(AllFriendsQuery(), FriendsReadModel::class.java ).get()
        model.addAttribute("friendsReadModel", friendsReadModel)
        return "plan-dinner"
    }
}