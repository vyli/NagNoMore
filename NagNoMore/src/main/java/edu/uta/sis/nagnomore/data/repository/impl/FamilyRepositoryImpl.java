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

    public List<FamilyEntity> listAllFamilies(){
        return em.createQuery("FROM family f",FamilyEntity.class).getResultList();
    }

}
