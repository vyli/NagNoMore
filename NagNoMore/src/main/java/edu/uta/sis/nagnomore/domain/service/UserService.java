package edu.uta.sis.nagnomore.domain.service;

import edu.uta.sis.nagnomore.domain.data.WwwFamily;
import edu.uta.sis.nagnomore.domain.data.WwwUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * Created by me on 10.2.2015.
 */
public interface UserService extends UserDetailsService {

    public WwwUser getUserByUsername(String username);

    public WwwUser getUserById(Long userId);

    public UserDetails loadUserByUsername(String username);

    public WwwUser getUserByFullName(String name);

    public WwwUser getUserByEmail(String email);

    public WwwUser getUserByPhoneNumber(String phoneNumber);

    public List<WwwUser> getUsers();

    public List<WwwUser> getUsersByFamily(WwwFamily family);

    public List<WwwUser> getChildrenByFamily(WwwFamily family);

    public List<WwwUser> getParentsByFamily(WwwFamily family);

    public List<WwwUser> getEldersByFamily(WwwFamily family);

    public void disable(Long id);

    public void create(WwwUser u);

    public WwwUser update(WwwUser wwwUser);

    public WwwUser update(Long id, String userName, String fullName, String password, String email, String phoneNumber, String role, WwwFamily family);

    public void remove(Long id);
}
