package edu.uta.sis.nagnomore.data.repository;

import edu.uta.sis.nagnomore.data.entities.FamilyEntity;
import edu.uta.sis.nagnomore.data.entities.TaskEntity;
import edu.uta.sis.nagnomore.data.entities.UserEntity;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

/**
 * Created by Hanna on 2.6.2016.
 */
public interface FamilyRepository {
    public void addFamily(FamilyEntity fe);
    public void updateFamily(FamilyEntity id);
    public FamilyEntity findFamily(Integer id);
    public void removeFamily(Integer id);

    public List<UserEntity> listParents();
    public List<UserEntity> listChildren();
    public List<UserEntity> listFamilyMembers();

//    public List<FamilyEntity> listAllFamilies();    //  tarvittaneen vasta myöhemmin kun on usempia perheitä


}
