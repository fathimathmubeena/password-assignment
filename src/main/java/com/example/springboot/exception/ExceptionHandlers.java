package com.example.springboot.exception;

import com.example.springboot.controller.response.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseWrapper handleSiteNotFoundException(NotFoundException siteNotFoundException) {
        return new ResponseWrapper(siteNotFoundException.getResult(), null);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseWrapper handleDuplicateNameExceptionException(DuplicateKeyException duplicateNameException) {
        return new ResponseWrapper(duplicateNameException.getResult(), null);
    }

    @ExceptionHandler(InvalidException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public ResponseWrapper handleInvalidNumberException(InvalidException invalidNumberException) {
        return new ResponseWrapper(invalidNumberException.getResult(), null);
    }


}
