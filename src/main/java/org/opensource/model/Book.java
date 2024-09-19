package org.opensource.model;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlRootElement(name="book")
@XmlAccessorType(XmlAccessType.FIELD)
public class Book {
    @XmlElement(name="title")
    private String title;
    @XmlElement(name="author")
    private String author;
    @XmlElement(name="genre")
    private String genre;
    @XmlElement(name="year")
    private String year;
    @XmlElement(name="publisher")
    private String publisher;
}
