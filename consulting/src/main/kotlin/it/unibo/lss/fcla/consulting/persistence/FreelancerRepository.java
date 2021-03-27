package it.unibo.lss.fcla.consulting.persistence;

import it.unibo.lss.fcla.consulting.common.EventSourcedRepository;
import it.unibo.lss.fcla.consulting.domain.freelancer.Freelancer;

/**
 * @author Stefano Braggion
 */
public class FreelancerRepository extends EventSourcedRepository<Freelancer> {
    public FreelancerRepository(EventStore eventStore) {
        super(eventStore);
    }
}
