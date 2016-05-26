package edu.uta.sis.nagnomore.data.repository.impl;

import edu.uta.sis.nagnomore.data.entities.CalendarEntity;
import edu.uta.sis.nagnomore.data.repository.CalendarRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Hannu Lohtander on 10.5.2016.
 */
@Repository
@Transactional(readOnly = false)
public class CalendarReposityImpl implements CalendarRepository {

    @PersistenceContext
    EntityManager em;

    public void save(CalendarEntity entity) {
        em.persist(entity);
    }

    public CalendarEntity find(Integer id) {
        return em.find(CalendarEntity.class, id);
    }

}
