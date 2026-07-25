package com.backend.investment.Exception;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String Message){
        super(Message);
    }
}
