package com.project.whistleblower.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendComplaintToAuthority(File pdfFile){
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("projectx0990@gmail.com");
            helper.setTo("shauryakumarsharma0007@gmail.com"); // Replace with actual address
            helper.setSubject("New Anonymous Corruption Complaint Submitted");

            helper.setText(
                    "Respected Authority,\n\n" +
                            "An anonymous complaint regarding alleged corrupt practices by a law enforcement official has been submitted through our public grievance platform.\n\n" +
                            "Please find the attached PDF document summarizing the complaint, including the date/time of submission, a detailed description of the incident, and links to supporting evidence (e.g., images, audio recordings, documents).\n\n" +
                            "We request that you kindly acknowledge this report and take appropriate action in accordance with relevant laws and departmental protocols.\n\n" +
                            "This complaint was submitted anonymously to protect the safety and identity of the whistleblower.\n\n" +
                            "Sincerely,\n" +
                            "Whistleblower Platform (Automated Service)\n" +
                            "Do not reply to this email."
            );

            FileSystemResource file = new FileSystemResource(pdfFile);
            helper.addAttachment("complaint.pdf", file);

            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
