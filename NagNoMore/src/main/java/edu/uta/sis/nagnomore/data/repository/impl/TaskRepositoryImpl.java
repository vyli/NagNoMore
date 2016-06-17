package edu.uta.sis.nagnomore.data.repository.impl;

import edu.uta.sis.nagnomore.data.entities.CategoryEntity;
import edu.uta.sis.nagnomore.data.entities.LocationEntity;
import edu.uta.sis.nagnomore.data.entities.TaskEntity;
import edu.uta.sis.nagnomore.data.entities.UserEntity;
import edu.uta.sis.nagnomore.data.repository.TaskRepository;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by mare on 29.5.2016.
 */

@Repository
@Transactional(readOnly = false)
public class TaskRepositoryImpl implements TaskRepository {

    @PersistenceContext
    EntityManager em;


    public void addTask(TaskEntity te) {
        em.persist(te);
    }

    public void updateTask(TaskEntity te) {
        em.merge(te);
    }

    public TaskEntity find(Integer id) {
        return em.find(TaskEntity.class, id);
    }

    public TaskEntity remove(Integer id) {
        TaskEntity te = find(id);
        em.remove(te);
        return te;
    }

    public List<TaskEntity> findAll() {
        return em.createQuery("From TaskEntity t", TaskEntity.class).getResultList();
    }


    public List<TaskEntity> findAllByLocation(LocationEntity le) {

        return em.createQuery("FROM TaskEntity t WHERE t.location=:le", TaskEntity.class)
                .setParameter("le", le)
                .getResultList();

    }

    public List<TaskEntity> findAllByCategory(CategoryEntity ce) {

        return em.createQuery("FROM TaskEntity t WHERE t.category=:ce", TaskEntity.class)
                .setParameter("ce", ce)
                .getResultList();

    }

    public List<TaskEntity> findAllByCreator(UserEntity ue) {

        return em.createQuery("FROM TaskEntity t WHERE t.creator=:ue", TaskEntity.class)
                .setParameter("ue", ue)
                .getResultList();

    }

    public List<TaskEntity> findAllByAssignee(UserEntity ue) {

        return em.createQuery("FROM TaskEntity t WHERE t.assignee=:ue", TaskEntity.class)
                .setParameter("ue", ue)
                .getResultList();
    }

    public List<TaskEntity> findAllByStatus(TaskEntity.Status s) {

        return em.createQuery("FROM TaskEntity t WHERE t.status=:s", TaskEntity.class)
                .setParameter("s", s)
                .getResultList();
    }
}
