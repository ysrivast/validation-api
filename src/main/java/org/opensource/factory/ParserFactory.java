package org.opensource.factory;

import lombok.AllArgsConstructor;
import org.opensource.parser.FileParser;
import org.opensource.parser.excel.ExcelParser;
import org.opensource.parser.pdf.PDFParser;
import org.opensource.parser.xml.ParallelXMLParser;
import org.opensource.parser.xml.XMLParser;
import org.opensource.types.FileType;

@AllArgsConstructor
public class ParserFactory {
    private static final XMLParser xmlParser = new XMLParser();
    private static final ParallelXMLParser parallelXMLParser = new ParallelXMLParser();
    private static final ExcelParser excelParser = new ExcelParser();
    private static final PDFParser pdfParser = new PDFParser();


    public static FileParser getInstance(FileType fileType){
        switch (fileType) {
            case PDF -> {
                return pdfParser;
            }
            case XML -> {
                return parallelXMLParser;
            }
            case EXCEL -> {
                return excelParser;
            }
            default -> {
                return null;
            }
        }
    }
}
