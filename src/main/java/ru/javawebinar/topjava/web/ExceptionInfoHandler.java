package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice(annotations = RestController.class)
public class ExceptionInfoHandler {

    private static final Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(HttpServletRequest req, NotFoundException e) {
        log.warn("NotFoundException at request {}: {}", req.getRequestURL(), e.getMessage());
        return e.getMessage();
    }
}
