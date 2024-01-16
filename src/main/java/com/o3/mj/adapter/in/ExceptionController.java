package com.o3.mj.adapter.in;

import com.o3.mj.adapter.in.dto.ErrorResponse;
import com.o3.mj.usecase.exception.RedundantCustomerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {ExceptionController.class})
public class ExceptionController {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RedundantCustomerException.class)
    public ErrorResponse handleDidNotHavRoleException(RedundantCustomerException e) {
        return new ErrorResponse(400, e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception e) {
        logger.error("server error", e);
        return new ErrorResponse(500, e.getMessage());
    }
}
