package com.mcy.exception.token;

public class TokenTimeoutException extends Exception {
    public TokenTimeoutException() {
        super();
    }

    public TokenTimeoutException(String message) {
        super(message);
    }

    public TokenTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenTimeoutException(Throwable cause) {
        super(cause);
    }

    protected TokenTimeoutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
