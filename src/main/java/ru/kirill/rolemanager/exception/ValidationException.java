package ru.kirill.rolemanager.exception;

import jakarta.xml.ws.WebFault;

@WebFault
public class ValidationException extends Exception {

    public ValidationException(String message) {
        super(message);
    }
}
