package edu.uta.sis.nagnomore.web.testing;

import edu.uta.sis.nagnomore.data.entities.FamilyEntity;
import edu.uta.sis.nagnomore.data.entities.UserEntity;
import edu.uta.sis.nagnomore.data.repository.FamilyRepository;
import edu.uta.sis.nagnomore.data.repository.UserRepository;
import edu.uta.sis.nagnomore.domain.data.Task;
import edu.uta.sis.nagnomore.domain.service.CategoryService;
import edu.uta.sis.nagnomore.domain.service.TaskService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Eero Asunta on 5.7.2016.
 */
@Controller
public class TestTaskController {

    @Autowired
    TaskService taskService;

    @Autowired
    CategoryService cs;

    @Autowired
    UserRepository ur;

    @Autowired
    FamilyRepository fr;

    @Autowired
    TestCategoryController tcc;

    @RequestMapping("/taskadd")
    public String taskadd() {

        System.out.println("Starting task testing.");

        // We need at least 1 family for this test
        FamilyEntity fe = new FamilyEntity();
        fe.setFamilyName("TestFamily123");
        fr.addFamily(fe);

        // We need two users on same family for this test
        UserEntity ue1 = new UserEntity();
        ue1.setUsername("Test User Child");
        ue1.setRole("ROLE_CHILD");
        ue1.setFamily(fe);
        ur.create(ue1);

        UserEntity ue2 = new UserEntity();
        ue2.setUsername("Test User Parent");
        ue2.setRole("ROLE_PARENT");
        ue2.setFamily(fe);
        ur.create(ue2);

        //Empty the Category-table
        tcc.test2();
        // Create 3 test categories
        tcc.test1();

        Task t = new Task();
        t.setTitle("Title: Test Task number 1");
        t.setDescription("Desc: Test Description number 1");
        DateTime dt = DateTime.now();
        t.setCreated(dt);
        DateTime due = dt.plusWeeks(2);
        t.setDue(due);
        t.setPriority(1);
        t.setPrivacy(false);
        t.setAlarm(false);
        t.setCategory(cs.get(1));
        t.setCreator(ue2.getFullName());
        t.setAssignee(ue1.getFullName());
        Task.Status status = Task.Status.COMPLETED;
        t.setStatus(status);

        taskService.addTask(t);

        return "/home";
    }

    @RequestMapping("/taskempty")
    public String taskempty() {

        List<Task> tasklist = taskService.findAll(); // This tests findAll()

        int size = tasklist.size();

        System.out.println("Removing " + size + " entries from Task table.");

        if(size>0) {

            for(int i=0; i<size; i++) {
                taskService.remove(tasklist.get(i)); // This tests remove()
                System.out.println("Removing: " + i);
            }
        }
        return "/home";
    }

    @RequestMapping("/task3")
    public String test3() {

        return "/home";
    }

}
