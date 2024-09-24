package org.opensource.exception;

import org.springframework.http.HttpStatus;

public class FileUploadException extends RuntimeException{
    private String message;
    private HttpStatus status;

    public FileUploadException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }

    public FileUploadException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.message = message;
        this.status = status;
    }
}
