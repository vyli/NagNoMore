package edu.uta.sis.nagnomore.data.repository.impl;

import edu.uta.sis.nagnomore.data.entities.FamilyEntity;
import edu.uta.sis.nagnomore.data.entities.UserEntity;
import edu.uta.sis.nagnomore.data.repository.UserRepository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hannu Lohtander on 5.4.2016.
 */
@Repository
@Transactional(readOnly = false)
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    EntityManager em;

    public UserEntity getUserById(Integer id) {
        return em.find(UserEntity.class, id);
    }

    public UserEntity getUserByUsername(String username) {
        return em.createQuery("FROM UserEntity u WHERE u.username=:username", UserEntity.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    public UserEntity getUserByFullName(String name) {
        return em.createQuery("FROM UserEntity u WHERE u.fullName=:name", UserEntity.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public UserEntity getUserByEmail(String email) {
        return em.createQuery("FROM UserEntity u WHERE u.email=:email", UserEntity.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    public UserEntity getUserByPhoneNumber(String number) {
        return em.createQuery("FROM UserEntity u WHERE u.phoneNumber=:number", UserEntity.class)
                .setParameter("number", number)
                .getSingleResult();
    }

    public List<UserEntity> getUsers() {
        return em.createQuery("FROM UserEntity u", UserEntity.class).getResultList();
    }

    public List<UserEntity> getUsersByFamily(FamilyEntity family) {
        return em.createQuery("FROM UserEntity u WHERE u.family = :family", UserEntity.class)
                .setParameter("family", family)
                .getResultList();
    }

    public List<UserEntity> getChildrenByFamily(FamilyEntity family) {
        return getUsersByRoleAndFamily(family, "ROLE_CHILD");
    }

    public List<UserEntity> getParentsByFamily(FamilyEntity family) {
        return getUsersByRoleAndFamily(family, "ROLE_PARENT");
    }

    public List<UserEntity> getEldersByFamily(FamilyEntity family) {
        return getUsersByRoleAndFamily(family, "ROLE_ELDER");
    }

    private List<UserEntity> getUsersByRoleAndFamily(FamilyEntity family, String role) {
        return em.createQuery("FROM UserEntity u WHERE u.role = :role AND u.family = :family", UserEntity.class)
                .setParameter("family", family)
                .setParameter("role", role)
                .getResultList();
    }

    public void store(UserEntity u) {
        em.persist(u);
    }

    public void update(UserEntity u) {
        em.merge(u);
    }

    public void remove(Integer id) {
        UserEntity ue = getUserById(id);
        em.remove(ue);
    }
}
