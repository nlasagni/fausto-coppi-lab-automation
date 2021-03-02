package it.unibo.lss.fcla.consulting.domain.member

/**
 * @author Stefano Braggion
 *
 *
 */
class Member(
    val memberId: MemberId,
    val firstName: String,
    val lastName: String
) {

    private val memberConsultings: List<MemberConsultings> = listOf()

    init {

    }
}