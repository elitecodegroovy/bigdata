package com.kafka.utils;

/**
 * @author JohnLiu
 * @version 0.1.0
 * @date 2014/8/27
 */
public class ProcessingException extends Exception {
    private Throwable cause;

    public ProcessingException() {
        super();
    }

    public ProcessingException(String msg) {
        super(msg);
    }

    public ProcessingException(String msg, Throwable cause) {
        super(msg);
        setCause(cause);
    }

    private void setCause(Throwable cause) {
        if (ExceptionHelper.supportsNestedThrowable()) {
            ExceptionHelper.setCause(this, cause);
        } else {
            this.cause = cause;
        }
    }
}
