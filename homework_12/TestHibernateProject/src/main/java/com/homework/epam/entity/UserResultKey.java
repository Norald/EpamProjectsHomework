package com.homework.epam.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserResultKey implements Serializable {
    @Column(name = "user_id")
    private int userId;

    @Column(name = "subject_exam_id")
    private int subjectExamId;

    public UserResultKey() {
    }

    public UserResultKey(int userId, int subjectExamId) {
        this.userId = userId;
        this.subjectExamId = subjectExamId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSubjectExamId() {
        return subjectExamId;
    }

    public void setSubjectExamId(int subjectExamId) {
        this.subjectExamId = subjectExamId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserResultKey that = (UserResultKey) o;
        return userId == that.userId &&
                subjectExamId == that.subjectExamId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, subjectExamId);
    }
}
