package com.cxi.uninotes.exceptions;

public class InternalServerException extends RuntimeException{
    public InternalServerException(String errorMessage) {
        super(errorMessage);
    }
}
