package edu.uta.sis.nagnomore.data.entities;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * Created by mare on 29.5.2016.
 */

@Entity
@Table(name="task")
public class TaskEntity {

    @Id
    @GeneratedValue
    Integer id;

    String title;
    String description;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    DateTime created;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    DateTime due;

    Integer priority;

    Boolean privacy;
    Boolean alarm;

    //En ole koskaan toteuttanut ManyToManya plus koko CategoryEntity puuttuu, joten toistaiseksi kommentoitu pois
    //@ManyToMany
    //CategoryEntity category;

    //FamilyEntity puuttuu vielä, joten kommentoitu pois
    //@ManyToOne
    //FamilyEntity family;

    @ManyToOne
    UserEntity creator;

    @ManyToOne
    UserEntity assignee;

    @ManyToOne()
    LocationEntity location;

    public enum Status {
        NEEDS_ACTION, IN_PROGRESS, COMPLETED
    }

    Status status;

    //ReminderEntity puuttuu vielä
    //@OneToMany(fetch = FetchType.EAGER)
    //List<ReminderEntity> reminderList;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DateTime getCreated() {
        return created;
    }

    public void setCreated(DateTime created) {
        this.created = created;
    }

    public DateTime getDue() {
        return due;
    }

    public void setDue(DateTime due) {
        this.due = due;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Boolean privacy) {
        this.privacy = privacy;
    }

    public Boolean getAlarm() {
        return alarm;
    }

    public void setAlarm(Boolean alarm) {
        this.alarm = alarm;
    }

    public UserEntity getCreator() {
        return creator;
    }

    public void setCreator(UserEntity creator) {
        this.creator = creator;
    }

    public UserEntity getAssignee() {
        return assignee;
    }

    public void setAssignee(UserEntity assignee) {
        this.assignee = assignee;
    }

    public LocationEntity getLocation() {
        return location;
    }

    public void setLocation(LocationEntity location) {
        this.location = location;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
