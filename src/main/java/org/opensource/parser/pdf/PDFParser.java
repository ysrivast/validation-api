package org.opensource.parser.pdf;

import lombok.extern.log4j.Log4j2;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.opensource.parser.FileParser;
import org.opensource.utils.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Log4j2
public class PDFParser implements FileParser {
    @Override
    public void parse(String fileName) {
        File file = new File(fileName);
        if(!file.exists()){
            log.info("file not found with name : {0}" , fileName);
            return;
        }
        try {
            reaLineByLine(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private void reaLineByLine(File file) throws IOException {
        PDDocument document = Loader.loadPDF(file);
        PDFTextStripper pdfStripper = new PDFTextStripper();
        pdfStripper.setStartPage(1);
        pdfStripper.setEndPage(1);
        pdfStripper.setSortByPosition(true);

        //load all lines into a string
        String pages = pdfStripper.getText(document);

        //split by detecting newline
        String[] lines = pages.split("\r\n|\r|\n");

        int count=1;   //Just to indicate line number
        for(String temp:lines)
        {
            System.out.println(count+" "+temp);
            count++;
        }
    }

    @Override
    public void split(String fileName) {
        File file = new File(fileName);
        if(!file.exists()){
            log.info("file not found with name : {0}" , fileName);
            return;
        }
        Splitter splitter = new Splitter();
        PDDocument document = null;
        try {
            document = Loader.loadPDF(file);
            List<PDDocument> Pages = splitter.split(document);
            Iterator<PDDocument> iterator = Pages.listIterator();
            int i = 1;
            while(iterator.hasNext()) {
                PDDocument pd = iterator.next();
                log.info("pdf text : {0}", "src/main/resources/output/"+ i +".pdf");
                pd.save("src/main/resources/output/"+ i++ +".pdf");
            }
            document.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
