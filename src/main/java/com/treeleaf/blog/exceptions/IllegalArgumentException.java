package com.treeleaf.blog.exceptions;

public class IllegalArgumentException extends RuntimeException{
    public IllegalArgumentException(String message){
        super(message);
    }
}