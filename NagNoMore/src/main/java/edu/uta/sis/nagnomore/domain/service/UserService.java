package edu.uta.sis.nagnomore.domain.service;

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

    public List<WwwUser> getUsers();

    public void disable(Long id);

    public void create(WwwUser u);

    public WwwUser update(Long id, String name, String email, String pw, String phone, String role);

    public void remove(Long id);
}
