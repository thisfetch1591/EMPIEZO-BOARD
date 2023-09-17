package com.empiezo.empiezo.exception;

public class AlreadyExistUserEmailException extends EmpiezoException{

    private static final String MESSAGE = "이미 존재하는 이메일입니다.";

    public AlreadyExistUserEmailException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
