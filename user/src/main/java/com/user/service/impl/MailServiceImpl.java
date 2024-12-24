package com.user.service.impl;


import com.user.pojo.message;
import com.user.service.MailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;


@Service
public class MailServiceImpl implements MailService {
    String username = "ely-sia@qq.com";
    String password = "tgbpzcxtixddeaij";


    @RabbitListener(queues = "email")
    public void send(message msg) throws Exception {
        //不理解，为什么这里不能搞个专门的Properties，明明是固定的属性
        Properties prop=new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.host", "smtp.qq.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.starttls.enable", "true"); // 启用TLS加密
        Session session=Session.getDefaultInstance(prop, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        Message message = new MimeMessage(session);
        // 设置发件人
        message.setFrom(new InternetAddress(username));
        // 设置收件人
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(msg.getEmail()));
        // 设置邮件主题
        message.setSubject("来自筑乐屋的消息");
        // 设置邮件内容
        message.setText(msg.getContent());
        // 发送邮件
        Transport.send(message);

    }
}
