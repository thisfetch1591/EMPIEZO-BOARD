package com.empiezo.empiezo.exception;

public class AlreadyExistUsernameException extends EmpiezoException {

    private static final String MESSAGE = "이미 존재하는 아이디입니다.";

    public AlreadyExistUsernameException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
