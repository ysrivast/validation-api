package org.opensource.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(FileUploadException.class)
    protected ProblemDetail handleFileUploadException(FileUploadException ex, WebRequest request) {
        log.error("Exception occurred ",ex);
        ProblemDetail body = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage());
        body.setType(URI.create("http://my-app-host.com/errors/not-found"));
        body.setTitle("Something went wrong");
        body.setDetail(ex.getMessage());
        return body;
    }
    @ExceptionHandler(Exception.class)
    protected ProblemDetail handleAllException(Exception ex, WebRequest request) {
        log.error("Exception occurred ",ex);
        ProblemDetail body = ProblemDetail
                .forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,ex.getLocalizedMessage());
        body.setType(URI.create("http://my-app-host.com/errors/not-found"));
        body.setTitle("Something went wrong");
        return body;
    }

}
