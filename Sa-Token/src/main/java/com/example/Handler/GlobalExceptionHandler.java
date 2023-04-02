package com.example.Handler;

import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author duran
 * @description TODO
 * @date 2023-01-06 17:48
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public SaResult handlerException(Exception e){
     e.printStackTrace();
        return SaResult.error(e.getMessage());
    }
}
