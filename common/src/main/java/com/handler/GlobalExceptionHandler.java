package com.handler;


import com.pojo.Result;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler
    public Result exceptionHandler(Exception ex){
        System.out.println(ex.getMessage());
        return Result.error(ex.getMessage());
    }
}
