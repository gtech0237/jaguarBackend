package com.backend.investment.Exception;


public class UserAlreadyExistException extends RuntimeException{

    public UserAlreadyExistException(String msg){
        super(msg);
    }
}
