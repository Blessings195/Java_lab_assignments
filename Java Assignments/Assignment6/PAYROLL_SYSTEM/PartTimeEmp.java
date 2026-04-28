package PAYROLL_SYSTEM;

import java.time.LocalDate;

public class PartTimeEmp extends Employee {
    
    private double hourlyRate;
    private int hoursWorked;

    /*String getTempID(){
    return tempID;
    }*/
    public PartTimeEmp(String name, String panNo, LocalDate joiningDate, String designation, String tempID, String department, String empType,
        double hourlyRate, int hoursWorked
    ){
        super(name, panNo, joiningDate, designation, tempID, department, empType);
        //this.tempID = this.getTempID();
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    double getRate(){
        return hourlyRate;
    }
    int getHours(){
        return hoursWorked;
    }
    @Override
    double calcCTC(){
        return hourlyRate * hoursWorked;
    }

    @Override
    protected void printAdditionalDetails(){
        System.out.printf("║  Hourly Rate : %-25.2f║%n", this.getRate());
        System.out.printf("║  Hours Worked: %-27d║%n", this.getHours());
    }
}
