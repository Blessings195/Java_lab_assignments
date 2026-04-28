package PAYROLL_SYSTEM;

import java.time.LocalDate;


public class FullTimeEmp extends Employee {
    private double basicSalary;
    private final double houseRentAllowance;
    private final double healthInsurance;
    private final double da; //dearness allowance

    public FullTimeEmp(String name, String panNo, LocalDate joiningDate, String designation, String empID, String department, String type,
        double basicSalary, double houseRentAllowance, double healthInsurance, double da){
        super(name, panNo, joiningDate, designation, empID, department, type);
        this.basicSalary = basicSalary;
        this.houseRentAllowance = houseRentAllowance;
        this.healthInsurance = healthInsurance;
        this.da = da;


    }

    double getSalary(){
        return basicSalary;
    }
    double gethra(){
        return houseRentAllowance;
    }
    double getHi(){
        return healthInsurance;
    }
    double getDa(){
        return da;
    }

    //CTC
    @Override
    double calcCTC(){
        return basicSalary + houseRentAllowance + healthInsurance + da;
    }
    //Additional Details
    @Override
    protected void printAdditionalDetails() {
        System.out.printf("║  Basic Salary: %-25.2f║%n", this.getSalary());
        System.out.printf("║  HRA         : %-25.2f║%n", this.gethra());
        System.out.printf("║  DA          : %-25.2f║%n", this.getDa());
        System.out.printf("║  Health Insurance : %-25.2f║%n", this.getHi());
    }
    
}
