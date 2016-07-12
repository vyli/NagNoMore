package edu.uta.sis.nagnomore.web.testing;

import edu.uta.sis.nagnomore.domain.data.Category;
import edu.uta.sis.nagnomore.domain.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Iterator;
import java.util.List;

/**
 * Created by mare on 11.7.2016.
 */

@Controller
public class TestDataController {


    @Autowired
    CategoryService cs;


    @RequestMapping(value="/test/riinatest")
    public String testMe(){
        return "/jsp/testi/testidata";
    }


    @RequestMapping(value="/test/getdatatestajax")
    public @ResponseBody List<Category> getDataViaAjax(){

        //Eeron koodista kopioitu
        Category ce1 = new Category();
        ce1.setTitle("Harrastukset");
        ce1.setDescription("Kategoria harrastusluonteiselle toiminnalle.");
        cs.create(ce1);

        Category ce2 = new Category();
        ce2.setTitle("Lääkkeet");
        ce2.setDescription("Kategoria muistutuksille lääkkeenottoajoista.");
        cs.create(ce2);

        Category ce3 = new Category();
        ce3.setTitle("Kotityöt");
        ce3.setDescription("Kategoria kotitöille.");
        cs.create(ce3);

        List<Category> list = cs.getCategories();

        return list;
    }

    @RequestMapping(value="/test/putdatatestajax")
    public String postDataViaAjax(@RequestBody List<Category> list){

        Iterator<Category> listIterator = list.iterator();
        while(listIterator.hasNext()){
            System.out.println(listIterator.next().toString());
        }

        return "/jsp/testi/testidata";
    }






}
