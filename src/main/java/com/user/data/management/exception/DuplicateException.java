package com.user.data.management.exception;

public class DuplicateException extends RuntimeException{
    private static final long serialVersionUID = -4146860850978717726L;

    public DuplicateException(final String message){
        super(message);
    }
}
