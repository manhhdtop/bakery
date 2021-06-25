package com.bakery.server.service.impl;

import com.bakery.server.exception.BadRequestException;
import com.bakery.server.service.MailService;
import com.bakery.server.utils.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Service
@Slf4j
public class MailServiceImpl implements MailService {
    @Autowired
    private Environment env;

    @Override
    public void sendMail(String toEmail, String subject, String message) {
        sendMail(subject, message, Collections.singletonList(toEmail));
    }

    @Override
    public void sendMail(List<String> toEmails, String subject, String message) {
        sendMail(subject, message, toEmails);
    }

    @Override
    public void sendMail(String toEmail, String subject, String message, Multipart multipart) {
        sendMail(subject, message, multipart, Collections.singletonList(toEmail));
    }

    @Override
    public void sendMail(List<String> toEmails, String subject, String message, Multipart multipart) {
        sendMail(subject, message, multipart, toEmails);
    }

    private void sendMail(String subject, String message, List<String> toEmails) {
        sendMail(subject, message, null, toEmails);
    }

    private void sendMail(String subject, String messageBody, Multipart multipart, List<String> toEmails) {
        AssertUtil.isTrue(StringUtils.isNotBlank(subject), "mail.subject.not_blank");
        AssertUtil.isTrue(StringUtils.isNotBlank(messageBody), "mail.message.not_blank");
        AssertUtil.notEmpty(toEmails, "mail.emails.not_blank");
        log.info("(MailService) start send mail");

        Properties props = new Properties();
        props.put("mail.smtp.auth", env.getRequiredProperty("mail.smtp.auth"));
        props.put("mail.smtp.host", env.getRequiredProperty("mail.smtp.host"));
        props.put("mail.smtp.socketFactory.port", env.getRequiredProperty("mail.smtp.socketFactory.port"));
        props.put("mail.smtp.socketFactory.class", env.getRequiredProperty("mail.smtp.socketFactory.class"));
        props.put("mail.smtp.port", env.getRequiredProperty("mail.smtp.port"));
        String email = env.getRequiredProperty("mail.config.emailFrom");
        String password = env.getRequiredProperty("mail.config.password");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            String emails = String.join(",", toEmails);
            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(emails));
            message.setSubject(subject);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(messageBody, "text/html; charset=UTF-8");

            if (multipart == null) {
                multipart = new MimeMultipart();
            }
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
        } catch (AddressException e) {
            log.error("(MailService) AddressException: {}, \n", e.getMessage(), e);
            throw new BadRequestException("mail.email_address.invalid");
        } catch (MessagingException e) {
            log.error("(MailService) MessagingException: {}, \n", e.getMessage(), e);
            throw new BadRequestException("mail.send_error");
        }
        log.info("(MailService) send mail success");
    }
}
