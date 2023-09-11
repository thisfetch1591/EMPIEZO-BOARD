package com.empiezo.empiezo.exception;

public class PostNotFoundException extends EmpiezoException {

    private static final String MESSAGE = "존재하지 않는 게시글입니다.";
    public PostNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
