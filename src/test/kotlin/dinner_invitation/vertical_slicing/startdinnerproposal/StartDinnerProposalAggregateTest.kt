package dinner_invitation.vertical_slicing.startdinnerproposal

import dinner_invitation.vertical_slicing.common.Event
import dinner_invitation.vertical_slicing.domain.DinnerInvitationAggregate
import dinner_invitation.vertical_slicing.domain.commands.startcreditproposal.StartDinnerProposalCommand
import dinner_invitation.vertical_slicing.events.DinnerProposalStartedEvent
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.junit.jupiter.api.BeforeEach
import java.time.LocalDateTime
import java.util.*
import kotlin.test.Test

class StartDinnerProposalAggregateTest {
  private lateinit var fixture: FixtureConfiguration<DinnerInvitationAggregate>

  @BeforeEach
  fun setUp() {
   fixture = AggregateTestFixture(DinnerInvitationAggregate::class.java)
  }

 @Test
 fun `Start dinner proposal`() {
  // GIVEN
  val events = mutableListOf<Event>()

  // WHEN
  val aggregateId = UUID.fromString("41bc3104-8f83-43ef-960b-9931e855e97d").toString()
  val now = LocalDateTime.now()
  val command =
   StartDinnerProposalCommand(
    aggregateId = aggregateId,
    dateTime = now,
    friends = listOf("123456789", "9807926"))

  // THEN
  val expectedEvents = mutableListOf<Event>()

  expectedEvents.add(
   DinnerProposalStartedEvent(
    dinnerProposalID = aggregateId,
    dateTime = now,
    friends = listOf("123456789", "9807926"),
   )
  )

  fixture
   .given(events)
   .`when`(command)
   .expectSuccessfulHandlerExecution()
   .expectEvents(*expectedEvents.toTypedArray())
 }
 }