package com.studentmgr.exception;

/**
 * Raised when user-supplied data fails validation.
 * Inherits from StudentManagerException (Inheritance).
 */
public class InvalidDataException extends StudentManagerException {

    public InvalidDataException(String message) {
        super(message);
    }
}
