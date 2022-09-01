package com.skdlsco.donelib.global.error;

import com.skdlsco.donelib.global.error.code.ErrorCode;
import com.skdlsco.donelib.global.error.code.GlobalErrorCode;
import com.skdlsco.donelib.global.error.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return makeErrorResponse(GlobalErrorCode.PAGE_NOT_FOUND);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException e, HttpServletRequest request) {
        return makeErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception e, HttpServletRequest request) {
        String uri = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUriString();
        log.error("handleException exception-name={} uri={} request-uri={}", e.getClass().getName(), uri, request.getRequestURI(), e);
        return makeErrorResponse(GlobalErrorCode.INTERVAL_SERVER_ERROR);
    }

    public ResponseEntity<Object> makeErrorResponse(ErrorCode code) {
        ErrorResponse response = ErrorResponse.of(code);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }
}