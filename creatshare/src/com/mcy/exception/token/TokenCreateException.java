package com.mcy.exception.token;

public class TokenCreateException extends Exception {
    public TokenCreateException() {
        super();
    }

    public TokenCreateException(String message) {
        super(message);
    }

    public TokenCreateException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenCreateException(Throwable cause) {
        super(cause);
    }

    protected TokenCreateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
