package com.homework.epam.dto;

import javax.persistence.Column;
import java.sql.Date;

public class UserResultDto {
    private int userId;
    private int subjectExamId;

    private int result;
    private Date dateOfExam;


    private String email;

    private long idn;

    private boolean blocked;

    private int userRoleId;

    private String password;

    private String se_name;

    private String se_description;

    private String se_name_ua;

    private String se_description_ua;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getIdn() {
        return idn;
    }

    public void setIdn(long idn) {
        this.idn = idn;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public int getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSe_name() {
        return se_name;
    }

    public void setSe_name(String se_name) {
        this.se_name = se_name;
    }

    public String getSe_description() {
        return se_description;
    }

    public void setSe_description(String se_description) {
        this.se_description = se_description;
    }

    public String getSe_name_ua() {
        return se_name_ua;
    }

    public void setSe_name_ua(String se_name_ua) {
        this.se_name_ua = se_name_ua;
    }

    public String getSe_description_ua() {
        return se_description_ua;
    }

    public void setSe_description_ua(String se_description_ua) {
        this.se_description_ua = se_description_ua;
    }
}
