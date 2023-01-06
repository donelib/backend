package com.skdlsco.donelib.global.error;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.skdlsco.donelib.global.error.code.ErrorCode;
import com.skdlsco.donelib.global.error.code.GlobalErrorCode;
import com.skdlsco.donelib.global.error.exception.BadRequestException;
import com.skdlsco.donelib.global.error.exception.BusinessException;
import com.skdlsco.donelib.global.log.aop.annotation.TraceExclude;
import com.skdlsco.donelib.global.log.tracelog.TraceLogManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestControllerAdvice
@TraceExclude
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    final private TraceLogManager traceLogManager;

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return makeErrorResponse(GlobalErrorCode.PAGE_NOT_FOUND);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        traceLogManager.setException(ex);
        return makeErrorResponse(ex.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllException(Exception ex) {
        traceLogManager.setException(ex);
        return makeErrorResponse(GlobalErrorCode.INTERVAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return makeErrorResponse(GlobalErrorCode.METHOD_NOW_ALLOWED);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        return resolveBadRequestException(ex);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(BindException ex) {
        return resolveBadRequestException(ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return resolveBadRequestException(ex);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(HttpMessageNotReadableException ex) {
        Throwable throwable = ex.getMostSpecificCause();
        if (throwable instanceof InvalidFormatException invalidFormatException) {
            return resolveBadRequestException(invalidFormatException);
        }
        return makeErrorResponse(GlobalErrorCode.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> resolveBadRequestException(Exception ex) {
        List<ErrorDetail> detail = Collections.emptyList();

        if (ex instanceof InvalidFormatException) {
            detail = getInvalidFormatExceptionDetail((InvalidFormatException) ex);
        } else if (ex instanceof BindException) {
            detail = getBindExceptionDetail((BindException) ex);
        } else if (ex instanceof BadRequestException) {
            detail = getBadRequestExceptionDetail((BadRequestException) ex);
        }
        return makeErrorResponse(GlobalErrorCode.BAD_REQUEST, detail);
    }

    private List<ErrorDetail> getBadRequestExceptionDetail(BadRequestException ex) {
        ArrayList<ErrorDetail> errorDetail = new ArrayList<>();
        if (ex.getErrorDetail() != null)
            errorDetail.add(ex.getErrorDetail());
        return errorDetail;
    }

    private List<ErrorDetail> getBindExceptionDetail(BindException ex) {
        ArrayList<ErrorDetail> errorDetail = new ArrayList<>();
        for (ObjectError error : ex.getGlobalErrors()) {
            errorDetail.add(ErrorDetail.of(error.getObjectName(), error.getDefaultMessage()));
        }
        for (FieldError error : ex.getFieldErrors()) {
            try {
                TypeMismatchException mismatchException = error.unwrap(TypeMismatchException.class);
                String fieldName = error.getField();
                String fieldType = mismatchException.getRequiredType().getSimpleName();
                String reason = fieldName + " should be of type " + fieldType;

                errorDetail.add(ErrorDetail.of(fieldName, reason));
            } catch (IllegalArgumentException e) {
                // TypeMismatchException 아닌 경우
                errorDetail.add(ErrorDetail.of(error.getField(), error.getDefaultMessage()));
            }
        }
        return errorDetail;
    }

    private List<ErrorDetail> getInvalidFormatExceptionDetail(InvalidFormatException ex) {
        String fieldName = getInvalidFormatExceptionFieldName(ex);
        String fieldType = ex.getTargetType().getSimpleName();
        String reason = fieldName + " should be of type " + fieldType;

        return Collections.singletonList(ErrorDetail.of(fieldName, reason));
    }

    private String getInvalidFormatExceptionFieldName(InvalidFormatException ex) {
        for (JsonMappingException.Reference r : ex.getPath()) {
            return r.getFieldName();
        }
        return "";
    }

    public ResponseEntity<ErrorResponse> makeErrorResponse(ErrorCode code) {
        return makeErrorResponse(code, code.getMessage());
    }

    public ResponseEntity<ErrorResponse> makeErrorResponse(ErrorCode code, List<ErrorDetail> errors) {
        return makeErrorResponse(code, code.getMessage(), errors);
    }

    public ResponseEntity<ErrorResponse> makeErrorResponse(ErrorCode code, String message) {
        return makeErrorResponse(code, message, Collections.emptyList());
    }

    public ResponseEntity<ErrorResponse> makeErrorResponse(ErrorCode code, String message, List<ErrorDetail> errors) {
        ErrorResponse response = ErrorResponse.of(code, message, errors);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }
}