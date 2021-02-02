package it.unibo.lss.fcla.athleticPreparation.domain.model

import io.kotest.core.spec.style.FreeSpec
import java.util.Date

class MembershipCardTests : FreeSpec({

    "test card is active" - {
        val membershipCard = MembershipCard("1234", Date())
        assert(!membershipCard.isActive())
    }
})