package com.n26.handler;


import com.n26.dto.error.ErrorDetail;
import com.n26.exception.FutureDatedTransactionException;
import com.n26.exception.OutDatedTransactionException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;


/**
 * @author ttdduman
 */

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    /**
     * Handles HttpMessageNotReadableException
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        logger.debug("HttpMessageNotReadableException is handled");
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetail.setTitle("Message Not Readable");
        errorDetail.setDetail(ex.getMessage());

        String message = ex.getCause().getLocalizedMessage();
        if (message.contains("Date") || message.contains("BigDecimal")) {
            return handleExceptionInternal(ex, errorDetail, headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
        }


        return handleExceptionInternal(ex, errorDetail, headers, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Handles MethodArgumentNotValidException
     * @param manve
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException manve, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.debug("MethodArgumentNotValidException is handled");
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        errorDetail.setTitle("Validation Failed");
        errorDetail.setDetail(manve.getMessage());

        return handleExceptionInternal(manve, errorDetail, headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    /**
     * Handles OutDatedTransactionException
     * @param odte
     * @param request
     * @return
     */
    @ExceptionHandler(OutDatedTransactionException.class)
    public ResponseEntity<?> handleOutDatedTransactionException(OutDatedTransactionException odte, WebRequest request) {
        logger.debug("OutDatedTransactionException is handled");
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.NO_CONTENT.value());
        errorDetail.setTitle("Transaction timestamp is old");
        errorDetail.setDetail(odte.getMessage());
        return handleExceptionInternal(odte, errorDetail, new HttpHeaders(), HttpStatus.NO_CONTENT, request);
    }

    /**
     * Handles FutureDatedTransactionException
     * @param fdte
     * @param request
     * @return
     */
    @ExceptionHandler(FutureDatedTransactionException.class)
    public ResponseEntity<?> handleFutureDatedTransactionException(FutureDatedTransactionException fdte, WebRequest request) {
        logger.debug("FutureDatedTransactionException is handled");
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        errorDetail.setTitle("Transaction timestamp is in the future");
        errorDetail.setDetail(fdte.getMessage());
        return handleExceptionInternal(fdte, errorDetail, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
    }


}
