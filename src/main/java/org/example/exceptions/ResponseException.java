package org.example.exceptions;

public class ResponseException extends Exception{

    private final String message = "Response is not got!";

    @Override
    public String getMessage() {
        return message;
    }
}
