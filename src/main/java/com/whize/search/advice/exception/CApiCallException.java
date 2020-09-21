package com.whize.search.advice.exception;

public class CApiCallException extends RuntimeException {

    public CApiCallException(String msg, Throwable t) {
        super(msg, t);
    }

    public CApiCallException(String msg) {
        super(msg);
    }

    public CApiCallException() {
        super();
    }
}
