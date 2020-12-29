package com.homework.epam.entity;


import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "subject_exam")
public class SubjectExam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "subject_exam")
    private int id;

    private String name;

    private String description;

    private String name_ua;

    private String description_ua;

    @OneToMany(mappedBy = "subjectExam")
    private Set<UserResult> userResults;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName_ua() {
        return name_ua;
    }

    public void setName_ua(String name_ua) {
        this.name_ua = name_ua;
    }

    public String getDescription_ua() {
        return description_ua;
    }

    public void setDescription_ua(String description_ua) {
        this.description_ua = description_ua;
    }

    public Set<UserResult> getUserResults() {
        return userResults;
    }

    public void setUserResults(Set<UserResult> userResults) {
        this.userResults = userResults;
    }

    @Override
    public String toString() {
        return "SubjectExam{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", name_ua='" + name_ua + '\'' +
                ", description_ua='" + description_ua + '\'' +
                ", userResults=" + userResults +
                '}';
    }
}
