package com.storage_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;
import java.net.URLConnection;

@Configuration
public class SeaweedsConfig {

    @Value("${seaweed.s3.endpoint}")
    private String endpoint;

    @Value("${seaweed.s3.access-key}")
    private String accessKey;

    @Value("${seaweed.s3.secret-key}")
    private String secretKey;

    @Bean(name = "seaweedsClient")
    public S3Client s3Client() {
        return S3Client.builder()
                .endpointOverride(URI.create(endpoint))
                .region(Region.US_EAST_1)
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(accessKey, secretKey)
                        )
                )
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true)
                        .build())
//                .httpClientBuilder(URLConnection.)
                .build();
    }

//    @Bean
//    public S3Presigner s3Presigner() {
//        return S3Presigner.builder()
//                .region(Region.US_EAST_1)  // Lấy region từ S3Client
//                .endpointOverride(URI.create(endpoint))
//                .credentialsProvider(StaticCredentialsProvider.create(
//                        AwsBasicCredentials.create(accessKey, secretKey)
//                ))
//                .build();
//    }
}
