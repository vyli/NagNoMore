package edu.uta.sis.nagnomore.domain.data;


import edu.uta.sis.nagnomore.domain.service.CategoryService;
import edu.uta.sis.nagnomore.domain.service.FamilyService;
import edu.uta.sis.nagnomore.domain.service.TaskService;
import edu.uta.sis.nagnomore.domain.service.UserService;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by mare on 25.7.2016.
 */

public class TaskSkeleton {



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

    Integer categoryId;

    Integer familyId;

    Integer creatorId;

    Integer assigneeId;

    String location;

    Integer reminderId;

    String statusId;

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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategory(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Integer familyId) {
        this.familyId = familyId;
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

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreator(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(Integer assigneeId) {
        this.assigneeId = assigneeId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return statusId;
    }

    public void setStatus(String statusId) {
        this.statusId = statusId;
    }

    public Integer getReminderId() {
        return reminderId;
    }

    public void setReminderId(Integer reminderId) {
        this.reminderId = reminderId;
    }


}
