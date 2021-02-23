package it.unibo.lss.fcla.consulting.models

import it.unibo.lss.fcla.consulting.exceptions.ConsultingException

/**
 * Represents a freelancer
 */
class Freelancer(val firstName: String, val lastName: String, val role: FreelancerRole) {

    private val availability = Availability();

    init {
        //TODO use validation with specification pattern
        if(firstName == "" || lastName == "")
            throw ConsultingException("A freelancer must have a first name and a last name")
    }


}