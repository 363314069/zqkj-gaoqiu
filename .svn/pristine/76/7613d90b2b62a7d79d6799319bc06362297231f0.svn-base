package com.zqkj.exception;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.zqkj.utils.Content;
import com.zqkj.utils.R;

/**
 * Created by ace on 2017/9/8.O
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(BaseException.class)
    public R baseExceptionHandler(HttpServletResponse response, BaseException ex) {
        logger.error(ex.getMessage(),ex);
        return R.error(ex.getStatus(), ex.getMessage());
    }
    
    @ExceptionHandler(ValidataException.class)
    public R validExceptionHandler(HttpServletResponse response, ValidataException ex) {
        logger.error(ex.getMessage(),ex);
        return ex.getR();
    }
    
    @ExceptionHandler(Exception.class)
    public R otherExceptionHandler(HttpServletResponse response, Exception ex) {
        logger.error(ex.getMessage(),ex);
        return R.error(Content.EX_OTHER_CODE,ex.getMessage());
    }
}
