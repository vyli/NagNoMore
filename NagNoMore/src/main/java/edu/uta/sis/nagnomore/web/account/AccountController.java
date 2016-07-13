package edu.uta.sis.nagnomore.web.account;


import edu.uta.sis.nagnomore.domain.data.WwwFamily;
import edu.uta.sis.nagnomore.domain.data.WwwUser;
import edu.uta.sis.nagnomore.domain.service.FamilyService;
import edu.uta.sis.nagnomore.domain.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hannu Lohtander on 5.4.2016.
 */
@Controller
public class AccountController {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    UserService userService;
    @Autowired
    FamilyService familyService;

    // First time registration, role is elder by default. Create a family in the process
    @RequestMapping(value = "/account/registeruser", method = RequestMethod.POST)
    public String register(@ModelAttribute("user") UserRegisterForm user, Model model) {

        // TODO: Catch exceptions, validate

        // Create new family
        WwwFamily wwwFamily = new WwwFamily();
        wwwFamily.setFamilyName(user.getFamily());
        familyService.addFamily(wwwFamily);

        // Create user
        WwwUser wwwUser = new WwwUser(
                null,
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getFullName(),
                user.getPhoneNumber(),
                "ROLE_ELDER",
                Boolean.TRUE);
        wwwUser.setFamily(wwwFamily);

        userService.create(wwwUser);

        WwwUser u2 = userService.getUserByUsername(user.getUsername());
        model.addAttribute("user", u2);
        return "redirect:/account/show/" + u2.getId();
    }

    // Create a family member, limited to elders/admins
    @RequestMapping(value = "/account/createmember", method = RequestMethod.POST)
    public String createMember(@ModelAttribute("user") UserRegisterForm user, Model model, @AuthenticationPrincipal WwwUser loggedUser) {

        // TODO: Catch exceptions, validate

        if (loggedUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ELDER")) || loggedUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            // Create user
            WwwUser wwwUser = new WwwUser(
                    null,
                    user.getUsername(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getFullName(),
                    user.getPhoneNumber(),
                    user.getRole(),
                    Boolean.TRUE);
            wwwUser.setFamily(loggedUser.getFamily());

            userService.create(wwwUser);

            WwwUser u2 = userService.getUserByUsername(user.getUsername());
            model.addAttribute("user", u2);
            return "redirect:/account/show/" + u2.getId();
        }
        else {
            throw new AccessDeniedException("not.allowed");
        }
    }

    @RequestMapping(value = "/account/show/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal WwwUser user) {
        if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || user.getId().longValue() == id.longValue()) {
            WwwUser u = userService.getUserById(id);
            model.addAttribute("user", u);
            return "/account/account";
        } else {
            throw new AccessDeniedException("not.allowed");
        }
    }

    @RequestMapping(value = {"/accounts","/account/list"}, method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("users", userService.getUsers());

        return "/account/list";
    }

    // List all members of family, limited to family members and admins
    @RequestMapping(value = "/members/{id}", method = RequestMethod.GET)
    public String listFamilyMembers(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal WwwUser user) {
        if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || user.getFamily().getId().longValue() == id.longValue()) {
            model.addAttribute("users", userService.getUsersByFamily(familyService.findFamily(id.intValue())));
            return "/account/list";
        } else {
            throw new AccessDeniedException("not.allowed");
        }
    }

    // List elders of family, limited to family members and admins
    @RequestMapping(value = "/elders/{id}", method = RequestMethod.GET)
    public String listFamilyElders(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal WwwUser user) {
        if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || user.getFamily().getId().longValue() == id.longValue()) {
            model.addAttribute("users", userService.getEldersByFamily(familyService.findFamily(id.intValue())));
            return "/account/list";
        } else {
            throw new AccessDeniedException("not.allowed");
        }
    }

    // List parents of family, limited to family members and admins
    @RequestMapping(value = "/parents/{id}", method = RequestMethod.GET)
    public String listFamilyParents(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal WwwUser user) {
        if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || user.getFamily().getId().longValue() == id.longValue()) {
            model.addAttribute("users", userService.getParentsByFamily(familyService.findFamily(id.intValue())));
            return "/account/list";
        } else {
            throw new AccessDeniedException("not.allowed");
        }
    }

    // List children of family, limited to family members and admins
    @RequestMapping(value = "/children/{id}", method = RequestMethod.GET)
    public String listFamilyChildren(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal WwwUser user) {
        if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || user.getFamily().getId().longValue() == id.longValue()) {
            model.addAttribute("users", userService.getChildrenByFamily(familyService.findFamily(id.intValue())));
            return "/account/list";
        } else {
            throw new AccessDeniedException("not.allowed");
        }
    }

    @RequestMapping(value = "/account/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal WwwUser user) {
        if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || user.getId().longValue() == id.longValue()) {
            WwwUser w1 = userService.getUserById(id);
            model.addAttribute("user", w1);
            return "/account/update";
        } else {
            throw new AccessDeniedException("not.allowed");
        }
    }

    // TODO: Control who can change roles. Obvious security risk.
    @RequestMapping(value = "/account/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable("id") Long id, @ModelAttribute("form") UserRegisterForm form, Model model, @AuthenticationPrincipal WwwUser user) {
        // ONLY ROLE_ADMIN is ALLOWED TO UPDATE ANY USER
        if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || user.getId().longValue() == id.longValue()) {
            // public WwwUser update(Long id, String userName, String fullName, String password, String email, String phoneNumber, String role, WwwFamily family);
            WwwUser w2 = userService.update(
                    user.getId(),
                    form.getUsername(),
                    form.getFullName(),
                    form.getEmail(),
                    form.getPassword(),
                    form.getPhoneNumber(),
                    form.getRole(),
                    null
            );

            try {
                List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                authorities.add(new SimpleGrantedAuthority(w2.getRole()));
                Authentication auth = new UsernamePasswordAuthenticationToken(w2.getUsername(), w2.getPassword(), authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                SecurityContextHolder.getContext().setAuthentication(null);
                logger.error("Failure in autoLogin", e);
            }

            return "redirect:/account/show/" + w2.getId();
        } else {
            throw new AccessDeniedException("not.allowed");
        }
    }

    @RequestMapping(value = "/account/delete/{id}", method = RequestMethod.GET)
    public String remove(@PathVariable("id") Long id, Model model,@AuthenticationPrincipal WwwUser user) {
        if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            // set enabled false, do not remove.
            // If you remove remember to invalidate possible session this user has.
            userService.disable(id);
        }
        return "redirect:/accounts";
    }


    @RequestMapping(value = "/account/register", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("user", new WwwUser());
        return "/account/register";
    }

    // Used for "old" site, new functions for registering/creating users are above
    @RequestMapping(value = "/account/create", method = RequestMethod.POST)
    public String create(@ModelAttribute("user") UserRegisterForm user, Model model) {
        WwwUser user1 = new WwwUser(
                null,
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getFullName(),
                user.getPhoneNumber(),
                "ROLE_ADMIN",
                Boolean.TRUE);
        userService.create(user1);
        /*
        WwwUser u2 = userService.getUserByUsername(user.getUsername());
        model.addAttribute("user", user2);
        return "redirect:/account/show/" + user2.getId();
        */

        return "redirect:/mylogin";
    }

}
