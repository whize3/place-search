package com.whize.search.advice.exception;

public class CSigninFailedException extends RuntimeException {
    public CSigninFailedException(String msg, Throwable t) {
        super(msg, t);
    }

    public CSigninFailedException(String msg) {
        super(msg);
    }

    public CSigninFailedException() {
        super();
    }
}