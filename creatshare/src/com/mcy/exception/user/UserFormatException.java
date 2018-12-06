package com.mcy.exception.user;

public class UserFormatException extends Exception {
    public UserFormatException() {
        super();
    }

    public UserFormatException(String message) {
        super(message);
    }

    public UserFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserFormatException(Throwable cause) {
        super(cause);
    }

    protected UserFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
