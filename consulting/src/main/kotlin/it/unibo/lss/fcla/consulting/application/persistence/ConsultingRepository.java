package it.unibo.lss.fcla.consulting.application.persistence;

import it.unibo.lss.fcla.consulting.common.EventSourcedRepository;
import it.unibo.lss.fcla.consulting.domain.consulting.Consulting;

/**
 * @author Stefano Braggion
 */
public class ConsultingRepository extends EventSourcedRepository<Consulting> {
    public ConsultingRepository(EventStore eventStore) {
        super(eventStore);
    }
}
