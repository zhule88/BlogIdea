package com.handler;


import com.pojo.Result;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler
    public Result exceptionHandler(Exception ex){
        return Result.error(ex.getMessage());
    }
}
