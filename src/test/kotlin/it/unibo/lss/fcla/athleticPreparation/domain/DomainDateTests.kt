package it.unibo.lss.fcla.athleticPreparation.domain

import io.kotest.core.spec.style.FreeSpec

class DomainDateTests : FreeSpec({

    "test creation date" - {
        val domainDate = DomainDate(2021, 1, 1)
        assert(domainDate.day == 1 && domainDate.month == 1 && domainDate.year == 2021)
    }
})