package edu.uta.sis.nagnomore.web.testing;

import edu.uta.sis.nagnomore.data.entities.FamilyEntity;
import edu.uta.sis.nagnomore.data.entities.UserEntity;
import edu.uta.sis.nagnomore.data.repository.FamilyRepository;
import edu.uta.sis.nagnomore.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class TestiaVil {
    @Autowired
    UserRepository ur;

    @Autowired
    FamilyRepository fr;

    @RequestMapping("/vtest")
    public String test(){

        // Create family
        FamilyEntity fe = new FamilyEntity();
        fe.setFamilyName("TestFamily123");
        fr.addFamily(fe);

        // Create users
        UserEntity ue1 = new UserEntity();
        ue1.setUsername("TestUser1");
        ue1.setRole("ROLE_CHILD");
        ue1.setFamily(fe);
        ur.store(ue1);

        UserEntity ue2 = new UserEntity();
        ue2.setUsername("TestUser2");
        ue2.setRole("ROLE_PARENT");
        ue2.setFamily(fe);
        ur.store(ue2);

        UserEntity ue3 = new UserEntity();
        ue3.setUsername("TestUser3");
        ue3.setRole("ROLE_ELDER");
        ue3.setFamily(fe);
        ur.store(ue3);

        // Listing All Users in family
        System.out.println("Users in family 1: ");
        for (UserEntity u : ur.getUsersByFamily(fe)) {
            System.out.println("Username: " + u.getUsername() + ". Role: " + u.getRole());
        }

        // Listing Children in family
        System.out.println("Children in family 1: ");
        for (UserEntity u : ur.getChildrenByFamily(fe)) {
            System.out.println("Username: " + u.getUsername() + ". Role: " + u.getRole());
        }

        // Listing Parents in family
        System.out.println("Parents in family 1: ");
        for (UserEntity u : ur.getParentsByFamily(fe)) {
            System.out.println("Username: " + u.getUsername() + ". Role: " + u.getRole());
        }

        // Listing Elders in family
        System.out.println("Admins in family 1: ");
        for (UserEntity u : ur.getEldersByFamily(fe)) {
            System.out.println("Username: " + u.getUsername() + ". Role: " + u.getRole());
        }

        ur.remove(ue1.getId());
        ur.remove(ue2.getId());
        ur.remove(ue3.getId());
        fr.removeFamily(fe.getId());

        return "/home";
    }
}
