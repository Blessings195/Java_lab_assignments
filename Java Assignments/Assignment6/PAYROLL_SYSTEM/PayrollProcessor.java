package PAYROLL_SYSTEM;

import java.util.ArrayList;
import java.util.List;

public class PayrollProcessor {
        private List<Employee> employees = new ArrayList<>();

    public void addEmployee(Employee e) { 
        employees.add(e); 
        System.out.println("New employee added successfully!");
        System.out.println("\n");
    }

    public void processAllPayrolls() {
        if(employees.isEmpty()){
            System.out.println("No employees added yet.");
            return;
        }
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║          MONTHLY PAYROLL REPORT          ║");
        System.out.println("╚══════════════════════════════════════════╝\n");

        for (Employee e : employees) {
            e.displayPaySlip();
        }

        printSummary();
    }
    public List<Employee> getEmployees(){
        return employees;
    }

    private void printSummary() {
        double totalGross = 0, totalTax = 0, totalNet = 0;
        for (Employee e : employees) {
            totalGross += e.calcCTC();
            totalTax   += e.calculateTax();
            totalNet   += e.calculateNetSalary();
        }

        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║           PAYROLL SUMMARY                ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf ("║  Total Employees : %-23d║%n", employees.size());
        System.out.printf ("║  Total Gross Pay : %-22.2f║%n", totalGross);
        System.out.printf ("║  Total Tax       : %-22.2f║%n", totalTax);
        System.out.printf ("║  Total Net Pay   : %-22.2f║%n", totalNet);
        System.out.println("╚══════════════════════════════════════════╝");
    }
}
