package edu.uta.sis.nagnomore.web.testing;


import edu.uta.sis.nagnomore.domain.data.Category;
import edu.uta.sis.nagnomore.domain.service.CategoryService;
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
    CategoryService categoryService;

    @RequestMapping("/test/catadd")
    public String test1() {

        System.out.println("Starting category testing.");

        // Testing create()
        Category ce1 = new Category();
        ce1.setTitle("Harrastukset");
        ce1.setDescription("Kategoria harrastusluonteiselle toiminnalle.");
        categoryService.create(ce1);

        Category ce2 = new Category();
        ce2.setTitle("Lääkkeet");
        ce2.setDescription("Kategoria muistutuksille lääkkeenottoajoista.");
        categoryService.create(ce2);

        Category ce3 = new Category();
        ce3.setTitle("Kotityöt");
        ce3.setDescription("Kategoria kotitöille.");
        categoryService.create(ce3);


        return "/home";
    }

    @RequestMapping("/test/catempty")
    public String test2() {

        List<Category> catlist = categoryService.getCategories(); // This tests getCategories()
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

    @RequestMapping("/test/catupdate")
    public String test3() {

        // Empty table
        test2();
        // Create three entries
        test1();

        // Test updating the second entry;
        List<Category> catlist = categoryService.getCategories();
        Category c = categoryService.get(catlist.get(1).getId()); // This test get()
        c.setTitle("Updated title");
        c.setDescription("Updated description");
        categoryService.update(c);                 // This test update()

        return "/home";
    }

}
