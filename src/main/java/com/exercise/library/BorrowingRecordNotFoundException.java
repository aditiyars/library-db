package com.exercise.library;

public class BorrowingRecordNotFoundException extends RuntimeException{
    public BorrowingRecordNotFoundException(String message){
        super(message);
    }    
}
