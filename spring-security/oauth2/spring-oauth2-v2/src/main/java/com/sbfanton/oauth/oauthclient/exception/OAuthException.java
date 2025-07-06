package com.sbfanton.oauth.oauthclient.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class OAuthException extends Exception {

    private HttpStatus status;

    public OAuthException() {}

    public OAuthException(String message) {
        super(message);
    }

    public OAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public OAuthException(HttpStatus status) {
        this.status = status;
    }

    public OAuthException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public OAuthException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    public OAuthException(Throwable cause, HttpStatus status) {
        super(cause);
        this.status = status;
    }

    public OAuthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus status) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.status = status;
    }
}
