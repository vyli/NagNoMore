package edu.uta.sis.nagnomore.web.testing;

import edu.uta.sis.nagnomore.data.entities.FamilyEntity;
import edu.uta.sis.nagnomore.data.repository.FamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Hanna on 2.6.2016.
 */
@Controller
public class TestiaHannan {
    @Autowired
    FamilyRepository familyRepository;

    @RequestMapping("/Htest")
    public String eka(){

        System.out.println("Starting family testing.");

        FamilyEntity fe = new FamilyEntity();
        fe.setFamilyName("ekaSukunimi");

        familyRepository.addFamily(fe);     // Repository add test

        fe.setFamilyName("uudempiSukunimi");
        familyRepository.updateFamily(fe);  // Repository update test

        FamilyEntity kopioFe = familyRepository.findFamily(fe.getId()); // Repository find test
        System.out.println(fe.getFamilyName());                         // Entity getFamilyName test
        System.out.println(kopioFe.getFamilyName());

        familyRepository.removeFamily(fe.getId());                      // Repository remove test

        System.out.println("Ending Family testing.");

        return "/home";
    }

}
