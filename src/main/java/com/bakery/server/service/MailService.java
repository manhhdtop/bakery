package com.bakery.server.service;

import javax.mail.Multipart;
import java.io.File;
import java.util.List;

public interface MailService {
    void sendMail(String toEmail, String subject, String message);

    void sendMail(List<String> toEmails, String subject, String message);

    void sendMail(String toEmail, String subject, String message, List<File> files, List<String> cids);

    void sendMail(List<String> toEmails, String subject, String message, List<File> files, List<String> cids);
}
