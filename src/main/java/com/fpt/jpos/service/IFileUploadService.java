package com.fpt.jpos.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileUploadService {
    String uploadFile(MultipartFile multipartFile, Integer orderId) throws IOException;
}
