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
        FamilyEntity fe = new FamilyEntity();
        fe.setFamilyName("ekaSukunimi");

        familyRepository.addFamily(fe);
        System.out.println(fe);

        fe.setFamilyName("uudempiSukunimi");
        familyRepository.updateFamily(fe);
        System.out.println(fe);

        FamilyEntity kopioFe = familyRepository.findFamily(fe.getId());
        System.out.println(fe);
        System.out.println(kopioFe);

        familyRepository.removeFamily(fe.getId());
        System.out.println(kopioFe);
        System.out.println(fe);     // toimiko remove tuossa yläpuolella?

        return "/home";
    }

}
