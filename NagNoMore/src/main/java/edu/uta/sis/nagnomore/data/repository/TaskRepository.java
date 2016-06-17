package edu.uta.sis.nagnomore.data.repository;

import edu.uta.sis.nagnomore.data.entities.CategoryEntity;
import edu.uta.sis.nagnomore.data.entities.LocationEntity;
import edu.uta.sis.nagnomore.data.entities.TaskEntity;
import edu.uta.sis.nagnomore.data.entities.UserEntity;

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
    public List<TaskEntity> findAllByLocation(LocationEntity le);
    public List<TaskEntity> findAllByCategory(CategoryEntity ce);
    public List<TaskEntity> findAllByCreator(UserEntity ue);
    public List<TaskEntity> findAllByAssignee(UserEntity ue);
    public List<TaskEntity> findAllByStatus(TaskEntity.Status status);


}
