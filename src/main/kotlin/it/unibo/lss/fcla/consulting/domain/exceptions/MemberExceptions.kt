package it.unibo.lss.fcla.consulting.domain.exceptions

/**
 *
 */
class MemberFirstNameCannotBeNull : ConsultingException("Member fist name cannot be null or empty")

/**
 *
 */
class MemberLastNameCannotBeNull : ConsultingException("Member last name cannot be null or empty")

/**
 *
 */
class MemberConsultingAlreadyExist : ConsultingException("A Consulting with the given Id already exist for the member")
