package edu.uta.sis.nagnomore.domain.service;

import edu.uta.sis.nagnomore.domain.data.Category;

import java.util.List;

/**
 * Created by Eero Asunta on 3.6.2016.
 */
public interface CategoryService {

    public List<Category> getCategories();

    public Category get(Integer id);

    public void update(Category q);

    public Category remove(Category q);

    public void create(Category q);
}
