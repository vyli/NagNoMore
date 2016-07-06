package edu.uta.sis.nagnomore.data.repository;

import edu.uta.sis.nagnomore.data.entities.*;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by mare on 29.5.2016.
 */
public interface TaskRepository {

    public void addTask(TaskEntity te);
    public void updateTask(TaskEntity te);
    public TaskEntity find(Integer id);
    public TaskEntity remove(Integer id);
    public List<TaskEntity> findAll();

    // Find all by one field

    //public List<TaskEntity> findAllByLocation(LocationEntity le);
    public List<TaskEntity> findAllByCategory(CategoryEntity ce);
    public List<TaskEntity> findAllByCreator(UserEntity ue);
    public List<TaskEntity> findAllByAssignee(UserEntity ue);
    public List<TaskEntity> findAllByPriority(Integer p);
    public List<TaskEntity> findAllByStatus(TaskEntity.Status status);
    public List<TaskEntity> findAllByFamily(FamilyEntity fe);
    public List<TaskEntity> findAllByPrivacy(Boolean b);
    public List<TaskEntity> findAllByDueDate(DateTime start, DateTime end);

    // Find all by two fields

    public List<TaskEntity> findAllByCreatorAndPrivacy(UserEntity ue, Boolean p);
    public List<TaskEntity> findAllByAssigneeAndPrivacy(UserEntity ue, Boolean p);
    public List<TaskEntity> findAllByCreatorAndStatus(UserEntity ue, TaskEntity.Status status);
    public List<TaskEntity> findAllByAssigneeAndStatus(UserEntity ue, TaskEntity.Status status);
    public List<TaskEntity> findAllByFamilyAndCategory(FamilyEntity fe, CategoryEntity ce);
    public List<TaskEntity> findAllByCreatorAndCategory(UserEntity ue, CategoryEntity ce);
    public List<TaskEntity> findAllByAssigneeAndCategory(UserEntity ue, CategoryEntity ce);
    public List<TaskEntity> findAllByCreatorAndDueDate(UserEntity ue, DateTime start, DateTime end);
    public List<TaskEntity> findAllByAssigneeAndDueDate(UserEntity ue, DateTime start, DateTime end);

    // Find all by three fields

    public List<TaskEntity> findAllByCreatorAndCategoryAndStatus(UserEntity ue, CategoryEntity ce, TaskEntity.Status status);
    public List<TaskEntity> findAllByAssigneeAndCategoryAndStatus(UserEntity ue, CategoryEntity ce, TaskEntity.Status status);
    public List<TaskEntity> findAllByCreatorAndStatusAndPrivacy(UserEntity ue, TaskEntity.Status status, Boolean p);
    public List<TaskEntity> findAllByAssigneeAndStatusAndPrivacy(UserEntity ue, TaskEntity.Status status, Boolean p);
    public List<TaskEntity> findAllByFamilyAndCategoryAndPrivacy(FamilyEntity fe, CategoryEntity ce, Boolean p);
    public List<TaskEntity> findAllByCreatorAndCategoryAndPrivacy(UserEntity ue, CategoryEntity ce, Boolean p);
    public List<TaskEntity> findAllByAssigneeAndCategoryAndPrivacy(UserEntity ue, CategoryEntity ce, Boolean b);

    // Find all by four fields

    public List<TaskEntity> findAllByCreatorAndCategoryAndStatusAndPrivacy(UserEntity ue, CategoryEntity ce, TaskEntity.Status status, Boolean p);
    public List<TaskEntity> findAllByAssigneeAndCategoryAndStatusAndPrivacy(UserEntity ue, CategoryEntity ce, TaskEntity.Status status, Boolean p);

}
