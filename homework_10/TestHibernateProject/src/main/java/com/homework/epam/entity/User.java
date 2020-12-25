package com.homework.epam.entity;


import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user")
    private int id;

    private String email;

    private long idn;

    @Column(name = "block")
    private boolean blocked;

    @Column(name = "user_role_id")
    private int userRoleId;

    private String password;

    @OneToMany(mappedBy = "user")
    private Set<UserResult> userResults;

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

    public Set<UserResult> getUserResults() {
        return userResults;
    }

    public void setUserResults(Set<UserResult> userResults) {
        this.userResults = userResults;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", idn=" + idn +
                ", blocked=" + blocked +
                ", userRoleId=" + userRoleId +
                ", password='" + password + '\'' +
                ", userResults=" + userResults +
                '}';
    }
}
