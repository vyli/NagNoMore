package edu.uta.sis.nagnomore.domain.data;

import edu.uta.sis.nagnomore.data.entities.*;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Eero Asunta on 21.6.2016.
 */


public class Task {

    Integer id;

    @NotEmpty
    String title;

    String description;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    DateTime created;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    DateTime due;

    Integer priority;

    Boolean privacy;

    Boolean alarm;

    Category category;

    WwwFamily family;

    WwwUser creator;

    WwwUser assignee;

    String location;

    Reminder reminder;

    public enum Status {
        NEEDS_ACTION, IN_PROGRESS, COMPLETED
    }

    Status status;

    List<String> reminderList;

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public WwwFamily getFamily() {
        return family;
    }

    public void setFamily(WwwFamily family) {
        this.family = family;
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

    public WwwUser getCreator() {
        return creator;
    }

    public void setCreator(WwwUser creator) {
        this.creator = creator;
    }

    public WwwUser getAssignee() {
        return assignee;
    }

    public void setAssignee(WwwUser assignee) {
        this.assignee = assignee;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Reminder getReminder() {
        return reminder;
    }

    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
    }

}
