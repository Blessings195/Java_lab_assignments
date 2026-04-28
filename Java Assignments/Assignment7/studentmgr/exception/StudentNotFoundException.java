package com.studentmgr.exception;

/**
 * Raised when a student lookup fails for a given ID.
 * Inherits from StudentManagerException (Inheritance).
 */
public class StudentNotFoundException extends StudentManagerException {

    private final String studentId;

    public StudentNotFoundException(String studentId) {
        super("Student with ID '" + studentId + "' not found.");
        this.studentId = studentId;
    }

    public String getStudentId() { return studentId; }
}
