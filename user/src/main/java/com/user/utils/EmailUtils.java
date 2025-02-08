package com.user.utils;


import com.user.pojo.message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
@Slf4j
@Component
public class EmailUtils {

    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromAddress;

    @RabbitListener(queues = "email")
    public void send(message msg)  {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            helper.setFrom(fromAddress);
            helper.setTo(InternetAddress.parse(msg.getEmail()));
            helper.setSubject("来自筑乐屋的消息");
            helper.setText(msg.getContent(), true);
            mailSender.send(message);
        } catch (Exception e){
            log.error("验证码发送失败", e);
        }
    }
}
