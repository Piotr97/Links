package com.mail.service;

import com.mail.model.MyMail;

public interface EmailSender {

    MyMail sendEmail(MyMail myMail);
}
