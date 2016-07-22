package edu.uta.sis.nagnomore.domain.service.impl;

import edu.uta.sis.nagnomore.data.entities.*;
import edu.uta.sis.nagnomore.data.repository.CategoryRepository;
import edu.uta.sis.nagnomore.data.repository.FamilyRepository;
import edu.uta.sis.nagnomore.data.repository.TaskRepository;
import edu.uta.sis.nagnomore.data.repository.UserRepository;
import edu.uta.sis.nagnomore.domain.data.*;
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

        ReminderEntity re = new ReminderEntity();
        CategoryEntity ce = new CategoryEntity();
        Category c = new Category();
        Reminder r = new Reminder();
        UserEntity ue = new UserEntity();
        UserEntity ue2 = new UserEntity();
        WwwUser u = new WwwUser();
        WwwUser u2 = new WwwUser();
        WwwFamily f = new WwwFamily();



        for (TaskEntity te: list) {

            Task t = new Task();
            tasks.add(t);

            re = te.getReminder();
            if(re != null) {
                BeanUtils.copyProperties(re, r);
                t.setReminder(r);
            }

            ce = te.getCategory();
            if (ce != null) {
                BeanUtils.copyProperties(ce, c);
                t.setCategory(c);
            }

            if (te.getFamily() != null) {
                FamilyEntity fe = familyRepository.findFamily(te.getFamily().getId());
                BeanUtils.copyProperties(fe, f);
                t.setFamily(f);
            }

            ue = userRepository.getUserById(te.getCreator().getId().intValue());
            long uid = ue.getId();


            FamilyEntity fe1 = ue.getFamily();
            WwwFamily wf= new WwwFamily();
            BeanUtils.copyProperties(fe1, wf);
            u.setFamily(wf);
            u.setId(uid);
            BeanUtils.copyProperties(ue, u);
            t.setCreator(u);

            ue2 = userRepository.getUserById(te.getAssignee().getId().intValue());
            FamilyEntity fe2 = ue2.getFamily();
            WwwFamily wf2= new WwwFamily();
            BeanUtils.copyProperties(fe2, wf2);
            u.setFamily(wf2);

            BeanUtils.copyProperties(ue2, u2);
            t.setAssignee(u2);

            TaskEntity.Status tes = te.getStatus();
            Task.Status ts = null;
            switch (tes) {
                case NEEDS_ACTION:
                    ts = Task.Status.NEEDS_ACTION;
                    break;
                case IN_PROGRESS:
                    ts = Task.Status.IN_PROGRESS;
                    break;
                case COMPLETED:
                    ts = Task.Status.COMPLETED;
                    break;
            }

            t.setStatus(ts);

            Reminder reminder = new Reminder();
            ReminderEntity rem = te.getReminder();
            if(rem != null) {
                BeanUtils.copyProperties(rem, reminder);
                t.setReminder(reminder);
            }

            BeanUtils.copyProperties(te,t);
        }
        return tasks;
    }

    // Helper for common logic in create and update
    private void HandleCreateUpdateTask(Task t, Boolean doUpdate) {
        TaskEntity te = new TaskEntity();
        BeanUtils.copyProperties(t,te);

        ReminderEntity re = new ReminderEntity();
        Reminder r = t.getReminder();
        if(r != null) {
            BeanUtils.copyProperties(r, re);
            te.setReminder(re);
        }

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

        ReminderEntity rem = new ReminderEntity();
        Reminder reminder = t.getReminder();
        if(reminder != null) {
            BeanUtils.copyProperties(reminder, rem);
            te.setReminder(rem);
        }


        if(doUpdate)
            taskRepository.updateTask(te);
        else
            taskRepository.addTask(te);

        BeanUtils.copyProperties(te,t);
    }

    @Transactional
    public void updateAlarms(){
        List<Task> suspects = findAllWithOverdueReminders();
        Task t = new Task();
        Reminder r = new Reminder();
        Boolean alarm = false;
        DateTime dt = null;

        for(int i = 0; i < suspects.size(); i++) {
            t = suspects.get(i);
            TaskEntity te = taskRepository.find(t.getId());

            ReminderEntity re = te.getReminder();
            alarm = t.getAlarm();
            if (re != null && !alarm){
                dt = re.getTime();
                boolean b = dt.isBeforeNow();
                if(b){
                    // Alarm needs to be updated
                    t.setAlarm(true);
                    BeanUtils.copyProperties(t,te);
                    taskRepository.updateTask(te);
                }

            }
        }
    }

    @Transactional(readOnly = false)
    public void addTask(Task t) {

        HandleCreateUpdateTask(t, false);
    }

    @Transactional(readOnly = false)
    public void updateTask(Task t) {

        HandleCreateUpdateTask(t, true);
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

    @Transactional(readOnly = true)
    public List<Task> findAllTasksWithReminders() {

        List <TaskEntity> list = taskRepository.findAllTasksWithReminders();
        List<Task> tasks = getTasks(list);

        return tasks;
    }

    @Transactional(readOnly = true)
    public List<Task> findAllOverdue() {

        List <TaskEntity> list = taskRepository.findAllOverdue();
        List<Task> tasks = getTasks(list);

        return tasks;
    }

    @Transactional(readOnly = true)
    public List<Task> findAllWithOverdueReminders() {

        List <TaskEntity> list = taskRepository.findAllWithOverdueReminders();
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

    @Transactional(readOnly = true)
    public List<Task> findAllOverdueByAssignee(WwwUser u) {
        UserEntity ue = userRepository.getUserById((int)(long)(u.getId()));
        List <TaskEntity> list = taskRepository.findAllOverdueByAssignee(ue);
        List<Task> tasks = getTasks(list);

        return tasks;
    }

    @Transactional(readOnly = true)
    public List<Task> findAllWithOverdueRemindersByAssignee(WwwUser u) {
        UserEntity ue = userRepository.getUserById((int)(long)(u.getId()));
        List <TaskEntity> list = taskRepository.findAllWithOverdueRemindersByAssignee(ue);
        List<Task> tasks = getTasks(list);

        return tasks;
    }

    @Transactional(readOnly = true)
    public List<Task> findAllByFamilyAndPrivacy(WwwFamily f, Boolean p) {

        FamilyEntity fe = familyRepository.findFamily((int)(long)(f.getId()));
        List<TaskEntity> list = taskRepository.findAllByFamilyAndPrivacy(fe, p);
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
