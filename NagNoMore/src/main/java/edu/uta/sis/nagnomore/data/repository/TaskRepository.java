package edu.uta.sis.nagnomore.data.repository;

import edu.uta.sis.nagnomore.data.entities.*;

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
    //public List<TaskEntity> findAllByLocation(LocationEntity le);
    public List<TaskEntity> findAllByCategory(CategoryEntity ce);
    public List<TaskEntity> findAllByCreator(UserEntity ue);
    public List<TaskEntity> findAllByAssignee(UserEntity ue);
    public List<TaskEntity> findAllByPriority(Integer p);
    public List<TaskEntity> findAllByStatus(TaskEntity.Status status);
    public List<TaskEntity> findAllByFamily(FamilyEntity fe);

    public List<TaskEntity> findAllByCreatorAndStatus(UserEntity ue, TaskEntity.Status status);
    public List<TaskEntity> findAllByAssigneeAndStatus(UserEntity ue, TaskEntity.Status status);
    public List<TaskEntity> findAllByFamilyAndCategory(FamilyEntity fe, CategoryEntity ce);

    public List<TaskEntity> findAllByCreatorAndCategory(UserEntity ue, CategoryEntity ce);
    public List<TaskEntity> findAllByAssigneeAndCategory(UserEntity ue, CategoryEntity ce);

    public List<TaskEntity> findAllByCreatorAndCategoryAndStatus(UserEntity ue, CategoryEntity ce, TaskEntity.Status status);
    public List<TaskEntity> findAllByAssigneeAndCategoryAndStatus(UserEntity ue, CategoryEntity ce, TaskEntity.Status status);
}
