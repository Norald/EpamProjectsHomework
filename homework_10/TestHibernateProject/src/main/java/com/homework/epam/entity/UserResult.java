package com.homework.epam.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "users_results")
public class UserResult {

    @EmbeddedId
    private UserResultKey id = new UserResultKey();

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    User user;

    @ManyToOne
    @MapsId("subjectExamId")
    @JoinColumn(name = "subject_exam_id", insertable = false, updatable = false)
    SubjectExam subjectExam;

    private int result;

    @Column(name = "date_of_exam")
    private Date dateOfExam;

    public UserResultKey getId() {
        return id;
    }

    public void setId(UserResultKey id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SubjectExam getSubjectExam() {
        return subjectExam;
    }

    public void setSubjectExam(SubjectExam subjectExam) {
        this.subjectExam = subjectExam;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Date getDateOfExam() {
        return dateOfExam;
    }

    public void setDateOfExam(Date dateOfExam) {
        this.dateOfExam = dateOfExam;
    }
}
