package com.storage_service.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SeaweedsStorageService {
    String uploadFile(MultipartFile file) throws IOException;

    byte[] downloadFile(String fileName);

    void deleteFile(String fileName);

    String generatePresignedUrl(String fileName);
}
