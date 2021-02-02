package it.unibo.lss.fcla.athleticPreparation.domain.model

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.athleticPreparation.domain.exceptions.WorkoutPlanException
import it.unibo.lss.fcla.athleticPreparation.domain.model.AthleteInformation
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

    "test insert new informations" - {
        athleteProfile.createGenericInformation("generic information 1")
        athleteProfile.createGenericInformation("generic information 2")
        athleteProfile.createGenericInformation("generic information 3")
        athleteProfile.createNutritionHealthInformation("nutrition information 1")
        athleteProfile.createPhysiotherapyHealthInformation("physio information 2")
        athleteProfile.createPhysiotherapyHealthInformation("physio information 3")
        assert(athleteProfile.getGenericInformations().count() == 3 &&
            athleteProfile.getNutritionHealthInformations().count() == 1 &&
            athleteProfile.getPhysiotherapyHealthInformations().count() == 2)
    }
})
