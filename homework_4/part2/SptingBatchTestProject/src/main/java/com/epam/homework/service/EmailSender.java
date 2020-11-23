package com.epam.homework.service;

import com.sun.mail.smtp.SMTPTransport;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Service
public class EmailSender {

    private static final String SMTP_SERVER = "smtp.gmail.com";
    private static final String USERNAME = "@gmail.com";
    private static final String PASSWORD = "";

    private static final String EMAIL_FROM = "@gmail.com";
    private static final String EMAIL_TO_CC = "";

    private static final String EMAIL_SUBJECT = "Test Send Email via SMTP";
    private static final String EMAIL_TEXT = "";


    public void sendEmail(String receiver) throws MessagingException {
        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", SMTP_SERVER); //optional, defined in SMTPTransport
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "465"); // default port 25

        Session session = Session.getInstance(prop, null);
        Message msg = new MimeMessage(session);

        // from
        msg.setFrom(new InternetAddress(EMAIL_FROM));

        // to
        msg.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(receiver, false));

        // cc
        msg.setRecipients(Message.RecipientType.CC,
                InternetAddress.parse(EMAIL_TO_CC, false));

        // subject
        msg.setSubject(EMAIL_SUBJECT);

        // content
        msg.setText(EMAIL_TEXT);

        msg.setSentDate(new Date());

        // Get SMTPTransport
        SMTPTransport t = (SMTPTransport) session.getTransport("smtp");

        // connect
        t.connect(SMTP_SERVER, USERNAME, PASSWORD);

        // send
        t.sendMessage(msg, msg.getAllRecipients());

        System.out.println("Response: " + t.getLastServerResponse());

        t.close();

    }


}
