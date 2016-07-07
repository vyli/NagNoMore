package edu.uta.sis.nagnomore.web.account;


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

    @RequestMapping(value = "/account/register", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("user", new WwwUser());
        return "/account/register";
    }

    @RequestMapping(value = "/members/{id}", method = RequestMethod.GET)
    public String listFamilyMembers(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal WwwUser user) {
        if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || user.getFamily().getId().longValue() == id.longValue()) {
            model.addAttribute("users", userService.getUsersByFamily(familyService.findFamily(id.intValue())));
            return "/account/list";
        } else {
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
            WwwUser w2 = userService.update(
                    user.getId(),
                    form.getFullName(),
                    form.getEmail(),
                    form.getPassword(),
                    form.getPhoneNumber(),
                    form.getRole()
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

    @RequestMapping(value = "/account/create", method = RequestMethod.POST)
    public String register(@ModelAttribute("user") UserRegisterForm user, Model model) {
        // TODO @VALIDate
        userService.create(
                new WwwUser(
                        null,
                        user.getUsername(),
                        user.getPassword(),
                        user.getEmail(),
                        user.getFullName(),
                        user.getPhoneNumber(),
                        "ROLE_ELDER",
                        Boolean.TRUE));

        WwwUser u2 = userService.getUserByUsername(user.getUsername());
        model.addAttribute("user", u2);
        return "redirect:/account/show/" + u2.getId();
    }

    @RequestMapping(value = "/account/remove/{id}", method = RequestMethod.GET)
    public String remove(@PathVariable("id") Long id, Model model,@AuthenticationPrincipal WwwUser user) {
        if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            // set enabled false, do not remove.
            // If you remove remember to invalidate possible session this user has.
            userService.disable(id);
        }
        return "redirect:/accounts";
    }


}
