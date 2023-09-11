package com.empiezo.empiezo.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class EmpiezoException extends RuntimeException {

    public final Map<String, String> validation = new HashMap<>();

    public EmpiezoException(String message) {
        super(message);
    }

    public EmpiezoException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }
}
