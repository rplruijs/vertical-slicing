package improve.neso.vertical_slicing.startcreditproposal

import improve.neso.vertical_slicing.domain.commands.startcreditproposal.StartCreditProposalCommand
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

data class StartCreditProposalPayload(
    val relationNumber: String,
    val template: String
)

@RestController
class StartCreditProposalResource(private var commandGateway: CommandGateway,
                                  private var organisationDetailsRetriever: OrganisationDetailRetriever) {

    @PostMapping("/start-credit-proposal")
    fun processCommand(@RequestBody payload: StartCreditProposalPayload) {
        val organisationName =
            organisationDetailsRetriever.retrieveOrganisationDetails(payload.relationNumber)

        commandGateway.send<StartCreditProposalCommand>(
            StartCreditProposalCommand(
                UUID.randomUUID(),
                payload.relationNumber,
                organisationName,
                payload.template)
        )
    }
}