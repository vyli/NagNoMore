package edu.uta.sis.nagnomore.data.repository.impl;

import edu.uta.sis.nagnomore.data.entities.ReminderEntity;
import edu.uta.sis.nagnomore.data.repository.ReminderRepository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by VPS on 5.6.2016.
 */

@Repository
@Transactional(readOnly = false)
public class ReminderRepositoryImpl implements ReminderRepository {

    @PersistenceContext
    EntityManager em;

    public void add(ReminderEntity reminderEntity) {
        em.persist(reminderEntity);
    }

    public void update(ReminderEntity reminderEntity) {
        em.merge(reminderEntity);
    }

    public ReminderEntity find(Integer id) {
        return em.find(ReminderEntity.class, id);
    }

    public ReminderEntity remove(Integer id) {
        ReminderEntity reminderEntity = find(id);
        em.remove(reminderEntity);
        return reminderEntity;
    }

    public List<ReminderEntity> findAll() {
        try {
            return em.createQuery("From ReminderEntity r", ReminderEntity.class).getResultList();
        } catch (NoResultException nre) {
            return new ArrayList<ReminderEntity>();
        }
    }
}
