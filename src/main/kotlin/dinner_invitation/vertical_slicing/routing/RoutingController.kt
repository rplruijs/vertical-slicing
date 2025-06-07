package dinner_invitation.vertical_slicing.routing

import dinner_invitation.vertical_slicing.friends.AllFriendsQuery
import dinner_invitation.vertical_slicing.friends.FriendsReadModel
import org.axonframework.queryhandling.QueryGateway
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class RoutingController(val queryGateway: QueryGateway) {

    @GetMapping("/")
    fun landingPage(model: Model): String {
        return "dashboard/index"
    }

    @GetMapping("/mastering-htmx")
    fun masteringHTMX(model: Model): String {
        return "mastering-htmx"
    }

    @GetMapping("/plan-dinner")
    fun planDinnerPage(model: Model): String {
        val friendsReadModel = queryGateway.query(AllFriendsQuery(), FriendsReadModel::class.java ).get()
        model.addAttribute("friendsReadModel", friendsReadModel)
        return "plandinner/plan-dinner"
    }
}