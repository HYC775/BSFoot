package com.example.bsfood.exception;
import com.example.bsfood.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public Result handler(RuntimeException e){
         Result.error("运行时异常：----------------{}"+e.getMessage());
         System.out.println("运行时异常：");
         return Result.error(e.getMessage());
    }

 }