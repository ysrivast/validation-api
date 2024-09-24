package org.opensource.service.impl;

import org.opensource.controller.FileController;
import org.opensource.dto.FileInfo;
import org.opensource.exception.FileUploadException;
import org.opensource.service.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StorageServiceImpl implements StorageService {

    private final Path root = Paths.get("uploads");
    @Override
    public void save(MultipartFile file) {
        try {
            Files.createDirectories(root);
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        }catch (IOException e) {
            throw new FileUploadException(e.getLocalizedMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e) {
            throw new FileUploadException("Error occurred", e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public List<FileInfo> listFiles() {
        try {
             return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize)
                     .map(path -> new FileInfo(path.getFileName().toString(), "")).collect(Collectors.toUnmodifiableList());
        } catch (IOException e) {
            throw new FileUploadException("Could not load the files!", e,HttpStatus .INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<FileInfo> listFilesUrl() {
        return listFiles().stream().map(fileInfo -> {
            String url = MvcUriComponentsBuilder.fromMethodName(FileController.class, "getFile",fileInfo.getName()).build().toString();
            fileInfo.setUrl(url);
            return fileInfo;
        }).collect(Collectors.toList());
    }

    @Override
    public Resource getFile(String filename) {

        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable())
                return resource;
            else
                throw new FileUploadException("Could not read the file!", HttpStatus.BAD_REQUEST);
        } catch (MalformedURLException e) {
            throw new FileUploadException("Error in file download, Invalid url...",e, HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            throw new FileUploadException("Error in file download, Invalid url...",e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}