package com.studentmgr.repository;

import com.studentmgr.exception.*;
import com.studentmgr.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * StudentRepository - Data Access Layer (DAL).
 *
 * Implements IStudentRepository (Interface / Polymorphism).
 * Uses FileHandler via COMPOSITION for persistence.
 * Maintains an in-memory LinkedHashMap as a cache for fast CRUD.
 */
public class StudentRepository implements IStudentRepository {

    private final FileHandler fileHandler;   // Composition
    private final Map<String, Student> records;

    public StudentRepository(FileHandler fileHandler) throws FileOperationException {
        this.fileHandler = fileHandler;
        this.records     = fileHandler.load();   // load on startup
    }

    // ── CREATE ───────────────────────────────────────────────────
    @Override
    public void add(Student student)
            throws DuplicateStudentException, FileOperationException {
        String id = student.getStudentId();
        if (records.containsKey(id))
            throw new DuplicateStudentException(id);
        records.put(id, student);
        fileHandler.save(records);
    }

    // ── READ ─────────────────────────────────────────────────────
    @Override
    public Student get(String studentId)
            throws StudentNotFoundException, FileOperationException {
        Student s = records.get(studentId.toUpperCase());
        if (s == null) throw new StudentNotFoundException(studentId);
        return s;
    }

    @Override
    public List<Student> getAll() throws FileOperationException {
        return new ArrayList<>(records.values());
    }

    @Override
    public List<Student> search(String keyword) throws FileOperationException {
        String kw = keyword.toLowerCase().trim();
        List<Student> results = new ArrayList<>();
        for (Student s : records.values()) {
            if (s.getStudentId().toLowerCase().contains(kw)
             || s.getName().toLowerCase().contains(kw)
             || s.getCourse().toLowerCase().contains(kw)) {
                results.add(s);
            }
        }
        return results;
    }

    // ── UPDATE ───────────────────────────────────────────────────
    @Override
    public Student update(String studentId, String name, Integer age,
                          String course, String grade, String email)
            throws StudentNotFoundException, InvalidDataException, FileOperationException {

        String id = studentId.toUpperCase();
        Student s = records.get(id);
        if (s == null) throw new StudentNotFoundException(id);

        if (name   != null && !name.isEmpty())   s.setName(name);
        if (age    != null)                       s.setAge(age);
        if (course != null && !course.isEmpty())  s.setCourse(course);
        if (grade  != null && !grade.isEmpty())   s.setGrade(grade);
        if (email  != null)                       s.setEmail(email);

        fileHandler.save(records);
        return s;
    }

    // ── DELETE ───────────────────────────────────────────────────
    @Override
    public void delete(String studentId)
            throws StudentNotFoundException, FileOperationException {
        String id = studentId.toUpperCase();
        if (!records.containsKey(id))
            throw new StudentNotFoundException(id);
        records.remove(id);
        fileHandler.save(records);
    }

    @Override
    public int count() { return records.size(); }
}
