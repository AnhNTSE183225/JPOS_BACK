package com.fpt.jpos.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileUploadService {

    String upload(MultipartFile multipartFile) throws IOException;

    String uploadModelDesignFile(MultipartFile multipartFile, Integer orderId, Integer designStaffId) throws IOException;
}
