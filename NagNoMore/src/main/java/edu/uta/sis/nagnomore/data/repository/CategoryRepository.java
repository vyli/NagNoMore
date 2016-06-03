package edu.uta.sis.nagnomore.data.repository;



import edu.uta.sis.nagnomore.data.entities.CategoryEntity;
import edu.uta.sis.nagnomore.data.entities.EventEntity;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by Eero Asunta on 3.6.2016.
 */
public interface CategoryRepository {

    public void add(CategoryEntity categoryEntity);

    public void update(CategoryEntity categoryEntity);

    public CategoryEntity find(Integer id);

    public CategoryEntity remove(Integer id);

    public List<CategoryEntity> findAll();

}
