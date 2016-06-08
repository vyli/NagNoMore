package edu.uta.sis.nagnomore.web.testing;

import edu.uta.sis.nagnomore.data.entities.FamilyEntity;
import edu.uta.sis.nagnomore.data.repository.FamilyRepository;
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
    FamilyRepository familyRepository;

    @RequestMapping("/createFam")
    public String createFam(){
        System.out.println("Creating families");

        FamilyEntity fe = new FamilyEntity();               // create FamilyEntity test
        fe.setFamilyName("familyName Smith");
        familyRepository.addFamily(fe);

        FamilyEntity fe2 = new FamilyEntity();
        fe2.setFamilyName("family2Name Brown");
        familyRepository.addFamily(fe2);

        FamilyEntity fe3 = new FamilyEntity();
        fe3.setFamilyName("family3Name Wilson");
        familyRepository.addFamily(fe3);

        System.out.println("Listing all families:");

        for(FamilyEntity f :familyRepository.listAllFamilies() ) {
            System.out.println("Family name: " + f.getFamilyName() + " The id of the family: " +  f.getId());
        }

        return "/home";
    }

    @RequestMapping("/Htest")
    public String first(){

        System.out.println("Starting family testing.");

        FamilyEntity fe = new FamilyEntity();               // create FamilyEntity test
        fe.setFamilyName("First family name");

        familyRepository.addFamily(fe);     // Repository add test

        fe.setFamilyName("Updated family name");
        familyRepository.updateFamily(fe);  // Repository update test

        FamilyEntity copyedFe = familyRepository.findFamily(fe.getId()); // Repository find test
        System.out.println(fe.getFamilyName());                         // Entity getFamilyName test
        System.out.println(copyedFe.getFamilyName());

        familyRepository.removeFamily(fe.getId());                      // Repository remove test

        System.out.println("Ending Family testing.");

        return "/home";
    }

}
