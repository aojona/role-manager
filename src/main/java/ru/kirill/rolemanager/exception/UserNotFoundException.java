package ru.kirill.rolemanager.exception;

import jakarta.xml.ws.WebFault;

@WebFault
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User is not found");
    }
}
