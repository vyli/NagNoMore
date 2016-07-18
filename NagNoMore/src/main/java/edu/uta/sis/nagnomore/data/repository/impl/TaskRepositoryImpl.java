package edu.uta.sis.nagnomore.data.repository.impl;

import edu.uta.sis.nagnomore.data.entities.*;
import edu.uta.sis.nagnomore.data.repository.TaskRepository;
import org.joda.time.DateTime;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
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

    public List<TaskEntity> findAllByPriority(Integer p) {

        return em.createQuery("FROM TaskEntity t WHERE t.priority=:p", TaskEntity.class)
                .setParameter("p", p)
                .getResultList();
    }

    public List<TaskEntity> findAllByFamily(FamilyEntity fe) {

        return em.createQuery("FROM TaskEntity t WHERE t.family=:fe", TaskEntity.class)
                .setParameter("fe", fe)
                .getResultList();
    }

    public List<TaskEntity> findAllByStatus(TaskEntity.Status s) {

        return em.createQuery("FROM TaskEntity t WHERE t.status=:s", TaskEntity.class)
                .setParameter("s", s)
                .getResultList();
    }

    public List<TaskEntity> findAllByPrivacy(Boolean p) {

        return em.createQuery("FROM TaskEntity t WHERE t.privacy=:p", TaskEntity.class)
                .setParameter("p", p)
                .getResultList();
    }


    public List<TaskEntity> findAllByDueDate(DateTime start, DateTime end) {
        try {
            return em.createQuery("From TaskEntity t WHERE t.due >= :start AND t.due <= :end", TaskEntity.class)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .getResultList();
        } catch (NoResultException nre) {
            /* no-op */
            return new ArrayList<TaskEntity>();
        }
    }

    public List<TaskEntity> findAllTasksWithReminders() {

        return em.createQuery("FROM TaskEntity t WHERE t.reminder IS NOT NULL", TaskEntity.class)
                .getResultList();
    }

    public List<TaskEntity> findAllOverdue() {
        try {
            DateTime now = DateTime.now();
            TaskEntity.Status status = TaskEntity.Status.COMPLETED;
            List<TaskEntity> retList = em.createQuery("From TaskEntity t WHERE t.due <= :now AND t.status <> :status", TaskEntity.class)
                    .setParameter("now", now)
                    .setParameter("status", status)
                    .getResultList();

            return retList;

        } catch (NoResultException nre) {
            /* no-op */
            return new ArrayList<TaskEntity>();
        }
    }

    public List<TaskEntity> findAllWithOverdueReminders() {
        try {
            DateTime now = DateTime.now();
            TaskEntity.Status status = TaskEntity.Status.COMPLETED;
            List<TaskEntity> retList = em.createQuery("From TaskEntity t WHERE t.reminder.time <= :now AND t.status <> :status", TaskEntity.class)
                    .setParameter("now", now)
                    .setParameter("status", status)
                    .getResultList();

            return retList;

        } catch (NoResultException nre) {
            /* no-op */
            return new ArrayList<TaskEntity>();
        }
    }
    // *************************** Search by two fields:
    //**************************************************

    public List<TaskEntity> findAllByCreatorAndPrivacy(UserEntity ue, Boolean p) {

        return em.createQuery("FROM TaskEntity t WHERE t.creator=:ue AND t.privacy=:p", TaskEntity.class)
                .setParameter("ue", ue)
                .setParameter("p", p)
                .getResultList();
    }

    public List<TaskEntity> findAllByAssigneeAndPrivacy(UserEntity ue, Boolean p) {

        return em.createQuery("FROM TaskEntity t WHERE t.assignee=:ue AND t.privacy=:p", TaskEntity.class)
                .setParameter("ue", ue)
                .setParameter("p", p)
                .getResultList();
    }

    public List<TaskEntity> findAllByCreatorAndStatus(UserEntity ue, TaskEntity.Status s) {

        return em.createQuery("FROM TaskEntity t WHERE t.creator=:ue AND t.status=:s", TaskEntity.class)
                .setParameter("ue", ue)
                .setParameter("s", s)
                .getResultList();
    }

    public List<TaskEntity> findAllByAssigneeAndStatus(UserEntity ue, TaskEntity.Status s) {

        return em.createQuery("FROM TaskEntity t WHERE t.assignee=:ue AND t.status=:s", TaskEntity.class)
                .setParameter("ue", ue)
                .setParameter("s", s)
                .getResultList();
    }

    public List<TaskEntity> findAllByCreatorAndCategory(UserEntity ue, CategoryEntity ce) {

        return em.createQuery("FROM TaskEntity t WHERE t.creator=:ue AND t.category=:ce", TaskEntity.class)
                .setParameter("ue", ue)
                .setParameter("ce", ce)
                .getResultList();
    }

    public List<TaskEntity> findAllByAssigneeAndCategory(UserEntity ue, CategoryEntity ce) {

        return em.createQuery("FROM TaskEntity t WHERE t.assignee=:ue AND t.category=:ce", TaskEntity.class)
                .setParameter("ue", ue)
                .setParameter("ce", ce)
                .getResultList();
    }

    public List<TaskEntity> findAllByFamilyAndCategory(FamilyEntity fe, CategoryEntity ce) {

        return em.createQuery("FROM TaskEntity t WHERE t.family=:fe AND t.category=:ce", TaskEntity.class)
                .setParameter("fe", fe)
                .setParameter("ce", ce)
                .getResultList();
    }

    public List<TaskEntity> findAllByCreatorAndDueDate(UserEntity ue, DateTime start, DateTime end) {
        try {
            return em.createQuery("FROM TaskEntity t WHERE t.creator=:ue AND (t.start >= :start AND t.end <= :end)", TaskEntity.class)
                    .setParameter("ue", ue)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .getResultList();

        } catch (NoResultException nre) {
            return new ArrayList<TaskEntity>();
        }
    }

    public List<TaskEntity> findAllByAssigneeAndDueDate(UserEntity ue, DateTime start, DateTime end) {
        try {
            return em.createQuery("FROM TaskEntity t WHERE t.assignee=:ue AND (t.start >= :start AND t.end <= :end)", TaskEntity.class)
                    .setParameter("ue", ue)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .getResultList();

        } catch (NoResultException nre) {
            return new ArrayList<TaskEntity>();
        }
    }

    public List<TaskEntity> findAllOverdueByAssignee(UserEntity ue) {
        try {
            DateTime now = DateTime.now();
            TaskEntity.Status status = TaskEntity.Status.COMPLETED;
            List<TaskEntity> retList = em.createQuery("From TaskEntity t WHERE t.assignee=:ue AND t.due <= :now AND t.status <> :status", TaskEntity.class)
                    .setParameter("ue", ue)
                    .setParameter("now", now)
                    .setParameter("status", status)
                    .getResultList();

            return retList;

        } catch (NoResultException nre) {
            /* no-op */
            return new ArrayList<TaskEntity>();
        }
    }

    public List<TaskEntity> findAllWithOverdueRemindersByAssignee(UserEntity ue) {
        try {
            DateTime now = DateTime.now();
            TaskEntity.Status status = TaskEntity.Status.COMPLETED;
            List<TaskEntity> retList = em.createQuery("From TaskEntity t WHERE t.assignee=:ue AND t.reminder.time <= :now AND t.status <> :status", TaskEntity.class)
                    .setParameter("ue", ue)
                    .setParameter("now", now)
                    .setParameter("status", status)
                    .getResultList();

            return retList;

        } catch (NoResultException nre) {
            /* no-op */
            return new ArrayList<TaskEntity>();
        }
    }
    // *************************** Search by three fields:
    //***************************************************

    public List<TaskEntity> findAllByCreatorAndCategoryAndStatus(UserEntity ue, CategoryEntity ce, TaskEntity.Status s) {

        return em.createQuery("FROM TaskEntity t WHERE t.creator=:ue AND t.category=:ce AND t.status=:s", TaskEntity.class)
                .setParameter("ue", ue)
                .setParameter("ce", ce)
                .setParameter("s", s)
                .getResultList();
    }

    public List<TaskEntity> findAllByAssigneeAndCategoryAndStatus(UserEntity ue, CategoryEntity ce, TaskEntity.Status s) {

        return em.createQuery("FROM TaskEntity t WHERE t.assignee=:ue AND t.category=:ce AND t.status=:s", TaskEntity.class)
                .setParameter("ue", ue)
                .setParameter("ce", ce)
                .setParameter("s", s)
                .getResultList();
    }

    public List<TaskEntity> findAllByCreatorAndStatusAndPrivacy(UserEntity ue, TaskEntity.Status s, Boolean p) {

        return em.createQuery("FROM TaskEntity t WHERE t.creator=:ue AND t.status=:s AND t.privacy=:p", TaskEntity.class)
                .setParameter("ue", ue)
                .setParameter("s", s)
                .setParameter("p", p)
                .getResultList();
    }

    public List<TaskEntity> findAllByAssigneeAndStatusAndPrivacy(UserEntity ue, TaskEntity.Status s, Boolean p) {

        return em.createQuery("FROM TaskEntity t WHERE t.assignee=:ue AND t.status=:s AND t.privacy=:p", TaskEntity.class)
                .setParameter("ue", ue)
                .setParameter("s", s)
                .setParameter("p",p)
                .getResultList();
    }

    public List<TaskEntity> findAllByCreatorAndCategoryAndPrivacy(UserEntity ue, CategoryEntity ce, Boolean p) {

        return em.createQuery("FROM TaskEntity t WHERE t.creator=:ue AND t.category=:ce AND t.privacy=:p", TaskEntity.class)
                .setParameter("ue", ue)
                .setParameter("ce", ce)
                .setParameter("p",p)
                .getResultList();
    }

    public List<TaskEntity> findAllByAssigneeAndCategoryAndPrivacy(UserEntity ue, CategoryEntity ce, Boolean p) {

        return em.createQuery("FROM TaskEntity t WHERE t.assignee=:ue AND t.category=:ce AND t.privacy=:p", TaskEntity.class)
                .setParameter("ue", ue)
                .setParameter("ce", ce)
                .setParameter("p",p)
                .getResultList();
    }

    public List<TaskEntity> findAllByFamilyAndCategoryAndPrivacy(FamilyEntity fe, CategoryEntity ce, Boolean p) {

        return em.createQuery("FROM TaskEntity t WHERE t.family=:fe AND t.category=:ce", TaskEntity.class)
                .setParameter("fe", fe)
                .setParameter("ce", ce)
                .setParameter("p",p)
                .getResultList();
    }

    // *************************** Search by four fields:
    //***************************************************

    public List<TaskEntity> findAllByCreatorAndCategoryAndStatusAndPrivacy(UserEntity ue, CategoryEntity ce, TaskEntity.Status s, Boolean p) {

        return em.createQuery("FROM TaskEntity t WHERE t.creator=:ue AND t.category=:ce AND t.status=:s AND t.privacy=:p", TaskEntity.class)
                .setParameter("ue", ue)
                .setParameter("ce", ce)
                .setParameter("s", s)
                .setParameter("p", p)
                .getResultList();
    }

    public List<TaskEntity> findAllByAssigneeAndCategoryAndStatusAndPrivacy(UserEntity ue, CategoryEntity ce, TaskEntity.Status s, Boolean p) {

        return em.createQuery("FROM TaskEntity t WHERE t.assignee=:ue AND t.category=:ce AND t.status=:s AND t.privacy=:p", TaskEntity.class)
                .setParameter("ue", ue)
                .setParameter("ce", ce)
                .setParameter("s", s)
                .setParameter("p", p)
                .getResultList();
    }

}
