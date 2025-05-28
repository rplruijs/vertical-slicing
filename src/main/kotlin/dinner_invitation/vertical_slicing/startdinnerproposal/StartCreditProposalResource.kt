package dinner_invitation.vertical_slicing.startdinnerproposal

import dinner_invitation.vertical_slicing.domain.commands.startcreditproposal.StartDinnerProposalCommand
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.util.*

data class StartDinnerProposalPayload(
    val relationNumber: String,
    val dateTime: LocalDateTime,
    val friends : List<String>
)

@RestController
class StartCreditProposalResource(private var commandGateway: CommandGateway) {

    @PostMapping("/start-credit-proposal")
    fun processCommand(@RequestBody payload: StartDinnerProposalPayload) {

        commandGateway.send<StartDinnerProposalCommand>(
            StartDinnerProposalCommand(
                UUID.randomUUID().toString(),
                payload.friends,
                payload.dateTime,
                )
        )
    }
}