package edu.uta.sis.nagnomore.domain.service.impl;



import edu.uta.sis.nagnomore.data.entities.CategoryEntity;
import edu.uta.sis.nagnomore.data.repository.CategoryRepository;
import edu.uta.sis.nagnomore.domain.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Eero Asunta on 3.6.2016.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryEntity> getCategories() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CategoryEntity get(Integer id) {;
        return categoryRepository.find(id);
    }

    @Transactional(readOnly = false)
    public void update(CategoryEntity ce) {
        //CategoryEntity ce1 = categoryRepository.find(ce.getId());
        categoryRepository.update(ce);
    }

    @Transactional(readOnly = false)
    public CategoryEntity remove(CategoryEntity ce) {
        CategoryEntity ce1 = categoryRepository.remove(ce.getId());;
        return ce1;
    }

    @Transactional(readOnly = false)
    public void create(CategoryEntity ce) {
        categoryRepository.add(ce);
    }
}
