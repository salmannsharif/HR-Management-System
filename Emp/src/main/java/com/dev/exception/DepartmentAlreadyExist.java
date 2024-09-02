package com.dev.exception;

public class DepartmentAlreadyExist extends RuntimeException{
    public DepartmentAlreadyExist(String message){
        super(message);
    }
}
