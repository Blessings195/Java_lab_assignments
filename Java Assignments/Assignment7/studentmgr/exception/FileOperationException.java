package com.studentmgr.exception;

/**
 * Wraps IOException and data-parsing errors from file operations.
 * Inherits from StudentManagerException (Inheritance).
 */
public class FileOperationException extends StudentManagerException {

    public FileOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileOperationException(String message) {
        super(message);
    }
}
