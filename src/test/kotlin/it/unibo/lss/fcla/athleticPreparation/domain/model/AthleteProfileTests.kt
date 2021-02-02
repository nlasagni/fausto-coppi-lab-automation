package it.unibo.lss.fcla.athleticPreparation.domain.model

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.athleticPreparation.domain.exceptions.WorkoutPlanException
import java.util.Date

class AthleteProfileTests : FreeSpec({

    val athleteProfile = AthleteProfile("john", "petrucci", Date())

    "test renewal medical certificate" - {
        val medicalCertificate = MedicalCertificate(Date(), "competitive")
        athleteProfile.renewMedicalCertificate(Date(), "competitive")

        assert(athleteProfile.getMedicalCertificate() == medicalCertificate)
    }

    "test no multiple plan id was given" - {
        athleteProfile.createWorkoutPlan("plan1", 2, StrenghteningPlanType())
        shouldThrow<WorkoutPlanException> {
            athleteProfile.createWorkoutPlan("plan1", 3, PostInjuryPlanType())
        }
    }
})
