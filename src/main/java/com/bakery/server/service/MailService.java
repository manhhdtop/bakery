package com.bakery.server.service;

import javax.mail.Multipart;
import java.util.List;

public interface MailService {
    void sendMail(String toEmail, String subject, String message);

    void sendMail(List<String> toEmails, String subject, String message);

    void sendMail(String toEmail, String subject, String message, Multipart multipart);

    void sendMail(List<String> toEmails, String subject, String message, Multipart multipart);
}
