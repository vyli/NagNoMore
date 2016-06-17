package edu.uta.sis.nagnomore.data.entities;


import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;


/**
 * Created by Eero Asunta on 3.6.2016.
 */
@Entity
@Table(name= "categories")
public class CategoryEntity {

    @Column
    @Id
    @GeneratedValue
    Integer id;

    @Column
    String title;

    @Column
    String description;


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

}
