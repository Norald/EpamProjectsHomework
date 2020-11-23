package com.epam.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Class that describes user.
 *
 * @author Vladislav Prokopenko.
 */
@Entity
@Table(name = "user")
public class User {

    @Id
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "idn")
    private long idn;

    @Column(name = "block")
    private boolean blocked;

    @Column(name = "user_role_id")
    private int userRoleId;

    @Column(name = "password")
    private String password;

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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", idn=" + idn +
                ", blocked=" + blocked +
                ", user_role_id=" + userRoleId +
                ", password='" + "**********" + '\'' +
                '}';
    }
}
