package edu.uta.sis.nagnomore.web.tasks;

import edu.uta.sis.nagnomore.domain.data.Task;
import edu.uta.sis.nagnomore.domain.data.WwwFamily;
import edu.uta.sis.nagnomore.domain.data.WwwUser;
import edu.uta.sis.nagnomore.domain.data.Category;
import edu.uta.sis.nagnomore.domain.service.CategoryService;
import edu.uta.sis.nagnomore.domain.service.FamilyService;
import edu.uta.sis.nagnomore.domain.service.TaskService;
import edu.uta.sis.nagnomore.domain.service.UserService;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Hanna on 7.7.2016.
 */
@Controller
public class ReactTasksController {

    @Autowired
    TaskService taskService;

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    FamilyService familyService;

    Logger logger = Logger.getLogger(this.getClass().getName());

    @RequestMapping("/react/tasks")
    public @ResponseBody
    List<Task> listTasks(){

        return taskService.findAll();
    }
    @RequestMapping (value = "/react/tasks/create", method = RequestMethod.GET)
    @ResponseBody
    public Task create(){
        logger.debug("Create Task - Get - react");
        Task task = new Task();
        return task;
    }

    //in case of Task binding result errors return false
    @RequestMapping(value = "/react/tasks/create", method = RequestMethod.POST)
    @ResponseBody
    public boolean create(@RequestBody @Valid Task task, BindingResult bindingResult){
        logger.debug("Create Task - Post - react");
        if(bindingResult.hasErrors()){
            logger.debug("Create Task bindingResult.hasErrors - react");
            return false;
        }
        taskService.addTask(task);
        return true;
    }

    @RequestMapping(value = "/react/tasks/update/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Task update(@PathVariable("id") Integer id){
        logger.debug("Update GET - react Task " + id);
        Task task = taskService.find(id);
        return task;
    }

    //in case of Task binding result errors return false
    // TODO: ?add @RequestParam infront of Task ?
    @RequestMapping(value = "/react/tasks/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public boolean update(@PathVariable("id") Integer id,@RequestBody @Valid Task task, BindingResult bindingResult){
        logger.debug("Update POST -react Task " + id);
        if(bindingResult.hasErrors()){
            logger.debug("Update POST Task " + id + "bindingResult.hasErrors");
            return false;
        }
        task.setId(id);
        taskService.updateTask(task);
        return true;
    }


    @RequestMapping(value = "/react/tasks/remove/{id}",method = RequestMethod.GET)
    @ResponseBody
    public void remove(@PathVariable ("id") Integer id){
        logger.debug("Remove - react - Task " + id);
        taskService.remove(taskService.find(id));
        return;
    }


    @RequestMapping(value = "/react/tasks/read/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Task read(@PathVariable("id") Integer id){
        logger.debug("Read - react - Task " + id);
        Task task = taskService.find(id);
        return task;
    }

    //find
    @RequestMapping(value = "/react/tasks/findByCategory/{categoryId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Task> listTasksByCategory(@PathVariable Integer categoryId){
        logger.debug("find - react - Tasks  - Category");
        Category c = categoryService.get(categoryId);
        List<Task> list = taskService.findAllByCategory(c);
        return list;
    }

    @RequestMapping(value = "/react/tasks/findByDuedate", method = RequestMethod.GET)
    @ResponseBody
    public List<Task> listTasksByDueDate(@RequestBody DateTime start, @RequestBody DateTime end){
        logger.debug("find - react - Tasks - DueDate");
        List<Task> list = taskService.findAllByDueDate(start, end);
        return list;
    }
    @RequestMapping(value = "/react/tasks/findByStatus", method = RequestMethod.GET)
    @ResponseBody
    public List<Task> listTasksByStatus(@RequestBody Task.Status status){
        logger.debug("find - react - Tasks - Status");
        List<Task> list = taskService.findAllByStatus(status);
        return list;
    }

    @RequestMapping(value = "/react/tasks/findByPriority/{priority}", method = RequestMethod.GET)
    @ResponseBody
    public List<Task> listTasksByDueDate(@PathVariable Integer priority){
        logger.debug("find - react - Tasks  - Priority");
        List<Task> list = taskService.findAllByPriority(priority);
        return list;
    }
    @RequestMapping(value = "/react/tasks/findByPrivacy", method = RequestMethod.GET)
    @ResponseBody
    public List<Task> listTasksByDueDate(@RequestParam boolean privacy){
        logger.debug("find - react - Tasks  - Privacy");
        List<Task> list = taskService.findAllByPrivacy(privacy);
        return list;
    }


