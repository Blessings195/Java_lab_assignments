package PAYROLL_SYSTEM;

import java.time.LocalDate;

public class Manager extends FullTimeEmp {

    private double perfBonus;
    private int teamSize;
    private double eduAllowance;
    //Constructor
    public Manager(String name, String panNo, LocalDate joiningDate, String designation, String empID, String department, String empType,
        double basicSalary, double houseRentAllowance, double healthInsurance, double da, double perfBonus, 
        int teamSize, double eduAllowance) {
        super(name, panNo, joiningDate, designation, empID, department, empType, basicSalary, houseRentAllowance, healthInsurance, da);
        this.perfBonus = perfBonus;
        this.teamSize = teamSize;
        this.eduAllowance = eduAllowance;
    }

    //teamAllowance (1500 per team member)
    double teamAllowance(){
        return teamSize * 1500.0;
    }
    double getEduAllowance(){
        return eduAllowance;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public double getPerfBonus() {
        return perfBonus;
    }

    @Override
    double calcCTC(){
        return super.calcCTC() + teamAllowance() + this.getEduAllowance() + this.getPerfBonus();
    }
    @Override

    protected void printAdditionalDetails(){
        super.printAdditionalDetails();
        System.out.printf("║  Team Allowance: %-25.2f║%n", this.teamAllowance());
        System.out.printf("║  Edu Allowance: %-25.2f║%n", this.getEduAllowance());
        System.out.printf("║  Team Size: %d-25.2f║%n", this.getTeamSize());
        System.out.printf("║  Performance Bonus: %-25.2f║%n", this.getPerfBonus());
    } 
}
