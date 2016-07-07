package edu.uta.sis.nagnomore.web.tasks;

import edu.uta.sis.nagnomore.domain.data.Task;
import edu.uta.sis.nagnomore.domain.service.TaskService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Hanna on 7.7.2016.
 */
public class ReactTasksController {

    @Autowired
    TaskService taskService;

    Logger logger = Logger.getLogger(this.getClass().getName());

    @RequestMapping("/tasks/react")
    public @ResponseBody
    List<Task> listTasks(){

        return taskService.findAll();
    }
}
