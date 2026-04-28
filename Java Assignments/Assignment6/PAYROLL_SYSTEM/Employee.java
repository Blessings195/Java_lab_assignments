package PAYROLL_SYSTEM;
import java.time.LocalDate;
public abstract class Employee {
    private String name;
    private String panNo;
    private LocalDate joiningDate;
    private String designation;
    private String empID;
    private String department;
    private String empType;

    public Employee(String name, String panNo, LocalDate joiningDate, String designation, String empID, String department, String empType){
        this.name = name;
        this.panNo = panNo;
        this.joiningDate = joiningDate;
        this.designation = designation;
        this.empID = empID;
        this.department = department;

    }

    //getters
    String getName(){
        return name;
    }
    LocalDate getDate(){
        return joiningDate;
    }
    String getDesig(){
        return designation;
    }
    String getID(){
        return empID;
    }
    String getPan(){
        return panNo;
    }

    String getDept(){
        return department;
    }
    String getType(){
        return empType;
    }

    //calculate total cost to company
    abstract double calcCTC();

    // Tax deduction
    public double calculateTax() {
        double gross = calcCTC();
        if (gross <= 20_000)  return gross * 0.05;
        if (gross <= 50_000)  return gross * 0.10;
        if (gross <= 100_000) return gross * 0.15;
        return gross * 0.20;
    }
    //Net Salary
    public double calculateNetSalary() {
        return calcCTC() - calculateTax();
    }

    //PaySlip
    public void displayPaySlip() {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.printf ("║  PAY SLIP %-32s║%n", "");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf ("║  Employee ID : %-27s║%n", this.getID());
        System.out.printf ("║  Name        : %-27s║%n", this.getName());
        System.out.printf ("║  Department  : %-27s║%n", this.getDept());
        System.out.printf ("║  Type        : %-27s║%n", this.getType());
        System.out.printf ("║  Designation : %-27s║%n", this.getDesig());
        System.out.printf ("║  Joining Date : %-27s║%n", this.getDate());
        System.out.println("╠══════════════════════════════════════════╣");
        printAdditionalDetails();
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf ("║  Gross Salary: ₹%-25.2f║%n", calcCTC());
        System.out.printf ("║  Tax         : ₹%-25.2f║%n", calculateTax());
        System.out.printf ("║  Net Salary  : ₹%-25.2f║%n", calculateNetSalary());
        System.out.println("╚══════════════════════════════════════════╝");
        System.out.println();
    }
    //Additional details
    protected abstract void printAdditionalDetails();
}

