package dinner_invitation.vertical_slicing.domain.commands.addfriend

import dinner_invitation.vertical_slicing.common.Command
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class AddFriendCommand(@TargetAggregateIdentifier override var aggregateId: String,
                            val email: String,
                            val firstName: String,
                            val lastName: String,
                            val phoneNumber: String): Command