package com.studentmgr.model;

import com.studentmgr.exception.InvalidDataException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Student - Entity / Model class.
 *
 * Demonstrates ENCAPSULATION: all fields are private; access is controlled
 * through public getters and validated setters.
 */
public class Student {

    // ── Constants ────────────────────────────────────────────────
    private static final Set<String> VALID_GRADES = new HashSet<>(
        Arrays.asList("A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D", "F", "N/A")
    );
    private static final DateTimeFormatter FMT =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // ── Private Fields (Encapsulation) ───────────────────────────
    private String studentId;
    private String name;
    private int    age;
    private String course;
    private String grade;
    private String email;
    private String createdAt;

    // ── Constructor ──────────────────────────────────────────────
    public Student(String studentId, String name, int age,
                   String course, String grade, String email)
            throws InvalidDataException {
        setStudentId(studentId);
        setName(name);
        setAge(age);
        setCourse(course);
        setGrade(grade);
        this.email     = (email == null) ? "" : email.trim();
        this.createdAt = LocalDateTime.now().format(FMT);
    }

    // Overloaded constructor with defaults
    public Student(String studentId, String name, int age, String course)
            throws InvalidDataException {
        this(studentId, name, age, course, "N/A", "");
    }

    // ── Validated Setters (Encapsulation + Data Integrity) ───────
    public void setStudentId(String id) throws InvalidDataException {
        if (id == null || id.trim().isEmpty())
            throw new InvalidDataException("Student ID cannot be empty.");
        this.studentId = id.trim().toUpperCase();
    }

    public void setName(String name) throws InvalidDataException {
        if (name == null || name.trim().isEmpty())
            throw new InvalidDataException("Student name cannot be empty.");
        // Title case
        String[] words = name.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String w : words) {
            sb.append(Character.toUpperCase(w.charAt(0)))
              .append(w.substring(1).toLowerCase())
              .append(" ");
        }
        this.name = sb.toString().trim();
    }

    public void setAge(int age) throws InvalidDataException {
        if (age < 10 || age > 100)
            throw new InvalidDataException("Age must be between 10 and 100. Got: " + age);
        this.age = age;
    }

    public void setCourse(String course) throws InvalidDataException {
        if (course == null || course.trim().isEmpty())
            throw new InvalidDataException("Course cannot be empty.");
        this.course = course.trim();
    }

    public void setGrade(String grade) throws InvalidDataException {
        if (grade == null) grade = "N/A";
        String g = grade.trim().toUpperCase();
        if (!VALID_GRADES.contains(g))
            throw new InvalidDataException(
                "Invalid grade '" + grade + "'. Valid: " + VALID_GRADES);
        this.grade = g;
    }

    public void setEmail(String email) {
        this.email = (email == null) ? "" : email.trim();
    }

    // ── Getters ──────────────────────────────────────────────────
    public String getStudentId() { return studentId; }
    public String getName()      { return name; }
    public int    getAge()       { return age; }
    public String getCourse()    { return course; }
    public String getGrade()     { return grade; }
    public String getEmail()     { return email; }
    public String getCreatedAt() { return createdAt; }

    // For file reconstruction only
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    // ── Serialisation to proper CSV ───────────────────────────────
    // Format: studentId,"name",age,"course",grade,"email","createdAt"
    // Fields that may contain commas are wrapped in double-quotes.
    // Any double-quote inside a value is escaped as two double-quotes ("").
    public String toCsvLine() {
        return String.join(",",
            studentId,
            csvQuote(name),
            String.valueOf(age),
            csvQuote(course),
            grade,
            csvQuote(email),
            csvQuote(createdAt));
    }

    public static Student fromCsvLine(String line) throws InvalidDataException {
        String[] parts = parseCsvLine(line);
        if (parts.length < 7)
            throw new InvalidDataException("Corrupted record: " + line);
        Student s = new Student(
            parts[0].trim(),
            parts[1],
            Integer.parseInt(parts[2].trim()),
            parts[3],
            parts[4].trim(),
            parts[5]
        );
        s.setCreatedAt(parts[6]);
        return s;
    }

    /** Wrap a field in double-quotes and escape any internal double-quotes. */
    private static String csvQuote(String s) {
        if (s == null) s = "";
        return "\"" + s.replace("\"", "\"\"") + "\"";
    }

    /**
     * Parse one CSV line respecting double-quoted fields.
     * Handles commas and escaped double-quotes ("") inside quoted fields.
     */
    private static String[] parseCsvLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (inQuotes) {
                if (c == '"') {
                    // Peek ahead: "" means a literal quote inside a quoted field
                    if (i + 1 < line.length() && line.charAt(i + 1) == '"') {
                        sb.append('"');
                        i++;           // skip the second quote
                    } else {
                        inQuotes = false;   // closing quote
                    }
                } else {
                    sb.append(c);
                }
            } else {
                if (c == '"') {
                    inQuotes = true;
                } else if (c == ',') {
                    fields.add(sb.toString());
                    sb.setLength(0);
                } else {
                    sb.append(c);
                }
            }
        }
        fields.add(sb.toString());   // last field
        return fields.toArray(new String[0]);
    }

    // ── toString (Polymorphism via @Override) ────────────────────
    @Override
    public String toString() {
        return String.format(
            "  ID      : %s%n  Name    : %s%n  Age     : %d%n" +
            "  Course  : %s%n  Grade   : %s%n  Email   : %s%n  Added   : %s",
            studentId, name, age, course, grade,
            email.isEmpty() ? "N/A" : email, createdAt
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        return studentId.equals(((Student) o).studentId);
    }

    @Override
    public int hashCode() { return studentId.hashCode(); }
}
