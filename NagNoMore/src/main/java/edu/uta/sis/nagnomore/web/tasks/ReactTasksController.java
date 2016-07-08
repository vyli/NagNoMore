package edu.uta.sis.nagnomore.web.tasks;

import edu.uta.sis.nagnomore.domain.data.Task;
import edu.uta.sis.nagnomore.domain.service.TaskService;
import org.apache.log4j.Logger;
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
    // TODO: ?add @RequestParam infront of Task ?
    @RequestMapping(value = "/react/tasks/create", method = RequestMethod.POST)
    @ResponseBody
    public boolean create(@Valid Task task, BindingResult bindingResult){
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
    public boolean update(@PathVariable("id") Integer id, @Valid Task task, BindingResult bindingResult){
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
}
/*




*/