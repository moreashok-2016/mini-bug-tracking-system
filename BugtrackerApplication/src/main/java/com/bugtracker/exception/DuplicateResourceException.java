package com.bugtracker.exception;

public class DuplicateResourceException extends RuntimeException 
{
    public DuplicateResourceException(String message) 
    {
        super(message);
    }
}