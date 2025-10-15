package com.storage_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.UUID;

@Service
public class SeaweedsStorageServiceImpl implements SeaweedsStorageService {
    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${seaweed.s3.bucket}")
    private String bucketName;

    @Value("${seaweed.s3.public-url}")
    private String publicUrl;

    public SeaweedsStorageServiceImpl(@Qualifier("seaweedsClient") S3Client s3Client, S3Presigner s3Presigner) {
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
    }

    public void createBucketIfNotExists() {
        boolean exists = s3Client.listBuckets().buckets()
                .stream()
                .anyMatch(b -> b.name().equals(bucketName));
        if (!exists) {
            s3Client.createBucket(CreateBucketRequest.builder().bucket(bucketName).build());
        }
    }

    public String uploadFile(MultipartFile file) throws IOException {
        createBucketIfNotExists();
        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

        return publicUrl + ":/buckets/" + bucketName + "/" + fileName;
    }

    public byte[] downloadFile(String fileName) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        try (ResponseInputStream<GetObjectResponse> object = s3Client.getObject(getObjectRequest)) {
            return object.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException("Download failed", e);
        }
    }

    public void deleteFile(String fileName) {
        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build());
    }

    public String generatePresignedUrl(String fileName) {

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();
        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .getObjectRequest(getObjectRequest)
                .build();

        URL presignUrl = s3Presigner.presignGetObject(presignRequest).url();
        return presignUrl.toString();

    }
}
