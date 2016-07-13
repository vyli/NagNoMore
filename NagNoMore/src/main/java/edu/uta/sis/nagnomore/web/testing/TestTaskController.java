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

    @RequestMapping("/test/taskadd")
    public String taskadd() {

        System.out.println("Starting task testing.");

        // We need at least 1 family for this test
        WwwFamily f = new WwwFamily();
        f.setFamilyName("TestFamily123");
        fs.addFamily(f);

        // We need two users on same family for this test
        // Pass null for id; it's automatically generated
        WwwUser u1 = new WwwUser(null, "Raimo", "12345", "raipe@huu.haa", "Raimo Rujo",  "123456789", "ROLE_CHILD", true);
        u1.setFamily(f);
        us.create(u1);
        WwwUser u2 = new WwwUser(null, "Ulla", "12345", "ulla@huu.haa", "Ulla Rujo",  "123456789", "ROLE_PARENT", true);
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

        // Modify = false
        this.createTestTask(
                "Test task 1",
                "This is a task created by TestTaskController",
                dt, due, 0, false, true, cat, u1, u2, f, Task.Status.COMPLETED, false

        );

        System.out.println("Set breakpoint here to use phpMyAdmin to check DB before Task update.");

        // Change Category, Status, Title and Description
        cat = catlist.get(0);

        // Modify = true
        this.createTestTask(
                "Test task modified",
                "This is a task created and modified by TestTaskController",
                dt, due, 0, false, true, cat, u1, u2, f, Task.Status.NEEDS_ACTION, true

        );

        System.out.println("Set breakpoint here to use phpMyAdmin to check DB before cleanup.");

        // Cleanup

        // Remove all tasks
        taskempty();

        // Remove users created for this test
        if(u1 != null) {
            us.remove(u1.getId());
        }
        if(u2 != null) {
            us.remove(u2.getId());
        }

        //Remove Families created for this test
        if(f != null) {
            fs.removeFamily(f.getId());
        }

        return "/home";
    }

    @RequestMapping("/test/taskempty")
    public String taskempty() {

        List<Task> tasklist = taskService.findAll(); // This tests findAll()

        int size = tasklist.size();

        System.out.println("Removing " + size + " entries from Task table.");


        for(int i=0; i<size; i++) {
            taskService.remove(tasklist.get(i)); // This tests remove()
            System.out.println("Removing: " + i);
        }
        return "/home";
    }


    @RequestMapping("/test/find")
    public String findallby() {

        // Delete all tasks before starting
        taskempty();

        // We need at least 2 families for this test
        WwwFamily f = new WwwFamily();
        f.setFamilyName("TestFamily123");
        fs.addFamily(f);
        WwwFamily f1 = new WwwFamily();
        f1.setFamilyName("Secoondary family of idiots");
        fs.addFamily(f1);

        // We need 4 users on same family for this test
        // Pass null for id; it's automatically generated
        WwwUser u1 = new WwwUser(null, "Raimo", "12345", "raipe@huu.haa", "Raimo Rujo",  "123456789", "ROLE_PARENT", true);
        u1.setFamily(f);
        us.create(u1);
        WwwUser u2 = new WwwUser(null, "Ulla", "12345", "ulla@huu.haa", "Ulla Rujo",  "123456789", "ROLE_PARENT", true);
        u2.setFamily(f1);
        us.create(u2);
        WwwUser u3 = new WwwUser(null, "Ville", "12345", "ville@huu.haa", "Ville Rujo",  "123456789", "ROLE_CHILD", true);
        u3.setFamily(f1);
        us.create(u3);
        WwwUser u4 = new WwwUser(null, "Sirpa", "12345", "sirpa@huu.haa", "Sirpa Rujo",  "123456789", "ROLE_CHILD", true);
        u4.setFamily(f);
        us.create(u4);

        //Empty the Category-table
        tcc.test2();
        // Create 3 test categories
        tcc.test1();

        DateTime dt = DateTime.now();
        DateTime due1 = dt.plusWeeks(2);
        DateTime due2 = dt.plusWeeks(3);
        DateTime due3 = dt.plusWeeks(4);
        DateTime start = due2.minusDays(1);
        DateTime end = due2.plusDays(1);

        List<Category> catlist = cs.getCategories();
        Category cat1 = catlist.get(0);
        Category cat2 = catlist.get(1);
        Category cat3 = catlist.get(2);

        // Modify = false
        this.createTestTask(
                "Test task 1",
                "This is a task has due date creation + 2 weeks",
                dt, due1, 0, false, true, cat1, u1, u3, f, Task.Status.COMPLETED, false
        );
        this.createTestTask(
                "Test task 2",
                "This is a task has due date creation + 3 weeks",
                dt, due2, 1, true, false, cat2, u1, u3, f1, Task.Status.NEEDS_ACTION, false
        );
        this.createTestTask(
                "Test task 3",
                "This is a task has due date creation + 3 weeks",
                dt, due2, 1, false, true, cat2, u3, u1, f, Task.Status.IN_PROGRESS, false
        );
        this.createTestTask(
                "Test task 4",
                "This is a task has due date creation + 4 weeks",
                dt, due3, 1, true, true, cat1, u2, u4, f1, Task.Status.IN_PROGRESS, false
        );
        this.createTestTask(
                "Test task 5",
                "This is a task has due date creation + 4 weeks",
                dt, due3, 1, false, false, cat1, u4, u1, f, Task.Status.COMPLETED, false
        );

        List<Task> results = null;

        results = taskService.findAll();
        System.out.println("findAll() should find 5 tasks. Found: " +results.size());
        results = taskService.findAllByAssignee(u3);
        System.out.println("findAllByAssignee() should find 2 tasks. Found: " +results.size());
        results = taskService.findAllByCreator(u2);
        System.out.println("findAllByCreator() should find 1 tasks. Found: " +results.size());
        results = taskService.findAllByCategory(cat1);
        System.out.println("findAllByCategory() should find 3 tasks. Found: " +results.size());
        results = taskService.findAllByStatus(Task.Status.IN_PROGRESS);
        System.out.println("findAllByStatus() should find 2 tasks. Found: " +results.size());
        results = taskService.findAllByPriority(1);
        System.out.println("findAllByPriority() should find 4 tasks. Found: " +results.size());
        results = taskService.findAllByFamily(f1);
        System.out.println("findAllByFamily() should find 2 tasks. Found: " +results.size());
        results = taskService.findAllByPrivacy(false);
        System.out.println("findAllByPrivacy() should find 3 tasks. Found: " +results.size());
        results = taskService.findAllByDueDate(start, end);
        System.out.println("findAllByDueDate() should find 2 tasks. Found: " +results.size());

        System.out.println("Breakpoint place holder");

        // Cleanup

        // Remove all tasks
        taskempty();

        // Remove users created for this test
        if(u1 != null) {
            us.remove(u1.getId());
        }
        if(u2 != null) {
            us.remove(u2.getId());
        }
        if(u3 != null) {
            us.remove(u3.getId());
        }
        if(u4 != null) {
            us.remove(u4.getId());
        }

        //Remove Families created for this test
        if(f != null) {
            fs.removeFamily(f.getId());
        }
        if(f1 != null) {
            fs.removeFamily(f1.getId());
        }

        return "/home";
    }

    @RequestMapping("/test/test")
    public String test4() {

        return "/home";
    }

    private void createTestTask(String title, String description,
                                DateTime created, DateTime due,
                                int priority, Boolean privacy,
                                Boolean alarm, Category category,
                                WwwUser creator, WwwUser assignee,
                                WwwFamily family, Task.Status status,
                                Boolean modify
                                 ) {

        Task t = null;
        if(modify) {
            List<Task> tasklist = taskService.findAll();
            t = tasklist.get(0);
        }
        else
            t = new Task();

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

        if(!modify)
            taskService.addTask(t);
        else
            taskService.updateTask(t);
    }

}
