package edu.uta.sis.nagnomore.domain.service.impl;

import edu.uta.sis.nagnomore.data.entities.CategoryEntity;
import edu.uta.sis.nagnomore.data.entities.FamilyEntity;
import edu.uta.sis.nagnomore.data.entities.TaskEntity;
import edu.uta.sis.nagnomore.data.entities.UserEntity;
import edu.uta.sis.nagnomore.data.repository.CategoryRepository;
import edu.uta.sis.nagnomore.data.repository.FamilyRepository;
import edu.uta.sis.nagnomore.data.repository.TaskRepository;
import edu.uta.sis.nagnomore.data.repository.UserRepository;
import edu.uta.sis.nagnomore.domain.data.Category;
import edu.uta.sis.nagnomore.domain.data.Task;
import edu.uta.sis.nagnomore.domain.data.WwwFamily;
import edu.uta.sis.nagnomore.domain.data.WwwUser;
import edu.uta.sis.nagnomore.domain.service.TaskService;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    FamilyRepository familyRepository;

    // Helper to change Task.Status to TaskEntity.Status
    private TaskEntity.Status getStatus(Task.Status status){

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

        return s1;
    }

    // Helper to change TaskEntity-list to Task-list
    private List<Task> getTasks(List<TaskEntity> list){

        ArrayList<Task> tasks = new ArrayList<Task>(list.size());

        for (TaskEntity te: list) {
            Task t = new Task();
            BeanUtils.copyProperties(te,t);
            tasks.add(t);
        }
        return tasks;
    }

    @Transactional(readOnly = false)
    public void addTask(Task t) {
        TaskEntity te = new TaskEntity();
        BeanUtils.copyProperties(t,te);

        CategoryEntity ce = new CategoryEntity();
        Category c = t.getCategory();
        BeanUtils.copyProperties(c, ce);
        te.setCategory(ce);

        if (t.getFamily() != null) {
            FamilyEntity fe = familyRepository.findFamily(t.getFamily().getId());
            te.setFamily(fe);
        }

        UserEntity ue = userRepository.getUserById(t.getCreator().getId().intValue());
        te.setCreator(ue);

        UserEntity ue2 = userRepository.getUserById(t.getAssignee().getId().intValue());
        te.setAssignee(ue2);

        Task.Status ts = t.getStatus();
        TaskEntity.Status tes  = getStatus(ts);
        te.setStatus(tes);

        taskRepository.addTask(te);

        BeanUtils.copyProperties(te,t);
    }

    @Transactional(readOnly = false)
    public void updateTask(Task t) {
        TaskEntity te = new TaskEntity();
        BeanUtils.copyProperties(t,te);
        taskRepository.updateTask(te);
        BeanUtils.copyProperties(te,t);
    }

    @Transactional(readOnly = true)
    public Task find(Integer id) {
        TaskEntity te =  taskRepository.find(id);
        Task t = new Task();
        BeanUtils.copyProperties(te,t);
        return t;
    }

    @Transactional(readOnly = false)
    public Task remove(Task t) {
        TaskEntity te = taskRepository.remove(t.getId());
        BeanUtils.copyProperties(te,t);
        return t;
    }

    @Transactional(readOnly = true)
    public List<Task> findAll() {

        List <TaskEntity> list = taskRepository.findAll();
        List<Task> tasks = getTasks(list);

        return tasks;
    }

    // Eero 21.6.2016 location DataClass and Service missing, currently not assigned foe antyone
    // Do we even need the thing?
    //public List<Task> findAllByLocation(Location l);

    @Transactional(readOnly = true)
    public List<Task> findAllByCategory(Category c) {

        CategoryEntity ce = categoryRepository.find(c.getId());
        List <TaskEntity> list = taskRepository.findAllByCategory(ce);
        List<Task> tasks = getTasks(list);

        return tasks;
    }

    @Transactional(readOnly = true)
    public List<Task> findAllByCreator(WwwUser u) {

        UserEntity ue = userRepository.getUserById((int)(long)(u.getId()));
        List <TaskEntity> list = taskRepository.findAllByCreator(ue);
        List<Task> tasks = getTasks(list);

        return tasks;
    }

    @Transactional(readOnly = true)
    public List<Task> findAllByAssignee(WwwUser u) {

        UserEntity ue = userRepository.getUserById((int)(long)(u.getId()));
        List <TaskEntity> list = taskRepository.findAllByAssignee(ue);
        List<Task> tasks = getTasks(list);

        return tasks;
    }

    @Transactional(readOnly = true)
    public List<Task> findAllByPriority(Integer p) {

        List <TaskEntity> list = taskRepository.findAllByPriority(p);
        List<Task> tasks = getTasks(list);

        return tasks;
    }

    @Transactional(readOnly = true)
    public List<Task> findAllByFamily(WwwFamily f) {

        FamilyEntity fe = familyRepository.findFamily((int)(long)(f.getId()));
        List <TaskEntity> list = taskRepository.findAllByFamily(fe);
        List<Task> tasks = getTasks(list);

        return tasks;
    }

    @Transactional(readOnly = true)
    public List<Task> findAllByPrivacy(Boolean p) {

        List <TaskEntity> list = taskRepository.findAllByPrivacy(p);
        List<Task> tasks = getTasks(list);

        return tasks;
    }

    @Transactional(readOnly = true)
    public List<Task> findAllByStatus(Task.Status status) {

        TaskEntity.Status s1 = getStatus(status);
        List <TaskEntity> list = taskRepository.findAllByStatus(s1);
        List<Task> tasks = getTasks(list);

        return tasks;
    }

    @Transactional(readOnly = true)
    public List<Task> findAllByDueDate(DateTime start, DateTime end) {

        List <TaskEntity> list = taskRepository.findAllByDueDate(start, end);
        List<Task> tasks = getTasks(list);

        return tasks;
    }

    // Find all bt two fields:

    @Transactional(readOnly = true)
    public List<Task> findAllByCreatorAndPrivacy(WwwUser u, Boolean p) {

        UserEntity ue = userRepository.getUserById((int)(long)(u.getId()));
        List<TaskEntity> list = taskRepository.findAllByCreatorAndPrivacy(ue, p);
        List<Task> tasks = getTasks(list);

        return tasks;
    }

    @Transactional(readOnly = true)
    public List<Task> findAllByAssigneeAndPrivacy(WwwUser u, Boolean p) {

        UserEntity ue = userRepository.getUserById((int)(long)(u.getId()));
        List<TaskEntity> list = taskRepository.findAllByAssigneeAndPrivacy(ue, p);
        List<Task> tasks = getTasks(list);

        return tasks;
    }

    @Transactional(readOnly = true)
    public List<Task> findAllByCreatorAndStatus(WwwUser u, Task.Status status) {

        UserEntity ue = userRepository.getUserById((int)(long)(u.getId()));
        TaskEntity.Status s1 = getStatus(status);
        List<TaskEntity> list = taskRepository.findAllByCreatorAndStatus(ue, s1);
        List<Task> tasks = getTasks(list);

        return tasks;
    }

    @Transactional(readOnly = true)
    public List<Task> findAllByAssigneeAndStatus(WwwUser u, Task.Status status) {

        UserEntity ue = userRepository.getUserById((int)(long)(u.getId()));
        TaskEntity.Status s1 = getStatus(status);
        List <TaskEntity> list = taskRepository.findAllByAssigneeAndStatus(ue, s1);
        List<Task> tasks = getTasks(list);

        return tasks;
    }

    @Transactional(readOnly = true)
    public List<Task> findAllByFamilyAndCategory(WwwFamily f, Category c) {

        FamilyEntity fe = familyRepository.findFamily((int)(long)(f.getId()));
        CategoryEntity ce = categoryRepository.find((int)(long)(c.getId()));
        List <TaskEntity> list = taskRepository.findAllByFamilyAndCategory(fe, ce);
        List<Task> tasks = getTasks(list);

        return tasks;
    }

    @Transactional(readOnly = true)
    public List<Task> findAllByCreatorAndCategory(WwwUser u, Category c) {

        UserEntity ue = userRepository.getUserById((int)(long)(u.getId()));
        CategoryEntity ce = categoryRepository.find((int)(long)(c.getId()));
        List <TaskEntity> list = taskRepository.findAllByCreatorAndCategory(ue, ce);
        List<Task> tasks = getTasks(list);

        return tasks;
    }

    @Transactional(readOnly = true)
    public List<Task> findAllByAssigneeAndCategory(WwwUser u, Category c) {

        UserEntity ue = userRepository.getUserById((int)(long)(u.getId()));
        CategoryEntity ce = categoryRepository.find((int)(long)(c.getId()));
        List <TaskEntity> list = taskRepository.findAllByAssigneeAndCategory(ue, ce);
        List<Task> tasks = getTasks(list);

        return tasks;
    }

    @Transactional(readOnly = true)
    public List<Task> findAllByCreatorAndDueDate(WwwUser u, DateTime start, DateTime end) {

        UserEntity ue = userRepository.getUserById((int)(long)(u.getId()));
        List <TaskEntity> list = taskRepository.findAllByCreatorAndDueDate(ue, start, end);
        List<Task> tasks = getTasks(list);

        return tasks;
    }

    @Transactional(readOnly = true)
    public List<Task> findAllByAssigneeAndDueDate(WwwUser u, DateTime start, DateTime end) {

        UserEntity ue = userRepository.getUserById((int)(long)(u.getId()));
        List <TaskEntity> list = taskRepository.findAllByAssigneeAndDueDate(ue, start, end);
        List<Task> tasks = getTasks(list);

        return tasks;
    }

    // Find all by three parameters

    @Transactional(readOnly = true)
    public List<Task> findAllByCreatorAndCategoryAndStatus(WwwUser u, Category c, Task.Status s) {

        UserEntity ue = userRepository.getUserById((int)(long)(u.getId()));
        CategoryEntity ce = categoryRepository.find((int)(long)(c.getId()));
        TaskEntity.Status status = getStatus(s);
        List <TaskEntity> list = taskRepository.findAllByCreatorAndCategoryAndStatus(ue, ce, status);
        List<Task> tasks = getTasks(list);

        return tasks;
    }

    @Transactional(readOnly = true)
    public List<Task> findAllByAssigneeAndCategoryAndStatus(WwwUser u, Category c, Task.Status s) {

        UserEntity ue = userRepository.getUserById((int)(long)(u.getId()));
        CategoryEntity ce = categoryRepository.find((int)(long)(c.getId()));
        TaskEntity.Status status = getStatus(s);
        List <TaskEntity> list = taskRepository.findAllByAssigneeAndCategoryAndStatus(ue, ce, status);
        List<Task> tasks = getTasks(list);

        return tasks;
    }

    @Transactional(readOnly = true)
    public List<Task> findAllByCreatorAndStatusAndPrivacy(WwwUser u, Task.Status s, Boolean p) {

        UserEntity ue = userRepository.getUserById((int)(long)(u.getId()));
        TaskEntity.Status status = getStatus(s);
        List <TaskEntity> list = taskRepository.findAllByCreatorAndStatusAndPrivacy(ue, status, p);
        List<Task> tasks = getTasks(list);

        return tasks;
    }

    @Transactional(readOnly = true)
    public List<Task> findAllByAssigneeAndStatusAndPrivacy(WwwUser u, Task.Status s, Boolean p) {

        UserEntity ue = userRepository.getUserById((int)(long)(u.getId()));
        TaskEntity.Status status = getStatus(s);
        List <TaskEntity> list = taskRepository.findAllByAssigneeAndStatusAndPrivacy(ue, status, p);
        List<Task> tasks = getTasks(list);

        return tasks;
    }

    @Transactional(readOnly = true)
    public List<Task> findAllByFamilyAndCategoryAndPrivacy(WwwFamily f, Category c, Boolean p) {

        FamilyEntity fe = familyRepository.findFamily((int)(long)(f.getId()));
        CategoryEntity ce = categoryRepository.find((int)(long)(c.getId()));
        List <TaskEntity> list = taskRepository.findAllByFamilyAndCategoryAndPrivacy(fe, ce, p);
        List<Task> tasks = getTasks(list);

        return tasks;
    }

    @Transactional(readOnly = true)
    public List<Task> findAllByCreatorAndCategoryAndPrivacy(WwwUser u, Category c, Boolean p) {

        UserEntity ue = userRepository.getUserById((int)(long)(u.getId()));
        CategoryEntity ce = categoryRepository.find((int)(long)(c.getId()));
        List <TaskEntity> list = taskRepository.findAllByCreatorAndCategoryAndPrivacy(ue, ce, p);
        List<Task> tasks = getTasks(list);

        return tasks;
    }

    @Transactional(readOnly = true)
    public List<Task> findAllByAssigneeAndCategoryAndPrivacy(WwwUser u, Category c, Boolean p) {

        UserEntity ue = userRepository.getUserById((int)(long)(u.getId()));
        CategoryEntity ce = categoryRepository.find((int)(long)(c.getId()));
        List <TaskEntity> list = taskRepository.findAllByAssigneeAndCategoryAndPrivacy(ue, ce, p);
        List<Task> tasks = getTasks(list);

        return tasks;
    }

    // Find all by four parameters

    @Transactional(readOnly = true)
    public List<Task> findAllByCreatorAndCategoryAndStatusAndPrivacy(WwwUser u, Category c, Task.Status s, Boolean p) {

        UserEntity ue = userRepository.getUserById((int)(long)(u.getId()));
        CategoryEntity ce = categoryRepository.find((int)(long)(c.getId()));
        TaskEntity.Status status = getStatus(s);
        List <TaskEntity> list = taskRepository.findAllByCreatorAndCategoryAndStatusAndPrivacy(ue, ce, status, p);
        List<Task> tasks = getTasks(list);

        return tasks;
    }

    @Transactional(readOnly = true)
    public List<Task> findAllByAssigneeAndCategoryAndStatusAndPrivacy(WwwUser u, Category c, Task.Status s, Boolean p) {

        UserEntity ue = userRepository.getUserById((int)(long)(u.getId()));
        CategoryEntity ce = categoryRepository.find((int)(long)(c.getId()));
        TaskEntity.Status status = getStatus(s);
        List <TaskEntity> list = taskRepository.findAllByAssigneeAndCategoryAndStatusAndPrivacy(ue, ce, status, p);
        List<Task> tasks = getTasks(list);

        return tasks;
    }

}
