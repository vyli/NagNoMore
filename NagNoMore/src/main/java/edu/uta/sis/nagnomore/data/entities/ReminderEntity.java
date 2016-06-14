package edu.uta.sis.nagnomore.data.entities;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by VPS on 5.6.2016.
 */

@Entity
@Table(name= "reminder")
public class ReminderEntity {

    @Id
    @GeneratedValue
    Integer id;

    String title;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    DateTime time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DateTime getTime() {
        return time;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }
}
