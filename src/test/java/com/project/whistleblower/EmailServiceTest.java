package com.project.whistleblower;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.File;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {
    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private com.project.whistleblower.service.EmailService emailService;

    @Test
    void testSendComplaintToAuthority() {
        File dummyFile = new File("dummy.pdf");
        MimeMessage mimeMessage = new MimeMessage((jakarta.mail.Session) null);
        Mockito.when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        emailService.sendComplaintToAuthority(dummyFile);

        verify(mailSender, times(1)).send(any(MimeMessage.class));
    }
}
