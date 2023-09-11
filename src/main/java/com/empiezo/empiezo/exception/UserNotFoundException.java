package com.empiezo.empiezo.exception;

public class UserNotFoundException extends EmpiezoException{

    private static final String MESSAGE = "존재하지 않는 사용자입니다.";
    public UserNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
