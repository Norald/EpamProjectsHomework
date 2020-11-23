package com.epam.homework.step;

import com.epam.homework.BatchConfig;
import com.epam.homework.service.EmailSender;
import com.epam.homework.model.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.MessagingException;

public class Processor implements ItemProcessor<User, User> {

    private static final Logger LOG = LogManager.getLogger(BatchConfig.class.getName());

    @Autowired
    EmailSender emailSender;

    public User process(User user) {
        LOG.info("Process... "+ user.getEmail());
        try {
            emailSender.sendEmail(user.getEmail());
        } catch (MessagingException e) {
            LOG.warn(e.getMessage());
        }
        return user;
    }

}
