package edu.uta.sis.nagnomore.web.testing;

import edu.uta.sis.nagnomore.data.repository.ReminderRepository;
import edu.uta.sis.nagnomore.domain.data.Reminder;
import edu.uta.sis.nagnomore.domain.service.ReminderService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by VPS on 6.6.2016.
 */

@Controller
public class TestReminderController {

    @Autowired
    ReminderService reminderService;

    @Autowired
    ReminderRepository reminderRepository;

    @RequestMapping(value="/rem_add")
    public String test1() {
        Reminder r = new Reminder();
        r.setTitle("Muistutus 2");
        DateTime t = new DateTime(2016, 7, 7, 14, 30);
        r.setTime(t);
        reminderService.create(r);
        return "/home";
    }

    @RequestMapping(value="/rem_list")
    public String test2() {
        List<Reminder> rl = reminderService.getAll();
        return "/home";
    }

    @RequestMapping(value="/rem_upd")
    public String test3() {
        List<Reminder> rl = reminderService.getAll();
        int sz = rl.size();
        if (sz > 0) {
            Reminder r = rl.get(0);
            int id = r.getId();
            Reminder rn = reminderService.get(id);
            r.setTitle("Uusi muistutus");
            reminderService.update(r);
        }
        return "/home";
    }

    @RequestMapping(value="/rem_rm")
    public String test4() {
        List<Reminder> rl = reminderService.getAll();
        int sz = rl.size();
        if (sz > 0) {
            Reminder r = rl.get(1);
            Reminder rn = reminderService.remove(r);
        }
        return "/home";
    }
}
