package com.sbfanton.oauth.oauthclient.controller;

import com.sbfanton.oauth.oauthclient.exception.EmailVerificationException;
import com.sbfanton.oauth.oauthclient.exception.OAuthException;
import com.sbfanton.oauth.oauthclient.exception.ServiceException;
import com.sbfanton.oauth.oauthclient.model.dto.StatusDTO;
import com.sbfanton.oauth.oauthclient.utils.constants.CodeTypes;
import com.sbfanton.oauth.oauthclient.utils.constants.DateFormatter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Value("${front.url}")
    private String frontUrl;

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

    @ExceptionHandler({ OAuthException.class })
    public void oauthError(HttpServletResponse response, Exception ex) throws IOException {
        String errMsg = ex.getMessage();
        String codedMsg = URLEncoder.encode(errMsg, StandardCharsets.UTF_8)
                .replace("+", "%20");
        Cookie errorCookie = new Cookie("oauth2_error", codedMsg);
        errorCookie.setPath("/");
        errorCookie.setMaxAge(30);
        errorCookie.setHttpOnly(false);
        errorCookie.setSecure(false);
        response.addCookie(errorCookie);

        response.sendRedirect(frontUrl + "/auth/callback");
    }

    @ExceptionHandler({ EmailVerificationException.class })
    public void emailValidationError(HttpServletResponse response, Exception ex) throws IOException {
        String errMsg = ex.getMessage();
        String codedMsg = URLEncoder.encode(errMsg, StandardCharsets.UTF_8)
                .replace("+", "%20");
        Cookie errorCookie = new Cookie("email_verification_error", codedMsg);
        errorCookie.setPath("/");
        errorCookie.setMaxAge(30);
        errorCookie.setHttpOnly(false);
        errorCookie.setSecure(false);
        response.addCookie(errorCookie);

        response.sendRedirect(frontUrl + "/email-verified");
    }
}
