package com.studentmgr.exception;

/**
 * Raised when attempting to add a student with an already-existing ID.
 * Inherits from StudentManagerException (Inheritance).
 */
public class DuplicateStudentException extends StudentManagerException {

    private final String studentId;

    public DuplicateStudentException(String studentId) {
        super("Student with ID '" + studentId + "' already exists.");
        this.studentId = studentId;
    }

    public String getStudentId() { return studentId; }
}
