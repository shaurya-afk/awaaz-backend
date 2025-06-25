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
            helper.setTo(new String[]{
                    "presidentofindia@rb.nic.in",
                    "connect@mygov.nic.in",
                    "complaints@cvc.gov.in",
                    "jscpg-mha@nic.in",
                    "darpg-arpg@nic.in",
                    "comment.ed0909@gmail.com"
            });
            helper.setSubject("New Anonymous Corruption Complaint Submitted");

            helper.setText(
                    """
                            Respected Authority,
                            
                            An anonymous complaint regarding alleged corrupt practices by a law enforcement official has been submitted through our public grievance platform.
                            
                            Please find the attached PDF document summarizing the complaint, including the date/time of submission, a detailed description of the incident, and links to supporting evidence (e.g., images, audio recordings, documents).
                            
                            We request that you kindly acknowledge this report and take appropriate action in accordance with relevant laws and departmental protocols.
                            
                            This complaint was submitted anonymously to protect the safety and identity of the whistleblower.
                            
                            Sincerely,
                            Whistleblower Platform (Automated Service)
                            Do not reply to this email."""
            );

            FileSystemResource file = new FileSystemResource(pdfFile);
            helper.addAttachment("complaint.pdf", file);

            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
