package com.studentmgr.service;

import com.studentmgr.exception.*;
import com.studentmgr.model.Student;
import com.studentmgr.repository.IStudentRepository;

import java.util.List;

/**
 * StudentService - Business / Service Layer.
 *
 * Provides a clean high-level API to the UI layer.
 * Depends on IStudentRepository (interface), not a concrete class —
 * demonstrating ABSTRACTION and the Dependency Inversion principle.
 *
 * COMPOSITION: holds a reference to IStudentRepository.
 */
public class StudentService {

    private final IStudentRepository repository;   // Composition + Abstraction

    public StudentService(IStudentRepository repository) {
        this.repository = repository;
    }

    // ── CREATE ───────────────────────────────────────────────────
    public Student addStudent(String id, String name, int age,
                              String course, String grade, String email)
            throws InvalidDataException, DuplicateStudentException, FileOperationException {
        Student student = new Student(id, name, age, course, grade, email);
        repository.add(student);
        return student;
    }

    // ── READ ─────────────────────────────────────────────────────
    public Student getStudent(String id)
            throws StudentNotFoundException, FileOperationException {
        return repository.get(id);
    }

    public List<Student> getAllStudents() throws FileOperationException {
        return repository.getAll();
    }

    public List<Student> searchStudents(String keyword) throws FileOperationException {
        return repository.search(keyword);
    }

    // ── UPDATE ───────────────────────────────────────────────────
    public Student updateStudent(String id, String name, Integer age,
                                 String course, String grade, String email)
            throws StudentNotFoundException, InvalidDataException, FileOperationException {
        return repository.update(id, name, age, course, grade, email);
    }

    // ── DELETE ───────────────────────────────────────────────────
    public void deleteStudent(String id)
            throws StudentNotFoundException, FileOperationException {
        repository.delete(id);
    }

    public int totalStudents() throws FileOperationException {
        return repository.count();
    }
}
