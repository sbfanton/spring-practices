package com.sbfanton.oauth.oauthclient.exception;

public class EmailVerificationException extends Exception {

    public EmailVerificationException() {
    }

    public EmailVerificationException(String message) {
        super(message);
    }

    public EmailVerificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailVerificationException(Throwable cause) {
        super(cause);
    }

    public EmailVerificationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
