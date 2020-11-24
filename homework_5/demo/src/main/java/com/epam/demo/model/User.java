package com.epam.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "user")
@ToString(exclude = "password")
public class User {

    @Id
    @Getter
    @Setter
    private int id;

    @Column(name = "email")
    @Getter
    @Setter
    private String email;

    @Column(name = "idn")
    @Getter
    @Setter
    private long idn;

    @Column(name = "block")
    @Getter
    @Setter
    private boolean blocked;

    @Column(name = "user_role_id")
    @Getter
    @Setter
    private int userRoleId;

    @Column(name = "password")
    @Getter
    @Setter
    private String password;

}
