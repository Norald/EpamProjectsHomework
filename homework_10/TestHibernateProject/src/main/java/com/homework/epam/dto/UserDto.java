package com.homework.epam.dto;

import com.homework.epam.entity.UserResult;

import javax.persistence.CascadeType;

import java.util.Set;

public class UserDto {
    private int id;

    private String email;

    private long idn;

    private boolean blocked;


    private int userRoleId;

    private String password;

    private Set<UserResultDto> userResults;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Set<UserResultDto> getUserResults() {
        return userResults;
    }

    public void setUserResults(Set<UserResultDto> userResults) {
        this.userResults = userResults;
    }
}
