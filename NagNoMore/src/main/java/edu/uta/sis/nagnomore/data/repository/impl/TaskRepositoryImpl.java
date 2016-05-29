package edu.uta.sis.nagnomore.data.repository.impl;

import edu.uta.sis.nagnomore.data.entities.TaskEntity;
import edu.uta.sis.nagnomore.data.entities.UserEntity;
import edu.uta.sis.nagnomore.data.repository.TaskRepository;
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

    public List<TaskEntity> findAllByCreator(UserEntity ue) {
        //TODO Taskien hakeminen jollain ehdolla tekemättä
        return null;
    }

    public List<TaskEntity> findAllByAssignee(UserEntity ue) {
        //TODO Taskien hakeminen jollain ehdolla tekemättä
        return null;
    }
}
