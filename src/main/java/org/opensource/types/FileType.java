package org.opensource.types;

import lombok.Getter;

@Getter
public enum FileType {
    PDF("pdf"), XML("xml"), EXCEL("excel");
    private String value;
    FileType(String value) {
        this.value= value;
    }
}
