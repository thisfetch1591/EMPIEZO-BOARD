package com.empiezo.empiezo.exception;

public class AlreadyExistLikesUserException extends EmpiezoException {

    private static final String MESSAGE = "이미 추천하셨습니다.";

    public AlreadyExistLikesUserException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
