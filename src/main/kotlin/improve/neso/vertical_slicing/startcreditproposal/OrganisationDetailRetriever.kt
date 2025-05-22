package improve.neso.vertical_slicing.startcreditproposal

interface OrganisationDetailRetriever {
    fun retrieveOrganisationDetails(relationNumber: String) : String
}