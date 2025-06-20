package com.project.whistleblower.service;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.project.whistleblower.model.Complaint;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Element;

@Service
public class ComplaintPdfService {
    private final TinyUrlService tinyUrlService;

    public ComplaintPdfService(TinyUrlService tinyUrlService) {
        this.tinyUrlService = tinyUrlService;
    }

    public File generateComplaintPdf(Complaint complaint){
        List<String> shortUrls = complaint.getEvidenceUrl().stream()
                .map(tinyUrlService::getTinyUrl)
                .toList();

        try {
            File pdfFile = File.createTempFile("complaint-", ".pdf");
            FileOutputStream fos = new FileOutputStream(pdfFile);
            Document document = new Document();
            PdfWriter.getInstance(document, fos);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 11);
            Font evidenceFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10);

            Paragraph title = new Paragraph("Anonymous Whistleblower Complaint", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Submitted At: " + complaint.getCreatedAt(), normalFont));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Complaint Description:", sectionFont));
            document.add(new Paragraph(complaint.getDescription(), normalFont));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Evidence Provided:", sectionFont));
            int index = 1;
            for (String url : shortUrls) {
                document.add(new Paragraph("Evidence " + index + ": " + url, evidenceFont));
                index++;
            }

            document.close();
            return pdfFile;
        }catch (Exception e){
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }
}
