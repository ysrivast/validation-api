package org.opensource.service;

import org.junit.jupiter.api.Test;
import org.opensource.factory.ParserFactory;
import org.opensource.parser.FileParser;
import org.opensource.parser.excel.ExcelParser;
import org.opensource.types.FileType;

import java.util.HashMap;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ValidationServiceTest {

    @Test
    void cleanDirectory() {

//        assertTrue("IP_CLAIM".startsWith("IP"));
//
//        HashMap<String, Double> h = new HashMap<>();
//        h.entrySet().stream().filter(e -> e.getValue()>0).map(e-> e.getKey()).collect(Collectors.toSet());
//        String ph = "3456789012312";
//        isMobileValid(ph);
    }

    private boolean isMobileValid(String ph) {
        if (!ph.isBlank()){
            if (ph.length() > 10) {
                return ph.startsWith("04");
            } else if (ph.length() == 10) {
                return !ph.startsWith("04");
            }
        }
        return false;
    }
    @Test
    void readExcelTest(){
        String xmlFileName = "src/main/resources/input/Plan.xlsx";
        ParserFactory.getInstance(FileType.EXCEL).parse(xmlFileName);
    }
}