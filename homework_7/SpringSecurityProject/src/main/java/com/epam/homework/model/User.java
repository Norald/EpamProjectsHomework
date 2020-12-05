package com.epam.homework.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString(exclude = "password")
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
}
