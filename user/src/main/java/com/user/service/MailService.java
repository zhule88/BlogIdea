package com.user.service;

import com.user.pojo.message;

import javax.mail.internet.AddressException;

public interface MailService {
    void send(message msg) throws Exception;
}
