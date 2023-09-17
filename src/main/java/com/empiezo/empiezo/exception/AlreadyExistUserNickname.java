package com.empiezo.empiezo.exception;

public class AlreadyExistUserNickname extends EmpiezoException {

    private static final String MESSAGE = "이미 존재하는 닉네임입니다.";

    public AlreadyExistUserNickname() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
