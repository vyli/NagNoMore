package edu.uta.sis.nagnomore.domain.service.impl;

import edu.uta.sis.nagnomore.data.entities.CategoryEntity;
import edu.uta.sis.nagnomore.data.entities.TaskEntity;
import edu.uta.sis.nagnomore.data.entities.UserEntity;
import edu.uta.sis.nagnomore.data.repository.CategoryRepository;
import edu.uta.sis.nagnomore.data.repository.TaskRepository;
import edu.uta.sis.nagnomore.data.repository.UserRepository;
import edu.uta.sis.nagnomore.domain.data.Category;
import edu.uta.sis.nagnomore.domain.data.Task;
import edu.uta.sis.nagnomore.domain.data.WwwUser;
import edu.uta.sis.nagnomore.domain.service.TaskService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static edu.uta.sis.nagnomore.domain.data.Task.Status.NEEDS_ACTION;

/**
 * Created by Eero Asunta on 21.6.2016.
 */

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    UserRepository userRepository;

    public void addTask(Task t) {
        TaskEntity te = new TaskEntity();
        BeanUtils.copyProperties(t,te);
        taskRepository.addTask(te);
        BeanUtils.copyProperties(te,t);
    }

    public void updateTask(Task t) {
        TaskEntity te = new TaskEntity();
        BeanUtils.copyProperties(t,te);
        taskRepository.updateTask(te);
        BeanUtils.copyProperties(te,t);
    }
    public Task find(Integer id) {
        TaskEntity te =  taskRepository.find(id);
        Task t = new Task();
        BeanUtils.copyProperties(te,t);
        return t;
    }

    public Task remove(Task t) {
        TaskEntity te = taskRepository.remove(t.getId());
        BeanUtils.copyProperties(te,t);
        return t;
    }
    public List<Task> findAll() {
        List <TaskEntity> list = taskRepository.findAll();
        ArrayList<Task> tasks = new ArrayList<Task>(list.size());
        for (TaskEntity te: list) {
            Task t = new Task();
            BeanUtils.copyProperties(te,t);
            tasks.add(t);
        }

        return tasks;
    }

    // Eero 21.6.2016 location DataClass and Service missing, currently not assigned foe antyone
    // Do we even need the thing?
    //public List<Task> findAllByLocation(Location l);

    public List<Task> findAllByCategory(Category c) {
        CategoryEntity ce = categoryRepository.find(c.getId());
        List <TaskEntity> list = taskRepository.findAllByCategory(ce);
        ArrayList<Task> tasks = new ArrayList<Task>(list.size());
        for (TaskEntity te: list) {
            Task t = new Task();
            BeanUtils.copyProperties(te,t);
            tasks.add(t);
        }
        return tasks;
    }
    public List<Task> findAllByCreator(WwwUser u) {
        UserEntity ue = userRepository.getUserById((int)(long)(u.getId()));
        List <TaskEntity> list = taskRepository.findAllByCreator(ue);
        ArrayList<Task> tasks = new ArrayList<Task>(list.size());
        for (TaskEntity te: list) {
            Task t = new Task();
            BeanUtils.copyProperties(te,t);
            tasks.add(t);
        }
        return tasks;
    }
    public List<Task> findAllByAssignee(WwwUser u) {
        UserEntity ue = userRepository.getUserById((int)(long)(u.getId()));
        List <TaskEntity> list = taskRepository.findAllByAssignee(ue);
        ArrayList<Task> tasks = new ArrayList<Task>(list.size());
        for (TaskEntity te: list) {
            Task t = new Task();
            BeanUtils.copyProperties(te,t);
            tasks.add(t);
        }
        return tasks;
    }

    // Not sure how to implement this properly
    public List<Task> findAllByStatus(Task.Status status) {

        TaskEntity.Status s1 = null;
        switch (status) {
            case NEEDS_ACTION:
                s1 = TaskEntity.Status.NEEDS_ACTION;
                break;
            case IN_PROGRESS:
                s1 = TaskEntity.Status.IN_PROGRESS;
                break;
            case COMPLETED:
                s1 = TaskEntity.Status.COMPLETED;
                break;
        }

        List <TaskEntity> list = taskRepository.findAllByStatus(s1);
        ArrayList<Task> tasks = new ArrayList<Task>(list.size());
        for (TaskEntity te: list) {
            Task t = new Task();
            BeanUtils.copyProperties(te,t);
            tasks.add(t);
        }
        return tasks;
    }

    public List<Task> findAllByCreatorAndStatus(WwwUser u, Task.Status status) {

        UserEntity ue = userRepository.getUserById((int)(long)(u.getId()));
        TaskEntity.Status s1 = null;
        switch (status) {
            case NEEDS_ACTION:
                s1 = TaskEntity.Status.NEEDS_ACTION;
                break;
            case IN_PROGRESS:
                s1 = TaskEntity.Status.IN_PROGRESS;
                break;
            case COMPLETED:
                s1 = TaskEntity.Status.COMPLETED;
                break;
        }

        List <TaskEntity> list = taskRepository.findAllByCreatorAndStatus(ue, s1);

        ArrayList<Task> tasks = new ArrayList<Task>(list.size());
        for (TaskEntity te: list) {
            Task t = new Task();
            BeanUtils.copyProperties(te,t);
            tasks.add(t);
        }
        return tasks;
    }

    public List<Task> findAllByAssigneeAndStatus(WwwUser u, Task.Status status) {

        UserEntity ue = userRepository.getUserById((int)(long)(u.getId()));
        TaskEntity.Status s1 = null;
        switch (status) {
            case NEEDS_ACTION:
                s1 = TaskEntity.Status.NEEDS_ACTION;
                break;
            case IN_PROGRESS:
                s1 = TaskEntity.Status.IN_PROGRESS;
                break;
            case COMPLETED:
                s1 = TaskEntity.Status.COMPLETED;
                break;
        }

        List <TaskEntity> list = taskRepository.findAllByAssigneeAndStatus(ue, s1);

        ArrayList<Task> tasks = new ArrayList<Task>(list.size());
        for (TaskEntity te: list) {
            Task t = new Task();
            BeanUtils.copyProperties(te,t);
            tasks.add(t);
        }
        return tasks;
    }

}
