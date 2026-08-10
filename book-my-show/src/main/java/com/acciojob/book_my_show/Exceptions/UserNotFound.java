package com.acciojob.book_my_show.Exceptions;

public class UserNotFound extends RuntimeException {
    public UserNotFound(String message) {
        super(message);
    }
}
