package org.opensource.service;

import org.opensource.dto.FileInfo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageService {
    void save(MultipartFile file);

    List<FileInfo> listFiles();

    List<FileInfo> listFilesUrl();

    Resource getFile(String filename);
}
