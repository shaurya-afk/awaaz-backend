package com.project.whistleblower.service;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class FirebaseStorageService {
    public String uploadFile(MultipartFile file) {
        try {
            String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
            Bucket bucket = StorageClient.getInstance().bucket();
            bucket.create(filename, file.getBytes(), file.getContentType());

            // Load service account from classpath
            InputStream serviceAccountStream = getClass().getClassLoader().getResourceAsStream("serviceAccountKey.json");
            if (serviceAccountStream == null) {
                throw new RuntimeException("serviceAccountKey.json not found in classpath");
            }
            Storage storage = StorageOptions.newBuilder()
                .setCredentials(ServiceAccountCredentials.fromStream(serviceAccountStream))
                .build()
                .getService();

            BlobInfo blobInfo = BlobInfo.newBuilder(bucket.getName(), filename).build();
            // Make the uploaded file public
            storage.create(blobInfo, file.getBytes());
            storage.createAcl(blobInfo.getBlobId(), com.google.cloud.storage.Acl.of(com.google.cloud.storage.Acl.User.ofAllUsers(), com.google.cloud.storage.Acl.Role.READER));
            // Generate a public URL
            String publicUrl = String.format("https://storage.googleapis.com/%s/%s", bucket.getName(), filename);
            return publicUrl;
        } catch (Exception e) {
            throw new RuntimeException("Upload Failed: " + e);
        }
    }
}
