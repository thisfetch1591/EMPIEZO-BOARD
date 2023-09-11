package com.empiezo.empiezo.exception;

public class LikeNotFoundException extends EmpiezoException{

    private static final String MESSAGE = "존재하지 않는 추천입니다.";
    public LikeNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}

