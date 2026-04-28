package PAYROLL_SYSTEM;

import java.time.LocalDate;

public class ContractEmployee extends Employee{
    private double projectFee;
    private int contractMonths;
    private double completionBonus;

    public ContractEmployee(String name, String panNo, LocalDate joiningDate, String designation, String empID, String department, String empType,
        double projectFee, double completionBonus, int contractMonths
    ){
        super(name, panNo, joiningDate, designation, empID, department, empType);
        this.projectFee = projectFee;
        this.completionBonus = completionBonus;
        this.contractMonths = contractMonths;
    }

    double getFee(){
        return projectFee;
    }
    int getMonths(){
        return contractMonths;
    }
    double getBonus(){
        return completionBonus;
    }
    //Monthly Pay
    public double getMonthlyFee() { return projectFee / contractMonths; }

    @Override
    double calcCTC(){
        return getMonthlyFee() + completionBonus;
    }
    @Override
    protected void printAdditionalDetails() {
        System.out.printf("║  Project Fee : ₹%-25.2f║%n", this.getFee());
        System.out.printf("║  Contract Mth: %d-27d║%n", this.getMonths());
        System.out.printf("║  Monthly Fee : ₹%-25.2f║%n", getMonthlyFee());
        System.out.printf("║  Comp. Bonus : ₹%-25.2f║%n", this.getBonus());
    }
}
