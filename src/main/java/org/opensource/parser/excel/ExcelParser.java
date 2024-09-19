package org.opensource.parser.excel;

import lombok.extern.log4j.Log4j2;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.opensource.model.Plan;
import org.opensource.parser.FileParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Log4j2
public class ExcelParser implements FileParser {
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
        List<Plan> plans = new ArrayList<>();
        int count =0;
        FileInputStream fileInputStream =  new FileInputStream(file);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        Iterator<Row> rowIterator = xssfSheet.rowIterator();
//        rowIterator.next();
        while(rowIterator.hasNext()) {
            count++;
            Row row = rowIterator.next();
            int i=0;
            try {
                if(row.getRowNum()==0)
                    continue;
                Plan plan = new Plan(
                        (long) row.getCell(i++,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue(),
                        row.getCell(i++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue(),
                        row.getCell(i++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getDateCellValue(),
                        row.getCell(i++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getDateCellValue(),
                        row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
                System.out.println("count : "+count+" row : " + row.getRowNum() + " cell val :"+ " i : "+i + " data : "+plan );
                plans.add(plan);
            }catch (Exception e){
                System.err.println("count : "+count+" row : " + row.getRowNum() + " cell val :"+ row.getCell(i-1) + " i : "+i );
            }
        }
        System.out.println(count);
        System.out.println(plans);
//        int count=1;   //Just to indicate line number
//        for(String temp:lines)
//        {
//            System.out.println(count+" "+temp);
//            count++;
//        }
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
            List<PDDocument> pages = splitter.split(document);
            Iterator<PDDocument> iterator = pages.listIterator();
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
