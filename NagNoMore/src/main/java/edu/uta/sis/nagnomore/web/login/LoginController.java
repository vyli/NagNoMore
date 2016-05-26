package edu.uta.sis.nagnomore.web.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Hannu Lohtander on 6.4.2016.
 */
@Controller
public class LoginController {


    @RequestMapping("/mylogin")
    public String mylogin() {

        return "/login/mylogin";
    }

    @RequestMapping("/loggedin")
    public String loggeddin() {

        return "/login/loggedin";
    }

    @RequestMapping("/loggedout")
    public String loggedout() {

        return "/login/loggedout";
    }

    @RequestMapping("loginerr")
    public String loginerr(Model model) {
        model.addAttribute("loginError", true);
        return "/login/mylogin";
    }
}
