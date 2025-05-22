package improve.neso.vertical_slicing.startcreditproposal

import de.eventsourcingbook.cart.common.Event
import improve.neso.vertical_slicing.domain.CreditProposalAggregate
import improve.neso.vertical_slicing.domain.commands.startcreditproposal.StartCreditProposalCommand
import improve.neso.vertical_slicing.events.CreditProposalStartedEvent
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.junit.jupiter.api.BeforeEach
import java.util.*
import kotlin.test.Test

class StartCreditProposalAggregateTest {
  private lateinit var fixture: FixtureConfiguration<CreditProposalAggregate>

  @BeforeEach
  fun setUp() {
   fixture = AggregateTestFixture(CreditProposalAggregate::class.java)
  }

 @Test
 fun `Start credit proposal`() {
  // GIVEN
  val events = mutableListOf<Event>()

  // WHEN
  val aggregateId = UUID.fromString("41bc3104-8f83-43ef-960b-9931e855e97d")
  val command =
   StartCreditProposalCommand(
    aggregateId = aggregateId,
    relationNumber = "123456789",
    organisationName = "John",
    template = "FILM_FINANCING"
   )

  // THEN
  val expectedEvents = mutableListOf<Event>()

  expectedEvents.add(
   CreditProposalStartedEvent(
    aggregateId = aggregateId,
    relationNumber = "123456789",
    organisationName = "John",
    template = "FILM_FINANCING"
   )
  )

  fixture
   .given(events)
   .`when`(command)
   .expectSuccessfulHandlerExecution()
   .expectEvents(*expectedEvents.toTypedArray())
 }
 }