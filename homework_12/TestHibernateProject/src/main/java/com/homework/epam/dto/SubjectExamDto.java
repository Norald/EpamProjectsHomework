package com.homework.epam.dto;

import java.util.Set;

public class SubjectExamDto {
    private int id;

    private String name;

    private String description;

    private String name_ua;

    private String description_ua;

    private Set<UserResultDto> userResults;

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

    public Set<UserResultDto> getUserResults() {
        return userResults;
    }

    public void setUserResults(Set<UserResultDto> userResults) {
        this.userResults = userResults;
    }
}
