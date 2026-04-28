package com.studentmgr.ui;

import com.studentmgr.exception.*;
import com.studentmgr.model.Student;
import com.studentmgr.service.StudentService;

import java.util.List;
import java.util.Scanner;

/**
 * ConsoleUI - Presentation / UI Layer.
 *
 * Handles all user interaction via the command-line interface.
 * Calls StudentService for every operation; never touches files directly.
 */
public class ConsoleUI {

    private final StudentService service;
    private final Scanner        scanner;

    public ConsoleUI(StudentService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    // ── Main loop ────────────────────────────────────────────────
    public void run() {
        while (true) {
            try {
                printMenu();
                String choice = scanner.nextLine().trim();
                switch (choice) {
                    case "1" -> menuAdd();
                    case "2" -> menuViewAll();
                    case "3" -> menuViewOne();
                    case "4" -> menuUpdate();
                    case "5" -> menuDelete();
                    case "6" -> menuSearch();
                    case "0" -> { System.out.println("\n  Goodbye!\n"); return; }
                    default  -> System.out.println("  [!] Invalid option.");
                }
            } catch (FileOperationException e) {
                System.out.println("  [FILE ERROR] " + e.getMessage());
            }
        }
    }

    // ── Menu display ─────────────────────────────────────────────
    private void printMenu() throws FileOperationException {
        separator('═', 54);
        System.out.println("   STUDENT RECORD MANAGER");
        separator('═', 54);
        System.out.println("  1. Add Student");
        System.out.println("  2. View All Students");
        System.out.println("  3. View Student by ID");
        System.out.println("  4. Update Student");
        System.out.println("  5. Delete Student");
        System.out.println("  6. Search Students");
        System.out.println("  0. Exit");
        separator('-', 54);
        System.out.println("  Total records: " + service.totalStudents());
        separator('-', 54);
        System.out.print("  Select: ");
    }

    // ── ADD ──────────────────────────────────────────────────────
    private void menuAdd() throws FileOperationException {
        header("ADD NEW STUDENT");
        try {
            String id     = prompt("Student ID (e.g. CS2024)", true);
            String name   = prompt("Full Name", true);
            int    age    = promptInt("Age");
            String course = prompt("Course / Branch", true);
            String grade  = promptOpt("Grade (A+/A/.../F or N/A)", "N/A");
            String email  = promptOpt("Email", "");

            Student s = service.addStudent(id, name, age, course, grade, email);
            System.out.println("\n  [✓] Student '" + s.getName() + "' added!\n");
            System.out.println(s);
        } catch (InvalidDataException | DuplicateStudentException e) {
            System.out.println("  [✗] " + e.getMessage());
        }
        pause();
    }

    // ── VIEW ALL ─────────────────────────────────────────────────
    private void menuViewAll() throws FileOperationException {
        header("ALL STUDENTS");
        List<Student> list = service.getAllStudents();
        if (list.isEmpty()) {
            System.out.println("  No records found.");
        } else {
            for (int i = 0; i < list.size(); i++) {
                System.out.println("\n  [" + (i + 1) + "] " + "-".repeat(40));
                System.out.println(list.get(i));
            }
        }
        System.out.println("\n  Total: " + list.size() + " student(s)");
        pause();
    }

    // ── VIEW ONE ─────────────────────────────────────────────────
    private void menuViewOne() throws FileOperationException {
        header("VIEW STUDENT");
        try {
            String id = prompt("Enter Student ID", true);
            System.out.println("\n" + service.getStudent(id));
        } catch (StudentNotFoundException e) {
            System.out.println("  [✗] " + e.getMessage());
        }
        pause();
    }

    // ── UPDATE ───────────────────────────────────────────────────
    private void menuUpdate() throws FileOperationException {
        header("UPDATE STUDENT");
        try {
            String id = prompt("Enter Student ID to update", true);
            System.out.println("\n  Current record:\n" + service.getStudent(id) + "\n");
            System.out.println("  (Leave blank to keep current value)");

            String name   = promptOpt("New Name", null);
            Integer age   = promptIntOpt("New Age");
            String course = promptOpt("New Course", null);
            String grade  = promptOpt("New Grade", null);
            String email  = promptOpt("New Email", null);

            Student updated = service.updateStudent(id, name, age, course, grade, email);
            System.out.println("\n  [✓] Record updated.\n" + updated);
        } catch (StudentNotFoundException | InvalidDataException e) {
            System.out.println("  [✗] " + e.getMessage());
        }
        pause();
    }

    // ── DELETE ───────────────────────────────────────────────────
    private void menuDelete() throws FileOperationException {
        header("DELETE STUDENT");
        try {
            String id = prompt("Enter Student ID to delete", true);
            System.out.println("\n" + service.getStudent(id) + "\n");
            System.out.print("  Confirm delete? (yes/no): ");
            if ("yes".equalsIgnoreCase(scanner.nextLine().trim())) {
                service.deleteStudent(id);
                System.out.println("  [✓] Record deleted.");
            } else {
                System.out.println("  [!] Cancelled.");
            }
        } catch (StudentNotFoundException e) {
            System.out.println("  [✗] " + e.getMessage());
        }
        pause();
    }

    // ── SEARCH ───────────────────────────────────────────────────
    private void menuSearch() throws FileOperationException {
        header("SEARCH STUDENTS");
        String kw = prompt("Enter keyword (ID / name / course)", true);
        List<Student> results = service.searchStudents(kw);
        if (results.isEmpty()) {
            System.out.println("  No matching records.");
        } else {
            for (int i = 0; i < results.size(); i++) {
                System.out.println("\n  [" + (i + 1) + "] " + "-".repeat(40));
                System.out.println(results.get(i));
            }
        }
        System.out.println("\n  " + results.size() + " result(s) found.");
        pause();
    }

    // ── Helpers ──────────────────────────────────────────────────
    private String prompt(String label, boolean required) {
        while (true) {
            System.out.print("  " + label + ": ");
            String v = scanner.nextLine().trim();
            if (!v.isEmpty() || !required) return v;
            System.out.println("  [!] This field is required.");
        }
    }

    private String promptOpt(String label, String defaultValue) {
        System.out.print("  " + label + ": ");
        String v = scanner.nextLine().trim();
        return v.isEmpty() ? defaultValue : v;
    }

    private int promptInt(String label) {
        while (true) {
            System.out.print("  " + label + ": ");
            try { return Integer.parseInt(scanner.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("  [!] Enter a valid integer."); }
        }
    }

    private Integer promptIntOpt(String label) {
        System.out.print("  " + label + " (blank to skip): ");
        String v = scanner.nextLine().trim();
        if (v.isEmpty()) return null;
        try { return Integer.parseInt(v); }
        catch (NumberFormatException e) { System.out.println("  [!] Invalid number, skipping."); return null; }
    }

    private void header(String title) {
        System.out.println();
        separator('═', 54);
        System.out.println("   " + title);
        separator('═', 54);
    }

    private void separator(char ch, int w) {
        System.out.println(String.valueOf(ch).repeat(w));
    }

    private void pause() {
        System.out.print("\n  Press Enter to continue...");
        scanner.nextLine();
    }
}
