package edu.uta.sis.nagnomore.data.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
// import java.util.List;

/**
 * Created by Hanna Palomäki on 1.6.2016.
 */
@Table
@Entity(name = "family")
public class FamilyEntity {

    @Id
    @GeneratedValue
    Integer id;

    String familyName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

}