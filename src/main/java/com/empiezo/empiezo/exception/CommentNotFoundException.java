package com.empiezo.empiezo.exception;

public class CommentNotFoundException extends EmpiezoException {
    private static final String MESSAGE = "존재하지 않는 댓글입니다.";
    public CommentNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
