package com.user.service;

import javax.mail.internet.AddressException;

public interface MailService {
    void send(String email,String content) throws Exception;
}
