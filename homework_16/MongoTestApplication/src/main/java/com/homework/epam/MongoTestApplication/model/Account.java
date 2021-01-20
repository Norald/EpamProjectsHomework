package com.homework.epam.MongoTestApplication.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
public class Account {
    private int cardNumber;
    private String nameOnAccount;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date expirationDate;
}
