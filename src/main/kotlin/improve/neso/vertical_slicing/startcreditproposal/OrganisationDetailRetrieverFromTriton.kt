package improve.neso.vertical_slicing.startcreditproposal

import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("triton")
class OrganisationDetailRetrieverFromTriton : OrganisationDetailRetriever {
    override fun retrieveOrganisationDetails(relationNumber: String): String {
        TODO("Not yet implemented")
    }
}