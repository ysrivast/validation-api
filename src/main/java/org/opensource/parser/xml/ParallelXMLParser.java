package org.opensource.parser.xml;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lombok.extern.log4j.Log4j2;
import org.opensource.model.Book;
import org.opensource.model.Library;
import org.opensource.parser.FileParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
public class ParallelXMLParser implements FileParser {
    @Override
    public void parse(String fileName) {
        File file = new File(fileName);
        if(!file.exists()){
            log.info("file not found with name : {0}" , fileName);
            return;
        }
        try {
            JAXBContext jc = JAXBContext.newInstance(Library.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            Library library = (Library) unmarshaller.unmarshal(file);
            log.info("number of object : {0}" , library.getBooks().size());
            List<Book> filteredBool = library.getBooks().stream().filter(book -> "1992".equals(book.getYear())).toList();
            Map<String, List<Book>> groups = library.getBooks().stream().collect(Collectors.groupingBy(Book::getYear));
            log.info("number of object : {0}" , filteredBool.size());
            writeToXml(groups);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

    }

    private void writeToXml(Map<String, List<Book>> groups) throws JAXBException {
        String xmlFilePath = "src/main/resources/output/";

        JAXBContext jc = JAXBContext.newInstance(Library.class);
        Marshaller marshaller = jc.createMarshaller();
        groups.entrySet().stream().forEach(
                group -> {
                    log.info("writing for year : {0} and count : {1}" , group.getKey(), group.getValue().size());
                    try {
                        cleanOutputDirectory(xmlFilePath+group.getKey());
                        marshaller.marshal(new Library(group.getValue()), new File(xmlFilePath +group.getKey()+"/"+ group.getKey()+".xml"));
                    } catch (JAXBException e) {
                        log.info("error in writing file -> {0}" , e.getCause());
                        e.printStackTrace();
                    }
                }
        );
    }

    private void cleanOutputDirectory(String xmlFilePath) {
        File file = new File(xmlFilePath);
        if(file.exists()){
            for(String fileName : file.list()){
                File currentFile = new File(file.getPath(),fileName);
                currentFile.delete();
            }
            file.delete();
        }
        if(!file.exists()){
            file.mkdir();
            log.info("directory created!!");
        }
    }

    @Override
    public void split(String fileName) {

//        String xmlFilePath = "src/main/resources/output/books.xml";
        File file = new File(fileName);
        if(!file.exists()){
            log.info("file not found with name : {0}" , fileName);
            return;
        }
          Unmarshaller unmarshaller = null;
        try {
            JAXBContext jc = JAXBContext.newInstance(Library.class);
            unmarshaller = jc.createUnmarshaller();
            Library library = (Library) unmarshaller.unmarshal(file);
            log.info("number of object : {0}" , library.getBooks().size());
            writeToXml(library.getBooks());
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
    }

    /**
     *
     * TODO:
     */
    private void writeToXml( List<Book> groups) throws JAXBException {
        String xmlFilePath = "src/main/resources/output/";
        JAXBContext jc = JAXBContext.newInstance(Library.class);
        Marshaller marshaller = jc.createMarshaller();
        groups.stream().parallel().forEach(
                book -> {
                    try {
                        cleanOutputDirectory(xmlFilePath+book.getTitle());
                        marshaller.marshal(new Library(List.of(book)), new File(xmlFilePath +book.getTitle()+"/"+ book.getTitle()+".xml"));
                    } catch (JAXBException e) {
                        log.info("error in writing file -> {0}" , e.getCause());
                        e.printStackTrace();
                    }
                }
        );
    }


    private void listAllFile(File file, List<File> files) {
        for (File f : file.listFiles()){
            if(!f.isDirectory()){
                files.add(f);
            }else{
                listAllFile(f, files);
            }
        }
    }
}
