package com.studentmgr;

import com.studentmgr.exception.FileOperationException;
import com.studentmgr.repository.FileHandler;
import com.studentmgr.repository.StudentRepository;
import com.studentmgr.service.StudentService;
import com.studentmgr.ui.ConsoleUI;

/**
 * Main - Application Entry Point.
 *
 * Wires up the full dependency chain:
 *   FileHandler -> StudentRepository -> StudentService -> ConsoleUI
 *
 * This is the Composition Root of the application.
 */
public class Main {

    public static void main(String[] args) {
        try {
            // Dependency chain (Composition)
            FileHandler       fileHandler = new FileHandler("students.csv");
            StudentRepository repository  = new StudentRepository(fileHandler);
            StudentService    service     = new StudentService(repository);
            ConsoleUI         ui          = new ConsoleUI(service);

            ui.run();

        } catch (FileOperationException e) {
            System.err.println("[FATAL] Could not load data file: " + e.getMessage());
            System.exit(1);
        }
    }
}
