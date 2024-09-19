package org.opensource.utils;

import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Log4j2
public class FileUtils {

    public static void exists(File file) {
        if(!file.exists()){
            log.info("file not found with name : {0}" , file.getAbsolutePath());
        }
    }

    public static void cleanOutputDirectory(File file) {
        if(!file.exists())
            return;
        for (File f : file.listFiles()){
            if(!f.isDirectory()){
                f.delete();
            }else{
                cleanOutputDirectory(f);
            }
            f.delete();
        }
    }

    public static void cleanOutputDirectory1(String xmlFilePath) {
        File file = new File(xmlFilePath);
        if(file.exists()) {
            Arrays.stream(file.listFiles()).parallel().forEach(f ->{
                f.delete();
            });
//            for (String fileName : file.list()) {
//                File currentFile = new File(file.getPath(), fileName);
//                currentFile.delete();
//            }
            file.delete();
        }
    }

}
