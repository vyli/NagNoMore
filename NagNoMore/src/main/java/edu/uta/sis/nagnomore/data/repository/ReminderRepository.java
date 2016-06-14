package edu.uta.sis.nagnomore.data.repository;

import edu.uta.sis.nagnomore.data.entities.ReminderEntity;

import java.util.List;

/**
 * Created by VPS on 5.6.2016.
 */

public interface ReminderRepository {
    public void add(ReminderEntity reminderEntity);
    public void update(ReminderEntity reminderEntity);
    public ReminderEntity remove(Integer id);
    public ReminderEntity find(Integer id);
    public List<ReminderEntity> findAll();
}
