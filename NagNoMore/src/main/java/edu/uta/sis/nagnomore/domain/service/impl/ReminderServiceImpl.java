package edu.uta.sis.nagnomore.domain.service.impl;

import edu.uta.sis.nagnomore.data.entities.ReminderEntity;
import edu.uta.sis.nagnomore.data.repository.ReminderRepository;
import edu.uta.sis.nagnomore.domain.data.Reminder;
import edu.uta.sis.nagnomore.domain.service.ReminderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VPS on 5.6.2016.
 */

@Service
public class ReminderServiceImpl implements ReminderService {

    @Autowired
    ReminderRepository reminderRepository;

    @Transactional(readOnly = false)
    public void create(Reminder r) {
        ReminderEntity re = new ReminderEntity();
        BeanUtils.copyProperties(r, re);
        reminderRepository.add(re);
        BeanUtils.copyProperties(re, r);
    }

    @Transactional(readOnly = false)
    public void update(Reminder r) {
        ReminderEntity re = reminderRepository.find(r.getId());
        BeanUtils.copyProperties(r, re);
        reminderRepository.update(re);
    }

    @Transactional(readOnly = false)
    public Reminder remove(Reminder r) {
        ReminderEntity re = reminderRepository.remove(r.getId());
        BeanUtils.copyProperties(re, r);
        return r;
    }

    @Transactional(readOnly = true)
    public Reminder get(Integer id) {
        ReminderEntity re = reminderRepository.find(id);
        Reminder r = new Reminder();
        BeanUtils.copyProperties(re, r);
        return r;
    }

    @Transactional(readOnly = true)
    public List<Reminder> getAll() {
        List<ReminderEntity> rel = reminderRepository.findAll();
        ArrayList<Reminder> rl = new ArrayList<Reminder>(rel.size());
        for (ReminderEntity re: rel) {
            Reminder r = new Reminder();
            BeanUtils.copyProperties(re, r);
            rl.add(r);
        }
        return rl;
    }
}
