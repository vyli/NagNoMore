package edu.uta.sis.nagnomore.domain.service.impl;

import edu.uta.sis.nagnomore.data.entities.UserEntity;
import edu.uta.sis.nagnomore.data.repository.UserRepository;
import edu.uta.sis.nagnomore.data.entities.FamilyEntity;
import edu.uta.sis.nagnomore.data.repository.FamilyRepository;
import edu.uta.sis.nagnomore.domain.data.WwwFamily;
import edu.uta.sis.nagnomore.domain.data.WwwUser;
import edu.uta.sis.nagnomore.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hannu Lohtander on 2.4.2016.
 */

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    FamilyRepository familyRepository;

    public WwwUser getUserById(Long userId) {
        UserEntity u = userRepository.getUserById(userId.intValue());
        return new WwwUser(new Long(u.getId()), u.getUsername(), u.getPassword(), u.getEmail(), u.getFullName(), u.getPhoneNumber(), u.getRole(), u.getCreated());
    }

    public WwwUser getUserByUsername(String username) {
        UserEntity u = userRepository.getUserByUsername(username);
        return new WwwUser(new Long(u.getId()), u.getUsername(), u.getPassword(), u.getEmail(), u.getFullName(), u.getPhoneNumber(), u.getRole(), u.getCreated());
    }

    public WwwUser getUserByFullName(String name) {
        UserEntity u = userRepository.getUserByFullName(name);
        return new WwwUser(new Long(u.getId()), u.getUsername(), u.getPassword(), u.getEmail(), u.getFullName(), u.getPhoneNumber(), u.getRole(), u.getCreated());
    }

    public WwwUser getUserByEmail(String email) {
        UserEntity u = userRepository.getUserByEmail(email);
        return new WwwUser(new Long(u.getId()), u.getUsername(), u.getPassword(), u.getEmail(), u.getFullName(), u.getPhoneNumber(), u.getRole(), u.getCreated());
    }

    public WwwUser getUserByPhoneNumber(String phoneNumber) {
        UserEntity u = userRepository.getUserByPhoneNumber(phoneNumber);
        return new WwwUser(new Long(u.getId()), u.getUsername(), u.getPassword(), u.getEmail(), u.getFullName(), u.getPhoneNumber(), u.getRole(), u.getCreated());
    }

    // TODO: Remove this?
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        return getUserByUsername(username);
    }

    public List<WwwUser> getUsers() {
        List<UserEntity> userEntities = userRepository.getUsers();
        ArrayList<WwwUser> wwwUserArrayList = new ArrayList<WwwUser>();
        if (userEntities != null && !userEntities.isEmpty()) {
            for (UserEntity u : userEntities) {
                wwwUserArrayList.add(new WwwUser(new Long(u.getId()), u.getUsername(), u.getPassword(), u.getEmail(), u.getFullName(), u.getPhoneNumber(), u.getRole(), u.isEnabled()));
            }
        }
        return wwwUserArrayList;
    }

    public List<WwwUser> getUsersByFamily(WwwFamily family) {
        List<UserEntity> userEntities = userRepository.getUsersByFamily(familyRepository.findFamily(family.getId()));
        ArrayList<WwwUser> wwwUserArrayList = new ArrayList<WwwUser>();
        if (userEntities != null && !userEntities.isEmpty()) {
            for (UserEntity u : userEntities) {
                wwwUserArrayList.add(new WwwUser(new Long(u.getId()), u.getUsername(), u.getPassword(), u.getEmail(), u.getFullName(), u.getPhoneNumber(), u.getRole(), u.isEnabled()));
            }
        }
        return wwwUserArrayList;
    }

    public List<WwwUser> getChildrenByFamily(WwwFamily family) {
        List<UserEntity> userEntities = userRepository.getChildrenByFamily(familyRepository.findFamily(family.getId()));
        ArrayList<WwwUser> wwwUserArrayList = new ArrayList<WwwUser>();
        if (userEntities != null && !userEntities.isEmpty()) {
            for (UserEntity u : userEntities) {
                wwwUserArrayList.add(new WwwUser(new Long(u.getId()), u.getUsername(), u.getPassword(), u.getEmail(), u.getFullName(), u.getPhoneNumber(), u.getRole(), u.isEnabled()));
            }
        }
        return wwwUserArrayList;
    }

    public List<WwwUser> getParentsByFamily(WwwFamily family) {
        List<UserEntity> userEntities = userRepository.getParentsByFamily(familyRepository.findFamily(family.getId()));
        ArrayList<WwwUser> wwwUserArrayList = new ArrayList<WwwUser>();
        if (userEntities != null && !userEntities.isEmpty()) {
            for (UserEntity u : userEntities) {
                wwwUserArrayList.add(new WwwUser(new Long(u.getId()), u.getUsername(), u.getPassword(), u.getEmail(), u.getFullName(), u.getPhoneNumber(), u.getRole(), u.isEnabled()));
            }
        }
        return wwwUserArrayList;
    }

    public List<WwwUser> getEldersByFamily(WwwFamily family) {
        List<UserEntity> userEntities = userRepository.getEldersByFamily(familyRepository.findFamily(family.getId()));
        ArrayList<WwwUser> wwwUserArrayList = new ArrayList<WwwUser>();
        if (userEntities != null && !userEntities.isEmpty()) {
            for (UserEntity u : userEntities) {
                wwwUserArrayList.add(new WwwUser(new Long(u.getId()), u.getUsername(), u.getPassword(), u.getEmail(), u.getFullName(), u.getPhoneNumber(), u.getRole(), u.isEnabled()));
            }
        }
        return wwwUserArrayList;
    }

    @Transactional(readOnly = false)
    public void create(WwwUser u) {
        UserEntity dbu = new UserEntity();
        dbu.setEmail(u.getEmail());
        dbu.setPassword(u.getPassword());
        dbu.setRole(u.getRole());
        dbu.setUsername(u.getUsername());
        dbu.setFullName(u.getFullName());
        dbu.setPhoneNumber(u.getPhoneNumber());
        dbu.setEnabled(u.isEnabled());
        if (u.getFamily() != null) {
            dbu.setFamily(familyRepository.findFamily(u.getFamily().getId()));
        }
        userRepository.create(dbu);
        u.setId(dbu.getId().longValue());
    }

    @Transactional(readOnly = false)
    public void remove(Long id) {
        userRepository.remove(id.intValue());
    }

    @Transactional(readOnly = false)
    public WwwUser update(WwwUser wwwUser) {
        UserEntity user = userRepository.getUserById(wwwUser.getId().intValue());
        user.setUsername(wwwUser.getUsername());
        user.setFullName(wwwUser.getFullName());
        user.setEmail(wwwUser.getEmail());
        user.setPhoneNumber(wwwUser.getPhoneNumber());
        user.setRole(wwwUser.getRole());
        user.setPassword(wwwUser.getPassword());
        user.setFamily(familyRepository.findFamily(wwwUser.getFamily().getId()));
        userRepository.update(user);
        WwwUser wwwUser2 = new WwwUser(user.getId().longValue(), user.getUsername(), user.getPassword(), user.getEmail(), user.getFullName(), user.getPhoneNumber(), user.getRole(), user.isEnabled());
        return wwwUser2;
    }

    @Transactional(readOnly = false)
    public WwwUser update(Long id, String userName, String fullName, String password, String email, String phoneNumber, String role, WwwFamily family) {
        UserEntity user = userRepository.getUserById(id.intValue());
        user.setUsername(userName);
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setRole(role);
        if (!StringUtils.isEmpty(password)) {
            user.setPassword(password);
        }
        if (family != null) {
            user.setFamily(familyRepository.findFamily(family.getId()));
        }
        userRepository.update(user);
        WwwUser wwwUser = new WwwUser(id, user.getUsername(), user.getPassword(), user.getEmail(), user.getFullName(), user.getPhoneNumber(), user.getRole(), user.isEnabled());
        return wwwUser;
    }

    @Transactional(readOnly = false)
    public void disable(Long id){
        UserEntity e = userRepository.getUserById(id.intValue());
        e.setEnabled(Boolean.FALSE);
        userRepository.update(e);
    }
}
