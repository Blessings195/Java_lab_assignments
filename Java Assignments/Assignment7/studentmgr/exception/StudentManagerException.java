package com.studentmgr.exception;

/**
 * StudentManagerException - Base custom exception.
 *
 * Demonstrates INHERITANCE: all application-specific exceptions
 * extend this class, forming a clean exception hierarchy.
 */
public class StudentManagerException extends Exception {

    public StudentManagerException(String message) {
        super(message);
    }

    public StudentManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}
