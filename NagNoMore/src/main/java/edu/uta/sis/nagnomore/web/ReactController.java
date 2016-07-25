package edu.uta.sis.nagnomore.web;

import edu.uta.sis.nagnomore.domain.data.*;
import edu.uta.sis.nagnomore.domain.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by mare on 24.7.2016.
 */


@Controller
public class ReactController {

    @Autowired
    CategoryService cs;

    @Autowired
    FamilyService familyService;

    @Autowired
    UserService us;

    @Autowired
    TaskService ts;

    @Autowired
    TaskSkeletonService tss;

    @RequestMapping(value="/react/categories")
    public @ResponseBody
    List<Category> getCategories(){

        List<Category> list = cs.getCategories();

        return list;
    }

    @RequestMapping(value="/react/families")
    public @ResponseBody List<WwwFamily> getFamilies(){

        List<WwwFamily> list = familyService.listAllFamilies();

        return list;
    }

    @RequestMapping(value="/react/familyMembers")
    public @ResponseBody List<WwwUser> getFamilyMembers(){

        List<WwwUser> list = us.getUsers();

        return list;
    }

    @RequestMapping(value="/react/addtask", method = RequestMethod.POST)

    public @ResponseBody List<Task> addTask(@RequestBody TaskSkeleton taskSkeleton){

        Task task = tss.convertToTask(taskSkeleton);
        ts.addTask(task);
        List<Task> list = ts.findAll();
        return list;
    }

    //Taskille on oman ReactTasksController



}
