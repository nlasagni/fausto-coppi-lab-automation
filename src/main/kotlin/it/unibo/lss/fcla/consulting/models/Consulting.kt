package it.unibo.lss.fcla.consulting.models

class Consulting(val consultingType: String, val description: String, val consultingDate: Date, val freelancer: Freelancer) {

    init {
        require(!description.isNullOrEmpty())
        require(!consultingType.isNullOrEmpty())
    }
}