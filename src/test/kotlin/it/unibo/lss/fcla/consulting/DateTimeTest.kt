package it.unibo.lss.fcla.consulting

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.consulting.consulting.Date

class DateTimeTest : FreeSpec({

    "test date equality" - {
        val date1 = Date(2021, 1, 1)
        val date2 = Date(2021, 1, 1)
        val date3 = Date(2021, 1, 2)

        assert((date1 == date2) && (date2 != date3))
    }
})
