package improve.neso.vertical_slicing.startcreditproposal

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("stub")
class OrganisationDetailRetrieverStub : OrganisationDetailRetriever {
    override fun retrieveOrganisationDetails(relationNumber: String): String {
        return "pets only"
    }
}