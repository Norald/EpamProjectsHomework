package com.homework.epam.entity;


import javax.persistence.*;

@Entity
@Table(name = "user_details")
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_details")
    private int id;

    private String name;


    private String surname;


    private String patronymic;


    private String city;


    private String region;


    @Column(name = "school_name")
    private String schoolName;

    @Column(name = "document_url")
    private String documentUrl;

    @Column(name = "average_certificate_point")
    private int averageCertificate;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user; //non owning entity

    private String name_ua;


    private String surname_ua;


    private String patronymic_ua;


    private String city_ua;


    private String region_ua;


    @Column(name = "school_name_ua")
    private String schoolNameUa;


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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public int getAverageCertificate() {
        return averageCertificate;
    }

    public void setAverageCertificate(int averageCertificate) {
        this.averageCertificate = averageCertificate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName_ua() {
        return name_ua;
    }

    public void setName_ua(String name_ua) {
        this.name_ua = name_ua;
    }

    public String getSurname_ua() {
        return surname_ua;
    }

    public void setSurname_ua(String surname_ua) {
        this.surname_ua = surname_ua;
    }

    public String getPatronymic_ua() {
        return patronymic_ua;
    }

    public void setPatronymic_ua(String patronymic_ua) {
        this.patronymic_ua = patronymic_ua;
    }

    public String getCity_ua() {
        return city_ua;
    }

    public void setCity_ua(String city_ua) {
        this.city_ua = city_ua;
    }

    public String getRegion_ua() {
        return region_ua;
    }

    public void setRegion_ua(String region_ua) {
        this.region_ua = region_ua;
    }

    public String getSchoolNameUa() {
        return schoolNameUa;
    }

    public void setSchoolNameUa(String schoolNameUa) {
        this.schoolNameUa = schoolNameUa;
    }
}
