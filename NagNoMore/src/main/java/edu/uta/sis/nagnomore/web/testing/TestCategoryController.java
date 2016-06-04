package edu.uta.sis.nagnomore.web.testing;

import edu.uta.sis.nagnomore.data.entities.*;
import edu.uta.sis.nagnomore.data.repository.*;
import edu.uta.sis.nagnomore.domain.service.CategoryService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Random;

/**
 * Created by Eero Asunta on 3.6.2016.
 */
@Controller
public class TestCategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryService categoryService;

    @RequestMapping("/catadd")
    public String test1() {

        System.out.println("Starting category testing.");

        // Testing create()
        CategoryEntity ce1 = new CategoryEntity();
        ce1.setTitle("Harrastukset");
        ce1.setDescription("Kategoria harrastusluonteiselle toiminnalle.");
        categoryService.create(ce1);

        CategoryEntity ce2 = new CategoryEntity();
        ce2.setTitle("Lääkkeet");
        ce2.setDescription("Kategoria muistutuksille lääkkeenottoajoista.");
        categoryService.create(ce2);

        CategoryEntity ce3 = new CategoryEntity();
        ce3.setTitle("Kotityöt");
        ce3.setDescription("Kategoria kotitöille.");
        categoryService.create(ce3);


        return "/home";
    }

    @RequestMapping("/catempty")
    public String test2() {

        List<CategoryEntity> catlist = categoryService.getCategories(); // This tests getCategories()
        int size = catlist.size();

        System.out.println("Removing " + size + " entries from Category table.");

        if(size>0) {

            for(int i=0; i<size; i++) {
                categoryService.remove(catlist.get(i)); // This tests remove()
                System.out.println("Removing: " + i);
            }
        }
        return "/home";
    }

    @RequestMapping("/catupdate")
    public String test3() {

        // Empty table
        test2();
        // Create three entries
        test1();

        // Test updating the second entry;
        List<CategoryEntity> catlist = categoryService.getCategories();
        CategoryEntity ce = categoryService.get(catlist.get(1).getId()); // This test get()
        ce.setTitle("Updated title");
        ce.setDescription("Updated description");
        categoryService.update(ce);                 // This test update()


        return "/home";
    }

}
