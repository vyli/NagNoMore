package edu.uta.sis.nagnomore.domain.service;

import edu.uta.sis.nagnomore.data.entities.ReminderEntity;
import edu.uta.sis.nagnomore.domain.data.Reminder;

import java.util.List;

/**
 * Created by VPS on 5.6.2016.
 */

public interface ReminderService {
    public void create(Reminder r);
    public void update(Reminder r);
    public Reminder remove(Reminder r);
    public Reminder get(Integer id);
    public List<Reminder> getAll();
}
