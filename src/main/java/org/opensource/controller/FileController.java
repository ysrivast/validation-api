package org.opensource.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.opensource.dto.FileInfo;
import org.opensource.dto.ResponseMessage;
import org.opensource.service.StorageService;
import org.opensource.service.ValidationService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@Log4j2
@RestController
@RequestMapping("/api/v1/file")
//@CrossOrigin("https://localhost:8081", "http://localhost:4200")
//@CrossOrigin(value = {"https://localhost:8081","http://localhost:8081", "http://localhost:4200"})
public class FileController {

    private final ValidationService validationService;
    private StorageService storageService;

    @GetMapping("/health")
    public ResponseEntity<String> health(){
        log.info("GET API invoked : health");
        return ResponseEntity.ok("up...");
    }
    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
            storageService.save(file);
            String message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
//          message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
    }
    @GetMapping("/files")
    public ResponseEntity<List<FileInfo>> getFiles() {
        return ResponseEntity.status(HttpStatus.OK).body(storageService.listFilesUrl());
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.getFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
