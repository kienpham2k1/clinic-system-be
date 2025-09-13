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
@RequiredArgsConstructor
public class SeaweedsStorageServiceImpl implements SeaweedsStorageService {
    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${seaweeds.bucket}")
    private String bucketName;

    @Value("${seaweeds.public-url}")
    private String publicUrl;

    public String uploadFile(MultipartFile file) throws IOException {
//        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
//
//        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
//                .bucket(bucketName)
//                .key(fileName)
//                .contentType(file.getContentType())
//                .build();
//
//        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
//
//        return publicUrl + ":/" + bucketName + "/" + fileName;

        try {
            // Gửi request list bucket để test kết nối
            ListBucketsResponse response = s3Client.listBuckets(ListBucketsRequest.builder().build());

            System.out.println("✅ Connected to SeaweedFS S3!");
            System.out.println("Buckets available:");
            response.buckets().forEach(bucket ->
                    System.out.println(" - " + bucket.name())
            );

        } catch (Exception e) {
            System.err.println("❌ Cannot connect to SeaweedFS S3!");
            e.printStackTrace();
        } finally {
            s3Client.close();
        }
        return "";
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
