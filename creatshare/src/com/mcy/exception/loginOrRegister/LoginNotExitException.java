package com.mcy.exception.loginOrRegister;

public class LoginNotExitException extends Exception {

    public LoginNotExitException() {
        super();
    }

    public LoginNotExitException(String message) {
        super(message);
    }

    public LoginNotExitException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginNotExitException(Throwable cause) {
        super(cause);
    }

    protected LoginNotExitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
