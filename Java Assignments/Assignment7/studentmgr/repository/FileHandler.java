    package com.studentmgr.repository;

    import com.studentmgr.exception.FileOperationException;
    import com.studentmgr.exception.InvalidDataException;
    import com.studentmgr.model.Student;
    import java.io.*;
    import java.util.LinkedHashMap;
    import java.util.Map;

    /**
     * FileHandler - Abstraction layer for all file I/O.
     *
     * Demonstrates ABSTRACTION: hides the details of reading/writing a
     * CSV file. The rest of the application only sees load() and save().
     *
     * Each line in students.csv stores one student in standard CSV format:
     *   studentId,"name",age,"course",grade,"email","createdAt"
     *
     * The first line is always the header row:
     *   studentId,name,age,course,grade,email,createdAt
     */
    public class FileHandler {

        private static final String CSV_HEADER = "studentId,name,age,course,grade,email,createdAt";

        private final String filePath;

        public FileHandler(String filePath) {
            this.filePath = filePath;
        }

        /**
         * Load all student records from the CSV file.
         * Skips the header row and any blank lines.
         *
         * @return Map of studentId -> Student
         */
        public Map<String, Student> load() throws FileOperationException {
            Map<String, Student> records = new LinkedHashMap<>();
            File file = new File(filePath);

            if (!file.exists()) return records;   // first run — no file yet

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                int lineNo = 0;
                while ((line = br.readLine()) != null) {
                    lineNo++;
                    line = line.trim();
                    if (line.isEmpty()) continue;
                    if (lineNo == 1 && line.startsWith("studentId")) continue; // skip header
                    try {
                        Student s = Student.fromCsvLine(line);
                        records.put(s.getStudentId(), s);
                    } catch (InvalidDataException | NumberFormatException e) {
                        throw new FileOperationException(
                            "Corrupted data at line " + lineNo + ": " + e.getMessage(), e);
                    }
                }
            } catch (IOException e) {
                throw new FileOperationException(
                    "Cannot read file '" + filePath + "': " + e.getMessage(), e);
            }
            return records;
        }

        /**
         * Persist all student records to the CSV file.
         * Always writes the header row first, then one student per line.
         *
         * @param records Map of studentId -> Student to write
         */
        public void save(Map<String, Student> records) throws FileOperationException {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                bw.write(CSV_HEADER);
                bw.newLine();
                for (Student s : records.values()) {
                    bw.write(s.toCsvLine());
                    bw.newLine();
                }
            } catch (IOException e) {
                throw new FileOperationException(
                    "Cannot write to file '" + filePath + "': " + e.getMessage(), e);
            }
        }
    }
