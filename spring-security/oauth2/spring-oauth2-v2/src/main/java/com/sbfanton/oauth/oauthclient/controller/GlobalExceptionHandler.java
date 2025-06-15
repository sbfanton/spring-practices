package com.sbfanton.oauth.oauthclient.controller;

import com.sbfanton.oauth.oauthclient.exception.ServiceException;
import com.sbfanton.oauth.oauthclient.model.dto.StatusDTO;
import com.sbfanton.oauth.oauthclient.utils.constants.CodeTypes;
import com.sbfanton.oauth.oauthclient.utils.constants.DateFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ Exception.class, ServiceException.class })
    public ResponseEntity<?> handleGeneralException(Exception ex) {
        HttpStatus status = null;
        if(ex instanceof ServiceException) {
            status = ((ServiceException) ex).getStatus();
        }
        return ResponseEntity
                .status(status != null ? status : HttpStatus.INTERNAL_SERVER_ERROR)
                .body(StatusDTO.builder()
                                .code(CodeTypes.ERROR.name())
                                .message(ex.getMessage())
                                .timestamp(DateFormatter.basicFormatter(DateFormatter.BASIC_FORMATTER))
                        .build()
                );
    }
}
