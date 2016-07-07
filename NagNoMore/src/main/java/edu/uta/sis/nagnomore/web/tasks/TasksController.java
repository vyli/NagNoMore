package edu.uta.sis.nagnomore.web.tasks;

import edu.uta.sis.nagnomore.domain.data.Task;
import edu.uta.sis.nagnomore.domain.service.TaskService;
import edu.uta.sis.nagnomore.domain.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by Hanna on 7.7.2016.
 */
@Controller
public class TasksController {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    TaskService taskService;

    @RequestMapping (value = {"/tasks"},method = RequestMethod.GET)
    public String list(Model model){
        logger.debug("Tasks List");
        model.addAttribute("tasks",taskService.findAll());
        return "/tasks";
    }

    @RequestMapping (value = {"/tasks/create"},method = RequestMethod.GET)
    public String create(Model model){
        logger.debug("Create Task - Get");
        model.addAttribute("task",new Task());
        return "/tasks/create";
    }

    @RequestMapping (value = {"/tasks/create"},method = RequestMethod.POST)
    public String create(Model model, @Valid @ModelAttribute("task") Task task, BindingResult bindingResult){
        logger.debug("Create Task - Post");
        if(bindingResult.hasErrors()){
            logger.debug("Create Task bindingResult.hasErrors");
            return "/tasks";
        }
        taskService.addTask(task);
        return "redirect:/tasks";
    }

    @RequestMapping(value = "/tasks/update/{id}",method = RequestMethod.GET)
    public String update(@PathVariable("id") Integer id,Model model){
        logger.debug("Update GET Task " + id);
        Task task = taskService.find(id);
        model.addAttribute("task",task);
        return "/tasks/update/" + id;
    }

    @RequestMapping(value = "/tasks/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable("id") Integer id, Model model, @Valid @ModelAttribute("task")Task task, BindingResult bindingResult){
        logger.debug("Update POST Task " + id);
        if(bindingResult.hasErrors()){
            logger.debug("Update POST Task " + id + "bindingResult.hasErrors");
            return "/task/update/" + id;
        }
        task.setId(id);
        taskService.updateTask(task);
        return "redirect:/tasks";
    }

    @RequestMapping(value = "/tasks/remove/{id}",method = RequestMethod.GET)
    public String remove(@PathVariable ("id") Integer id){
        logger.debug("Remove Task " + id);
        taskService.remove(taskService.find(id));
        return "redirect:/tasks";
    }

    @RequestMapping(value = "/tasks/read/{id}", method = RequestMethod.GET)
    public String read(@PathVariable("id") Integer id, Model model){
        logger.debug("Read Task " + id);
        Task task = taskService.find(id);
        model.addAttribute("task",task);
        return "/tasks/read";
    }


}
