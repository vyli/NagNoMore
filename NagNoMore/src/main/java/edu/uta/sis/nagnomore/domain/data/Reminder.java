package edu.uta.sis.nagnomore.domain.data;

import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Created by VPS on 7.6.2016.
 */

public class Reminder {

    Integer id;

    @NotEmpty
    String title;

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
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
