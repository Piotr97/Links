package com.mail.service;


import com.mail.model.MyMail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderService implements EmailSender {

    private static final Logger logger = LoggerFactory.getLogger(EmailSenderService.class);
    private JavaMailSender javaMailSender;

    @Value("${email.address}")
    private String hostEmailAddress;


    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    @Override
    public MyMail sendEmail(MyMail myMail) {
        MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(mail, true);
            helper.setTo(myMail.getAddress());
            helper.setFrom(hostEmailAddress);
            helper.setSubject(myMail.getSubject());
            helper.setText(myMail.getBody(), true);
        } catch (MessagingException e) {
            logger.error("Error sending email: {}" + e.getMessage());
        }

        javaMailSender.send(mail);
        return myMail;


    }
}