    @RequestMapping(value = "/react/tasks/findByAssignee/{aId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Task> listTasksByAssignee(@PathVariable long aId, @RequestParam(required=false) Integer categoryId,
                                          @RequestBody(required=false) Task.Status status,
                                          @RequestParam(required=false) Boolean privacy){
        logger.debug("find - react - Tasks - Assignee");
        List<Task> list;
        WwwUser u = userService.getUserById(aId);
        if(categoryId != null) {
            logger.debug(" & Category");
            Category c = categoryService.get(categoryId);
            if(status != null){
                logger.debug(" & Status");
                if(privacy != null) {
                    logger.debug(" & Privacy");
                    list = taskService.findAllByAssigneeAndCategoryAndStatusAndPrivacy(u, c, status, privacy);
                }else{  // "privacy" is not there
                    logger.debug("find - react - Tasks - Assignee & Category & Status");
                    list = taskService.findAllByAssigneeAndCategoryAndStatus(u,c,status);
                }
            }else{ // "status" is not there
                if(privacy != null){
                    logger.debug("find - react - Tasks - Assignee & Category & Privacy");
                    list =taskService.findAllByAssigneeAndCategoryAndPrivacy(u,c,privacy);
                }else{ // status& Privacy is not there
                    logger.debug("find - react - Tasks- Assignee & Category");
                    list= taskService.findAllByAssigneeAndCategory(u,c);
                }
            }
        } else {// category is not there
            if(status != null){
                logger.debug(" & Status");
                if(privacy != null) {
                    logger.debug(" & Privacy");
                    logger.debug("find - react - Tasks - Assignee & Status & Privacy");
                    list = taskService.findAllByAssigneeAndStatusAndPrivacy(u, status, privacy);
                }else{  // "privacy" &category is not there
                    logger.debug("find - react - Tasks - Assignee & Status");
                    list = taskService.findAllByAssigneeAndStatus(u,status);
                }
            }else{ // "status"&category is not there
                if(privacy != null){
                    logger.debug("find - react - Tasks - Assignee & Privacy");
                    list =taskService.findAllByAssigneeAndPrivacy(u,privacy);
                }else{ // category& status& Privacy is not there
                    logger.debug("find - react - Tasks - Assignee - all");
                    list= taskService.findAllByAssignee(u);
                }
            }
        }
        return list;
    }

    @RequestMapping(value = "/react/tasks/findByAssigneeAndDuedate/{aId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Task> listTasksByAssignee(@PathVariable long aId,@RequestBody DateTime start,@RequestBody DateTime end){
        logger.debug("find - react - Tasks - Assignee & DueDate");
        WwwUser u = userService.getUserById(aId);
        List<Task> list = taskService.findAllByAssigneeAndDueDate(u, start,end);
        return list;
    }


    // byCreator
    @RequestMapping(value = "/react/tasks/findByCreator/{cId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Task> listTasksByCreator(@PathVariable long cId, @RequestParam(required=false) Integer categoryId,
                                          @RequestBody(required=false) Task.Status status,
                                          @RequestParam(required=false) Boolean privacy){
        logger.debug("find - react - Tasks - Creator");
        List<Task> list;
        WwwUser u = userService.getUserById(cId);
        if(categoryId != null) {
            logger.debug(" & Category");
            Category c = categoryService.get(categoryId);
            if(status != null){
                logger.debug(" & Status");
                if(privacy != null) {
                    logger.debug(" & Privacy");
                    list = taskService.findAllByCreatorAndCategoryAndStatusAndPrivacy(u, c, status, privacy);
                }else{  // "privacy" is not there
                    logger.debug("find - react - Tasks - Creator & Category & Status");
                    list = taskService.findAllByCreatorAndCategoryAndStatus(u,c,status);
                }
            }else{ // "status" is not there
                if(privacy != null){
                    logger.debug("find - react - Tasks - Creator & Category & Privacy");
                    list =taskService.findAllByCreatorAndCategoryAndPrivacy(u,c,privacy);
                }else{ // status& Privacy is not there
                    logger.debug("find - react - Tasks- Creator & Category");
                    list= taskService.findAllByCreatorAndCategory(u,c);
                }
            }
        } else {// category is not there
            if(status != null){
                logger.debug(" & Status");
                if(privacy != null) {
                    logger.debug(" & Privacy");
                    logger.debug("find - react - Tasks - Creator & Status & Privacy");
                    list = taskService.findAllByCreatorAndStatusAndPrivacy(u, status, privacy);
                }else{  // "privacy" &category is not there
                    logger.debug("find - react - Tasks - Creator & Status");
                    list = taskService.findAllByCreatorAndStatus(u,status);
                }
            }else{ // "status"&category is not there
                if(privacy != null){
                    logger.debug("find - react - Tasks - Creator & Privacy");
                    list =taskService.findAllByCreatorAndPrivacy(u,privacy);
                }else{ // category& status& Privacy is not there
                    logger.debug("find - react - Tasks - Creator - all");
                    list= taskService.findAllByCreator(u);
                }
            }
        }
        return list;
    }

    @RequestMapping(value = "/react/tasks/findByCreatorAndDuedate/{cId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Task> listTasksByCreator(@PathVariable long cId,@RequestBody DateTime start,@RequestBody DateTime end){
        logger.debug("find - react - Tasks  - Creator & DueDate");
        WwwUser u = userService.getUserById(cId);
        List<Task> list = taskService.findAllByCreatorAndDueDate(u, start,end);
        return list;
    }

    // find by family
    @RequestMapping(value = "/react/tasks/findByFamily/{fId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Task> listTasksByFamily(@PathVariable Integer fId, @RequestParam(required=false)Integer cId,
                                        @RequestParam(required=false) Boolean privacy){
        logger.debug("find - react - Tasks  - Family");
        // List<Task> list;
        if(cId != null){
            Category c = categoryService.get(cId);
            if(privacy != null){//
                logger.debug("find - react - Tasks  - Family & Category & Privacy");
                return taskService.findAllByFamilyAndCategoryAndPrivacy(familyService.findFamily(fId),c,privacy);
            }
            logger.debug("find - react - Tasks  - Family & Category");
            return taskService.findAllByFamilyAndCategory(familyService.findFamily(fId),c);
        } else if(cId == null && privacy== null) {
            logger.debug("find - react - Tasks  - Family");
            return taskService.findAllByFamily(familyService.findFamily(fId));
        }
        // TODO ??: taskService.findAllByFamilyAndPrivacy ??
        logger.debug("TODO ?? : find - react - Tasks  - Family & Privacy = returns NULL");
        return null;
    }

}
