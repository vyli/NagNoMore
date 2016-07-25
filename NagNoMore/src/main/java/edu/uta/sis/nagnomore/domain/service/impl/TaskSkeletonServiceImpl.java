package edu.uta.sis.nagnomore.domain.service.impl;

import edu.uta.sis.nagnomore.domain.data.*;
import edu.uta.sis.nagnomore.domain.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by mare on 25.7.2016.
 */
@Service
public class TaskSkeletonServiceImpl implements TaskSkeletonService {

    @Autowired
    TaskService taskService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;
    @Autowired
    FamilyService familyService;



    public Task convertToTask(TaskSkeleton ts) {

        Task task = new Task();

        Integer categoryId = ts.getCategoryId();
        Category category = null;
        if(categoryId != null){
            category = categoryService.get(categoryId);
        }

        Integer familyId = ts.getFamilyId();
        WwwFamily family = null;
        if(categoryId != null){
            family = familyService.findFamily(familyId);
        }

        Integer creatorId = ts.getCreatorId();
        WwwUser creator = null;
        if(creatorId != null){
            creator = userService.getUserById((long)creatorId);
        }

        Integer assigneeId = ts.getAssigneeId();
        WwwUser assignee = null;
        if(assigneeId != null){
            assignee = userService.getUserById((long)assigneeId);
        }

        String statusId = ts.getStatus();
        Task.Status taskStatus = null;
        if(statusId.equals("NEEDS_ACTION")){
            taskStatus = Task.Status.NEEDS_ACTION;
        }
        else if(statusId.equals("IN_PROGRESS")) {
            taskStatus = Task.Status.IN_PROGRESS;
        }else if(statusId.equals("COMPLETED")) {
            taskStatus = Task.Status.COMPLETED;
        }



        task.setId(ts.getId());
        task.setTitle(ts.getTitle());
        task.setDescription(ts.getDescription());
        task.setCreated(ts.getCreated());
        task.setDue(ts.getDue());
        task.setPriority(ts.getPriority());
        task.setPrivacy(ts.getPrivacy());
        task.setAlarm(ts.getAlarm());
        task.setCategory(category);
        task.setFamily(family);
        task.setCreator(creator);
        task.setAssignee(assignee);
        task.setStatus(taskStatus);




        return task;

    }
}
