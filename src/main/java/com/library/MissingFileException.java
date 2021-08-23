package com.library;

public class MissingFileException extends Exception{
    public MissingFileException() {};
    public MissingFileException(String message) {
        super(message);
    }
}
