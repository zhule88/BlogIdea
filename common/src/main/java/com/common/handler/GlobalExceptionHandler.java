package com.common.handler;



import com.common.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler
    public Result exceptionHandler(Exception ex){
        log.error(ex.getMessage());
        return Result.error("服务器未知错误");
    }
}
