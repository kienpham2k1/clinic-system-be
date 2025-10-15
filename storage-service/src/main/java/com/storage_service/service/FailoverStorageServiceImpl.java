package com.storage_service.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
public class FailoverStorageServiceImpl implements StorageService {
    private final MinIOStorageService minIOStorageService;
    private final SeaweedsStorageService seaweedsStorageService;
    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        return "";
    }

    @Override
    public byte[] downloadFile(String fileName) {
        return new byte[0];
    }

    @Override
    public void deleteFile(String fileName) {

    }

    @Override
    public String generatePresignedUrl(String fileName) {
        return "";
    }

    @Override
    public boolean isHealthy() {
        return false;
    }
}
