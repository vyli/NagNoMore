package edu.uta.sis.nagnomore.web.testing;

import edu.uta.sis.nagnomore.data.entities.FamilyEntity;
import edu.uta.sis.nagnomore.data.entities.UserEntity;
import edu.uta.sis.nagnomore.data.repository.FamilyRepository;
import edu.uta.sis.nagnomore.data.repository.UserRepository;
import edu.uta.sis.nagnomore.domain.data.WwwFamily;
import edu.uta.sis.nagnomore.domain.data.WwwUser;
import edu.uta.sis.nagnomore.domain.service.FamilyService;
import edu.uta.sis.nagnomore.domain.service.UserService;
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

    @Autowired
    FamilyService fs;

    @Autowired
    UserService us;

    @RequestMapping("/test/v2")
    public String test2(){

        WwwFamily f = new WwwFamily();
        f.setFamilyName("vTestFamily");
        fs.addFamily(f);

        WwwFamily f2 = new WwwFamily();
        f2.setFamilyName("vTestFamily2");
        fs.addFamily(f2);

        //  public WwwUser(Long id, String username, String password, String email, String fullName, String phoneNumber, String role, Boolean enabled)
        WwwUser u1 = new WwwUser(null, "Pekka", "salasana", "pekka@huu.haa", "Pekka Rujo",  "123456789", "ROLE_CHILD", true);
        u1.setFamily(f);
        us.create(u1);

        // Change properties and try to update
        u1.setFullName("Pekka Puupää");
        u1.setPhoneNumber("040123123");
        u1.setFamily(f2);
        us.update(u1);

        //  public WwwUser(Long id, String username, String password, String email, String fullName, String phoneNumber, String role, Boolean enabled)
        WwwUser u2 = new WwwUser(null, "Ville", "salasana", "ville@huu.haa", "Ville Rujo",  "123456789", "ROLE_PARENT", true);
        u2.setFamily(f);
        us.create(u2);

        // Try to update using the alternative method (password can be null)
        // public WwwUser update(Long id, String userName, String fullName, String password, String email, String phoneNumber, String role, WwwFamily family);
        us.update(u2.getId(), "Velho", "Ville Valo", null, "ville@huu.haa", "05000000", "ROLE_ELDER", f2);

        // and clean up
        us.remove(u1.getId());
        us.remove(u2.getId());
        fs.removeFamily(f.getId());
        fs.removeFamily(f2.getId());

        return "/home";
    }

    @RequestMapping("/test/v")
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
        ur.create(ue1);

        UserEntity ue2 = new UserEntity();
        ue2.setUsername("TestUser2");
        ue2.setRole("ROLE_PARENT");
        ue2.setFamily(fe);
        ur.create(ue2);

        UserEntity ue3 = new UserEntity();
        ue3.setUsername("TestUser3");
        ue3.setRole("ROLE_ELDER");
        ue3.setFamily(fe);
        ur.create(ue3);

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
