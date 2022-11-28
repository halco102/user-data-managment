package com.user.data.management.exception;

public class NotFoundException extends RuntimeException{

    private static final long serialVersionUID = -4146860850978717726L;

    public NotFoundException(final String message){
        super(message);
    }

}
