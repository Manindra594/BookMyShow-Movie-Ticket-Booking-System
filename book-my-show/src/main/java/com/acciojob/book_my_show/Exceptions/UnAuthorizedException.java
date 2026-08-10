package com.acciojob.book_my_show.Exceptions;

public class UnAuthorizedException extends RuntimeException{
    public UnAuthorizedException(String messege){
        super(messege);
    }
}
