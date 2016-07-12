package edu.uta.sis.nagnomore.web.testing;

import edu.uta.sis.nagnomore.data.entities.FamilyEntity;
import edu.uta.sis.nagnomore.data.entities.UserEntity;
import edu.uta.sis.nagnomore.data.repository.FamilyRepository;
import edu.uta.sis.nagnomore.data.repository.UserRepository;
import edu.uta.sis.nagnomore.domain.data.Category;
import edu.uta.sis.nagnomore.domain.data.Task;
import edu.uta.sis.nagnomore.domain.data.WwwFamily;
import edu.uta.sis.nagnomore.domain.data.WwwUser;
import edu.uta.sis.nagnomore.domain.service.CategoryService;
import edu.uta.sis.nagnomore.domain.service.FamilyService;
import edu.uta.sis.nagnomore.domain.service.TaskService;
import edu.uta.sis.nagnomore.domain.service.UserService;
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
    FamilyService fs;

    @Autowired
    UserService us;

    @Autowired
    UserRepository ur;


    @Autowired
    TestCategoryController tcc;

    @RequestMapping("/taskadd")
    public String taskadd() {

        System.out.println("Starting task testing.");

        // We need at least 1 family for this test
        WwwFamily f = new WwwFamily();
        f.setFamilyName("TestFamily123");
        fs.addFamily(f);

        // We need two users on same family for this test
        // Pass null for id; it's automatically generated
        WwwUser u1 = new WwwUser(null, "Raimo", "12345", "raipe@huu.haa", "Raimo Rujo",  "123456789", "ROLE_CHILD", true);
        //u1.setRole("ROLE_CHILD");
        u1.setFamily(f);
        us.create(u1);


        WwwUser u2 = new WwwUser(null, "Ulla", "12345", "ulla@huu.haa", "Ulla Rujo",  "123456789", "ROLE_PARENT", true);
        //u2.setUsername("Test User Parent");
        //u2.setRole("ROLE_PARENT");
        u2.setFamily(f);
        us.create(u2);

        //Empty the Category-table
        tcc.test2();
        // Create 3 test categories
        tcc.test1();

        DateTime dt = DateTime.now();
        DateTime due = dt.plusWeeks(2);

        List<Category> catlist = cs.getCategories();
        Category cat = catlist.get(1);

        this.createTestTask(
                "Test task 1",
                "This is a task created by TestTaskController",
                dt, due, 0, false, true, cat, u1, u2, f, Task.Status.COMPLETED

        );

        /*
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
        */

        // Cleanup
        //us.remove(u1.getId());
        //us.remove(u2.getId());
        //fs.removeFamily(f.getId());

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

    private void createTestTask(String title, String description,
                                DateTime created, DateTime due,
                                int priority, Boolean privacy,
                                Boolean alarm, Category category,
                                WwwUser creator, WwwUser assignee,
                                WwwFamily family, Task.Status status
                                 ) {

        Task t = new Task();
        t.setTitle(title);
        t.setDescription(description);
        t.setCreated(created);
        t.setDue(due);
        t.setPriority(priority);
        t.setPrivacy(privacy);
        t.setAlarm(alarm);
        t.setCategory(category);
        t.setCreator(creator);
        t.setAssignee(assignee);
        t.setStatus(status);
        t.setFamily(family);

        taskService.addTask(t);
    }

}
