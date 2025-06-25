package com.project.whistleblower.controller;

import com.project.whistleblower.model.Complaint;
import com.project.whistleblower.service.ComplaintPdfService;
import com.project.whistleblower.service.EmailService;
import com.project.whistleblower.service.FirebaseStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/complaints")
public class ComplaintController{
    private final FirebaseStorageService firebaseStorageService;
    private final ComplaintPdfService complaintPdfService;
    private final EmailService emailService;

    public ComplaintController(FirebaseStorageService firebaseStorageService, ComplaintPdfService complaintPdfService, EmailService emailService) {
        this.firebaseStorageService = firebaseStorageService;
        this.complaintPdfService = complaintPdfService;
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<String> submitComplaint(
            @RequestParam("description") String description,
            @RequestParam("files") List<MultipartFile> files
    ){
        List<String> uploadedUrls = files.stream()
                .map(firebaseStorageService::uploadFile)
                .toList();

        Complaint complaint = new Complaint();
        complaint.setDescription(description);
        complaint.setEvidenceUrl(uploadedUrls);

        File pdf = complaintPdfService.generateComplaintPdf(complaint);
        emailService.sendComplaintToAuthority(pdf);

        return ResponseEntity.ok("Complaint submitted with files: " + uploadedUrls);
    }

    @GetMapping("/check")
    public ResponseEntity<String> checkComplaint(){
        return ResponseEntity.ok("Complaint service is running");
    }
}
