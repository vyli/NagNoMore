package edu.uta.sis.nagnomore.domain.service;

import edu.uta.sis.nagnomore.data.entities.*;
import edu.uta.sis.nagnomore.domain.data.Category;
import edu.uta.sis.nagnomore.domain.data.Task;
import edu.uta.sis.nagnomore.domain.data.WwwFamily;
import edu.uta.sis.nagnomore.domain.data.WwwUser;
import org.joda.time.DateTime;

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
    // I do not think this is needed at all.
    //public List<Task> findAllByLocation(Location l);

    // Check if past reminder time and alarm not set
    public void updateAlarms();

    // Find all by one parameter:

    public List<Task> findAllByCategory(Category c);
    public List<Task> findAllByCreator(WwwUser u);
    public List<Task> findAllByAssignee(WwwUser u);
    public List<Task> findAllByStatus(Task.Status status);
    public List<Task> findAllByPriority(Integer p);
    public List<Task> findAllByFamily(WwwFamily f);
    public List<Task> findAllByPrivacy(Boolean p);
    public List<Task> findAllByDueDate(DateTime start, DateTime end);
    public List<Task> findAllTasksWithReminders();
    public List<Task> findAllOverdue();
    public List<Task> findAllWithOverdueReminders();

    // Find all by two parameters:

    public List<Task> findAllByCreatorAndPrivacy(WwwUser u, Boolean p);
    public List<Task> findAllByAssigneeAndPrivacy(WwwUser u, Boolean p);
    public List<Task> findAllByCreatorAndStatus(WwwUser u, Task.Status status);
    public List<Task> findAllByAssigneeAndStatus(WwwUser u, Task.Status status);
    public List<Task> findAllByFamilyAndCategory(WwwFamily f, Category e);
    public List<Task> findAllByCreatorAndCategory(WwwUser u, Category e);
    public List<Task> findAllByAssigneeAndCategory(WwwUser u, Category e);
    public List<Task> findAllByCreatorAndDueDate(WwwUser u, DateTime start, DateTime end);
    public List<Task> findAllByAssigneeAndDueDate(WwwUser u, DateTime start, DateTime end);
    public List<Task> findAllOverdueByAssignee(WwwUser u);
    public List<Task> findAllWithOverdueRemindersByAssignee(WwwUser u);

    // Find all by three parameters

    public List<Task> findAllByCreatorAndCategoryAndStatus(WwwUser e, Category c, Task.Status status);
    public List<Task> findAllByAssigneeAndCategoryAndStatus(WwwUser u, Category c, Task.Status status);
    public List<Task> findAllByCreatorAndStatusAndPrivacy(WwwUser u, Task.Status status, Boolean p);
    public List<Task> findAllByAssigneeAndStatusAndPrivacy(WwwUser u, Task.Status status, Boolean p);
    public List<Task> findAllByFamilyAndCategoryAndPrivacy(WwwFamily f, Category c, Boolean p);
    public List<Task> findAllByCreatorAndCategoryAndPrivacy(WwwUser u, Category c, Boolean p);
    public List<Task> findAllByAssigneeAndCategoryAndPrivacy(WwwUser u, Category c, Boolean b);

    // Find all by four parameters

    public List<Task> findAllByCreatorAndCategoryAndStatusAndPrivacy(WwwUser u, Category c, Task.Status status, Boolean p);
    public List<Task> findAllByAssigneeAndCategoryAndStatusAndPrivacy(WwwUser u, Category c, Task.Status status, Boolean p);


}
