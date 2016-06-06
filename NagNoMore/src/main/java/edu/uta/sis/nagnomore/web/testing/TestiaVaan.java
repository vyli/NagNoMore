package edu.uta.sis.nagnomore.web.testing;

import edu.uta.sis.nagnomore.data.entities.*;
import edu.uta.sis.nagnomore.data.repository.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Random;

/**
 * Created by Hannu Lohtander on 10.5.2016.
 */
@Controller
public class TestiaVaan {

    @Autowired
    EventsRepository eventsRepository;

    @Autowired
    CalendarRepository calendarRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/test")
    public String test() {
        Random r = new Random();
        int rand = r.nextInt((10000 - 1) + 1) + 1;
        LocationEntity le = new LocationEntity();
        le.setName("A-" + rand);
        locationRepository.save(le);


        CalendarEntity ce = new CalendarEntity();
        ce.setName("B-" + rand);
        calendarRepository.save(ce);


        EventEntity e = new EventEntity();
        e.setCalendar(ce);
        e.setLocation(le);
        e.setTitle("title-1");
        e.setStart(DateTime.now());
        e.setEnd(DateTime.now());

        eventsRepository.add(e);

        //UserEntity id:llä 1 on luotu erikseen
        UserEntity ue = userRepository.getUserById(1);

        TaskEntity te = new TaskEntity();
        te.setTitle("Title" + rand);
        te.setDescription("Desc" + rand);
        DateTime dt = DateTime.now();
        te.setCreated(dt);
        DateTime due = dt.plusWeeks(2);
        te.setDue(due);
        te.setPriority(1);
        te.setPrivacy(false);
        te.setAlarm(false);
        te.setCreator(ue);
        te.setAssignee(ue);
        te.setLocation(le);
        TaskEntity.Status status = TaskEntity.Status.COMPLETED;
        te.setStatus(status);

        taskRepository.addTask(te);

        return "/home";

    }

    @RequestMapping("/test2")

    public void test2() {
        CalendarEntity calendarEntity = calendarRepository.find(2);
        System.out.println(calendarEntity);
        System.out.println(calendarEntity.getEvents());
    }

    @RequestMapping("/test3")

    public void test3() {
        EventEntity eventEntity = eventsRepository.find(5);
        System.out.println(eventEntity);
        System.out.println(eventEntity.getCalendar());
        System.out.println(eventEntity.getLocation());

    }

}
