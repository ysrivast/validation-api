package org.opensource.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.opensource.factory.ParserFactory;
import org.opensource.parser.FileParser;
import org.opensource.types.FileType;
import org.opensource.utils.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;

import static org.opensource.types.FileType.PDF;
import static org.opensource.types.FileType.XML;

@Service
@RequiredArgsConstructor
@Log4j2
public class ValidationService {
    private String pdfFileName = "src/main/resources/input/sample.pdf";
    private String xmlFileName = "src/main/resources/input/books.xml";
    public void pdfParse() {
        log.info("parsing file : {0}", pdfFileName);
        ParserFactory.getInstance(PDF).parse(pdfFileName);
    }

    public void pdfSplit() {
        log.info("splitting file : {0}", pdfFileName);
        ParserFactory.getInstance(PDF).parse(pdfFileName);
    }

    public void xmlParse() {
        log.info("parsing file : {0}", xmlFileName);
        ParserFactory.getInstance(XML).parse(xmlFileName);
    }

    public void xmlSplit() {
        log.info("splitting file : {0}", xmlFileName);
        ParserFactory.getInstance(XML).split(xmlFileName);
    }

    public void cleanDirectory() {
        log.info("deleting file : {0}");
        FileUtils.cleanOutputDirectory(new File("src/main/resources/output/"));
    }
}
