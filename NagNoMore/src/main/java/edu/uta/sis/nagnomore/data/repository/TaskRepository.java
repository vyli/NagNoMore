package edu.uta.sis.nagnomore.data.repository;

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
    public List<TaskEntity> findAllByCreator(UserEntity ue);
    public List<TaskEntity> findAllByAssignee(UserEntity ue);
    //Enumit on mulle vieraita, en tiedä, toimiiko vertailu Stringiin vai miten pitää toteuttaa
    //public List<TaskEntity> findAllByStatus(String status);


}
