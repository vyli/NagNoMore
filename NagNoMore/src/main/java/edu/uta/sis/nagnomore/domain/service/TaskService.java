package edu.uta.sis.nagnomore.domain.service;

import edu.uta.sis.nagnomore.data.entities.CategoryEntity;
import edu.uta.sis.nagnomore.data.entities.LocationEntity;
import edu.uta.sis.nagnomore.data.entities.TaskEntity;
import edu.uta.sis.nagnomore.data.entities.UserEntity;
import edu.uta.sis.nagnomore.domain.data.Category;
import edu.uta.sis.nagnomore.domain.data.Task;
import edu.uta.sis.nagnomore.domain.data.WwwUser;

import java.util.List;

/**
 * Created by mare on 29.5.2016.
 */
public interface TaskService {

    public void addTask(Task t);
    public void updateTask(Task t);
    public Task find(Integer id);
    public Task remove(Task t);
    public List<Task> findAll();

    // Eero 21.6.2016 location DataClass and Service missing, currently not assigned foe antyone
    //public List<Task> findAllByLocation(Location l);

    public List<Task> findAllByCategory(Category c);
    public List<Task> findAllByCreator(WwwUser u);
    public List<Task> findAllByAssignee(WwwUser u);
    //public List<Task> findAllByStatus(Task.Status status);


}
