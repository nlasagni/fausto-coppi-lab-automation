package it.unibo.lss.fcla.consulting.common

/**
 * @author Stefano Braggion
 *
 *
 */
interface IRepository<A: AbstractAggregate> {
    fun getById(aggregateId: AggregateId)
    fun save(aggregate: A)
}