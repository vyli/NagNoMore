package edu.uta.sis.nagnomore.data.repository;

import edu.uta.sis.nagnomore.data.entities.FamilyEntity;
import edu.uta.sis.nagnomore.data.entities.UserEntity;

import java.util.List;

/**
 * Created by Hannu Lohtander on 5.4.2016.
 */
public interface UserRepository {

    /*
    public UserEntity getUser(Integer id);

    public UserEntity getUser(String username);
    */

    public UserEntity getUserById(Integer id);

    public UserEntity getUserByUsername(String name);

    public UserEntity getUserByFullName(String name);

    public UserEntity getUserByEmail(String email);

    public UserEntity getUserByPhoneNumber(String phoneNumber);

    public List<UserEntity> getUsers();

    public List<UserEntity> getUsersByFamily(FamilyEntity family);

    public List<UserEntity> getChildrenByFamily(FamilyEntity family);

    public List<UserEntity> getParentsByFamily(FamilyEntity family);

    public List<UserEntity> getEldersByFamily(FamilyEntity family);

    public void store(UserEntity u);

    public void update(UserEntity u);

    public void remove(Integer id);
}
