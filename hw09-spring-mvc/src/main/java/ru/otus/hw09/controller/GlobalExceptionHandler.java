package ru.otus.hw09.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.hw09.exceptions.EntityNotFoundException;

import java.util.Map;

@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handeNotFoundException(EntityNotFoundException ex) {
        return new ModelAndView("customError", Map.of("errorText", ex.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView handeIllegalArgumentException(IllegalArgumentException ex) {
        return new ModelAndView("customError", Map.of("errorText", ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handeException(Exception ex) {
        ex.printStackTrace();
        return new ModelAndView("customError", Map.of("errorText", ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
