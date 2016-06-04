package edu.uta.sis.nagnomore.data.repository.impl;

import edu.uta.sis.nagnomore.data.entities.FamilyEntity;
import edu.uta.sis.nagnomore.data.entities.TaskEntity;
import edu.uta.sis.nagnomore.data.entities.UserEntity;
import edu.uta.sis.nagnomore.data.repository.FamilyRepository;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Hanna on 2.6.2016.
 */

@Repository
@Transactional(readOnly = false)
public class FamilyRepositoryImpl implements FamilyRepository {

    @PersistenceContext
    EntityManager em;

    public void addFamily(FamilyEntity fe) { em.persist(fe); }
    public void updateFamily(FamilyEntity fe) { em.merge(fe);}
    public FamilyEntity findFamily(Integer id) {return em.find(FamilyEntity.class,id);}
    public void removeFamily(Integer id) {
        FamilyEntity familyToRemove = findFamily(id);
        em.remove(familyToRemove);
    }

    public List<UserEntity> listParents() {
        return em.createQuery("FROM UserEntity u Where u.role = 'adult'",UserEntity.class).getResultList();
    }

    public List<UserEntity> listChildren() {
        return em.createQuery("FROM UserEntity u Where u.role = 'children'", UserEntity.class).getResultList();
    }

    public List<UserEntity> listFamilyMembers(){return em.createQuery("FROM UserEntity u", UserEntity.class).getResultList();}

    //  tällaista tarvittaneen vasta myöhemmin kun on usempia perheitä
    public List<FamilyEntity> listAllFamilies(){return em.createQuery("FROM FamilyEntity f",FamilyEntity.class).getResultList();}

    // Onko loogisesti/rakenteellisesti parempi toteuttaa nämä 4 seur funktiota familyssa vai taskissa? Kun haetaan yleensä aina tietyn perheen taskeja?
    public List<TaskEntity> findAllTasks() {return em.createQuery("FROM TaskEntity t",TaskEntity.class).getResultList(); }

    public List<TaskEntity> findAllTasksExpiring(DateTime start, DateTime end) {
        return em.createQuery("From TaskEntity t WHERE t.due BETWEEN :start AND :end",TaskEntity.class).getResultList();
    }

    public List<TaskEntity> findAllByCreator(UserEntity ue){
        return em.createQuery("FROM TaskEntity t WHERE t.creator=:ue",TaskEntity.class).getResultList();
    }

    public List<TaskEntity> findAllByAssignee(UserEntity ue){
        return em.createQuery("FROM TaskEntity t WHERE t.assignee=:ue",TaskEntity.class).getResultList();
    }

}
