package com.studentmgr.repository;

import com.studentmgr.exception.*;
import com.studentmgr.model.Student;
import java.util.List;

/**
 * IStudentRepository - Interface defining the data-access contract.
 *
 * Demonstrates ABSTRACTION and POLYMORPHISM: any class implementing
 * this interface can be plugged in (e.g., swap file storage for a DB)
 * without changing the service or UI layers.
 */
public interface IStudentRepository {

    void   add(Student student)
        throws DuplicateStudentException, FileOperationException;

    Student get(String studentId)
        throws StudentNotFoundException, FileOperationException;

    List<Student> getAll()
        throws FileOperationException;

    List<Student> search(String keyword)
        throws FileOperationException;

    Student update(String studentId, String name, Integer age,
                   String course, String grade, String email)
        throws StudentNotFoundException, InvalidDataException, FileOperationException;

    void delete(String studentId)
        throws StudentNotFoundException, FileOperationException;

    int count() throws FileOperationException;
}
