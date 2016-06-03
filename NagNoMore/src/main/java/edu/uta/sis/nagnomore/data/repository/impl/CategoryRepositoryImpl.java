package edu.uta.sis.nagnomore.data.repository.impl;


import edu.uta.sis.nagnomore.data.entities.CategoryEntity;
import edu.uta.sis.nagnomore.data.repository.CategoryRepository;;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eero Asunta on 3.6.2016.
 */
@Repository
@Transactional(readOnly = false)
public class CategoryRepositoryImpl implements CategoryRepository {

    @PersistenceContext
    EntityManager em;

    public void add(CategoryEntity categoryEntity) {
        em.persist(categoryEntity);
    }

    public void update(CategoryEntity categoryEntity) {
        em.merge(categoryEntity);
    }

    public CategoryEntity find(Integer id) {
        return em.find(CategoryEntity.class, id);
    }

    public CategoryEntity remove(Integer id) {
        CategoryEntity categoryEntity = find(id);
        em.remove(categoryEntity);
        return categoryEntity;
    }

    public List<CategoryEntity> findAll() {
        try {
            return em.createQuery("From CategoryEntity e", CategoryEntity.class).getResultList();
        } catch (NoResultException nre) {
            return new ArrayList<CategoryEntity>();
        }
    }
}
