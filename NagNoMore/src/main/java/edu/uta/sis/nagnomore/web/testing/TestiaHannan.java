package edu.uta.sis.nagnomore.web.testing;

import edu.uta.sis.nagnomore.data.entities.FamilyEntity;
import edu.uta.sis.nagnomore.data.repository.FamilyRepository;
import edu.uta.sis.nagnomore.domain.data.WwwFamily;
import edu.uta.sis.nagnomore.domain.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Hanna on 2.6.2016.
 */
@Controller
public class TestiaHannan {

    @Autowired
    FamilyRepository familyRepository;          // For the last 2 tests

    @Autowired
    FamilyService familyService;                // For the first test

    @RequestMapping("/serviceFamilyTesting")
    public String serviceTesting(){
        WwwFamily wwwFamily = new WwwFamily();
        wwwFamily.setFamilyName("Brown");
        familyService.addFamily(wwwFamily);                             // test add()

        wwwFamily.setFamilyName("Lee");
        familyService.addFamily(wwwFamily);                             // test add()

        List<WwwFamily> families = familyService.listAllFamilies();    // test ListAllF()
        for(WwwFamily f: families){
            System.out.println(f.getFamilyName());
        }

        System.out.println( "2nd FamilyName in DB: "+ (familyService.findFamilyByName(families.get(1).getFamilyName()) ).getFamilyName() );
        WwwFamily fToUpdate = familyService.findFamily(families.get(0).getId());   // test findFamily() & findFamilyByName()
        fToUpdate.setFamilyName("All New Rocks-Smith");
        familyService.updateFamily(fToUpdate);                          // test update()

        System.out.println(familyService.findFamily(families.get(0).getId()).getFamilyName()); // print first
        familyService.removeFamily(families.get(families.size()-1).getId());    //test remove(last one)
        familyService.removeFamily(families.get(families.size()-2).getId());    //test remove( second last one)
        return "/home";
    }



    @RequestMapping("/repositoryCreateFam")
    public String createFam(){
        System.out.println("Creating families");

        FamilyEntity fe = new FamilyEntity();               // create FamilyEntity test
        fe.setFamilyName("Smith");
        familyRepository.addFamily(fe);

        FamilyEntity fe2 = new FamilyEntity();
        fe2.setFamilyName("Brown");
        familyRepository.addFamily(fe2);

        FamilyEntity fe3 = new FamilyEntity();
        fe3.setFamilyName("Wilson");
        familyRepository.addFamily(fe3);

        System.out.println("Listing all families:");

        for(FamilyEntity f :familyRepository.listAllFamilies() ) {
            System.out.println("Family name: " + f.getFamilyName() + " The id of the family: " +  f.getId());
        }

        return "/home";
    }

    @RequestMapping("/HtestRepository")
    public String first(){

        System.out.println("Starting family testing.");

        FamilyEntity fe = new FamilyEntity();               // create FamilyEntity test
        fe.setFamilyName("First family name");
        System.out.println(fe.getFamilyName());                         // Entity getFamilyName test

        familyRepository.addFamily(fe);     // Repository add test

        fe.setFamilyName("Updated family name");
        familyRepository.updateFamily(fe);  // Repository update test

        FamilyEntity copyedFe = familyRepository.findFamily(fe.getId()); // Repository find test
        System.out.println(fe.getFamilyName());                         // Entity getFamilyName test
        System.out.println(copyedFe.getFamilyName());

        System.out.println("Test FindFamilyByName. FamilyName is: " + familyRepository.findFamilyByName( fe.getFamilyName()).getFamilyName());

        familyRepository.removeFamily(fe.getId());                      // Repository remove test

        System.out.println("Ending Family Repository testing.");

        return "/home";
    }

}
