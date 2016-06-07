package edu.uta.sis.nagnomore.domain.service.impl;



import edu.uta.sis.nagnomore.data.entities.CategoryEntity;
import edu.uta.sis.nagnomore.data.repository.CategoryRepository;
import edu.uta.sis.nagnomore.domain.data.Category;
import edu.uta.sis.nagnomore.domain.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eero Asunta on 3.6.2016.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<Category> getCategories() {
        List <CategoryEntity> list = categoryRepository.findAll();
        ArrayList<Category> categories = new ArrayList<Category>(list.size());
        for (CategoryEntity ce: list) {
            Category c = new Category();
            BeanUtils.copyProperties(ce,c);
            categories.add(c);
        }

        return categories;
    }

    @Transactional(readOnly = true)
    public Category get(Integer id) {;

        CategoryEntity ce =  categoryRepository.find(id);
        Category c = new Category();
        BeanUtils.copyProperties(ce,c);
        return c;
    }

    @Transactional(readOnly = false)
    public void update(Category c) {
        CategoryEntity ce = categoryRepository.find(c.getId());
        BeanUtils.copyProperties(c,ce);
        categoryRepository.update(ce);
    }

    @Transactional(readOnly = false)
    public Category remove(Category c) {

        CategoryEntity ce = categoryRepository.remove(c.getId());
        BeanUtils.copyProperties(ce,c);
        return c;
    }

    @Transactional(readOnly = false)
    public void create(Category c) {

        CategoryEntity ce = new CategoryEntity();
        BeanUtils.copyProperties(c,ce);
        categoryRepository.add(ce);
        // copy id and other stuff like created timestamp
        BeanUtils.copyProperties(ce,c);
    }
}
