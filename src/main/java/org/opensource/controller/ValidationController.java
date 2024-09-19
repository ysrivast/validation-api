package org.opensource.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.opensource.service.ValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@Log4j2
@RestController
@RequestMapping("/api/v1/validation")
public class ValidationController {

    private final ValidationService validationService;
    @GetMapping("/pdf")
    public ResponseEntity<String> pdfParse(){
        StopWatch stopWatch = new StopWatch();
        log.info("GET API invoked : pdfParse");
        stopWatch.start();
        validationService.pdfParse();
        stopWatch.stop();
        return ResponseEntity.ok("Total time take to process in seconds : "+stopWatch.getTotalTimeSeconds());
    }

    @GetMapping("/pdf-split")
    public ResponseEntity<String> pdfSplit(){
        StopWatch stopWatch = new StopWatch();
        log.info("GET API invoked : pdfSplit");
        stopWatch.start();
        validationService.pdfSplit();
        stopWatch.stop();
        return ResponseEntity.ok("Total time take to process in seconds : "+stopWatch.getTotalTimeSeconds());
    }
    @GetMapping("/xml")
    public ResponseEntity<String> xmlParse(){
        StopWatch stopWatch = new StopWatch();
        log.info("GET API invoked : pdfParse");
        stopWatch.start();
        validationService.xmlParse();
        stopWatch.stop();
        return ResponseEntity.ok("Total time take to process in seconds : "+stopWatch.getTotalTimeSeconds());
    }
    @GetMapping("/xml-split")
    public ResponseEntity<String> xmlSplit(){
        StopWatch stopWatch = new StopWatch();
        log.info("GET API invoked : pdfSplit");
        stopWatch.start();
        validationService.xmlSplit();
        stopWatch.stop();
        return ResponseEntity.ok("Total time take to process in seconds : "+stopWatch.getTotalTimeSeconds());
    }
    @GetMapping("/delete")
    public ResponseEntity<String> cleanDirectory(){
        StopWatch stopWatch = new StopWatch();
        log.info("GET API invoked : pdfSplit");
        stopWatch.start();
        validationService.cleanDirectory();
        stopWatch.stop();
        return ResponseEntity.ok("Total time take to process in seconds : "+stopWatch.getTotalTimeSeconds());
    }
}
