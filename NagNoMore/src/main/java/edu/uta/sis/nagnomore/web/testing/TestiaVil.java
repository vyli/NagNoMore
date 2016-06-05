package edu.uta.sis.nagnomore.web.testing;

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

        // Listing All Users
        System.out.println("Users in family 1: ");
        for (UserEntity u : ur.getUsersByFamily(fr.findFamily(1))) {
        }

        System.out.println("Children in family 1: ");
        for (UserEntity u : ur.getChildrenByFamily(fr.findFamily(1))) {
        }

        System.out.println("Parents in family 1: ");
        for (UserEntity u : ur.getParentsByFamily(fr.findFamily(1))) {
        }

        System.out.println("Admins in family 1: ");
        for (UserEntity u : ur.getAdminsByFamily(fr.findFamily(1))) {
        }

        return "/home";
    }
}
