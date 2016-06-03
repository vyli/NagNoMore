package edu.uta.sis.nagnomore.domain.service;

import edu.uta.sis.nagnomore.data.entities.CategoryEntity;

import java.util.List;

/**
 * Created by Eero Asunta on 3.6.2016.
 */
public interface CategoryService {

    public List<CategoryEntity> getCategories();

    public CategoryEntity get(Integer id);

    public void update(CategoryEntity q);

    public CategoryEntity remove(CategoryEntity q);

    public void create(CategoryEntity q);
}
