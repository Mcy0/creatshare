package com.mcy.exception.token;

public class TokenVerifyOrParseFailException extends Exception {
    public TokenVerifyOrParseFailException() {
        super();
    }

    public TokenVerifyOrParseFailException(String message) {
        super(message);
    }

    public TokenVerifyOrParseFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenVerifyOrParseFailException(Throwable cause) {
        super(cause);
    }

    protected TokenVerifyOrParseFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
