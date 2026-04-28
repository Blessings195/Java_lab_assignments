package PAYROLL_SYSTEM;

import java.time.LocalDate;
import java.util.Scanner;

public class PayrollSystemMain {

        public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        PayrollProcessor processor = new PayrollProcessor();
        int choice;

        do{
            System.out.println("======== EMPLOYEE PAYROLL SYSTEM MENU ========");
            System.out.println("1. Add new full-time employee");
            System.out.println("2. Add Part-time employee");
            System.out.println("3. Add new Manager ");
            System.out.println("4. Add new Contract employee");
            System.out.println("5. View employee details and salary");
            System.out.println("6. View Monthly report");
            System.out.println("7. Exit");
            System.out.println("Enter your choice: ");

            choice = scan.nextInt();

            switch(choice){
                case 1:
                    System.out.println("Enter employee ID: ");
                    scan.nextLine();
                    String id1 = scan.nextLine();

                    System.out.println("Enter name: ");
                    String n = scan.nextLine();

                    System.out.println("Enter the basic salary: ");
                    double basic = scan.nextDouble();
                    scan.nextLine();

                    System.out.println("Enter House Rent Insurance amount: ");
                    double hra = scan.nextDouble();
                    scan.nextLine();

                    System.out.println("Enter Health Insurance amount: ");
                    double hi = scan.nextDouble();
                    scan.nextLine();

                    System.out.println("Enter Dearness allowance amount: ");
                    double da = scan.nextDouble();
                    scan.nextLine();

                    System.out.println("Enter PAN number: ");
                    String pan = scan.nextLine();

                    LocalDate joining = LocalDate.now();

                    System.out.println("Enter designation: ");
                    String desig = scan.nextLine();

                    System.out.println("Enter department: ");
                    String dept = scan.nextLine();

                    String type = "Full-time Employee";

                    FullTimeEmp f1 = new FullTimeEmp(n, pan, joining, desig, id1, dept, type, basic, hra, hi, da);
                    processor.addEmployee(f1);
                    break;

                case 2:
                    System.out.println("Enter employee ID: ");
                    scan.nextLine();
                    String id2 = scan.nextLine();

                    System.out.println("Enter name: ");
                    String name = scan.nextLine();

                    System.out.println("Enter PAN number: ");
                    String pan2 = scan.nextLine();

                    System.out.println("Enter designation: ");
                    scan.nextLine();
                    String desig2 = scan.nextLine();


                    System.out.println("Enter department: ");
                    String dept1 = scan.nextLine();
                    scan.nextLine();

                    String type2 = "Part-time Employee";

                    LocalDate join = LocalDate.now();

                    System.out.println("Enter hours worked: ");
                    int hours = scan.nextInt();
                    scan.nextLine();

                    System.out.println("Enter rate per hour: ");
                    double rate = scan.nextDouble();
                    scan.nextLine();

                    PartTimeEmp p1 = new PartTimeEmp(name, pan2, join, desig2, id2, dept1, type2, rate, hours);
                    processor.addEmployee(p1);
                    break;

                case 3:
                    System.out.println("Enter employee ID: ");
                    scan.nextLine();
                    String id3 = scan.nextLine();

                    System.out.println("Enter name: ");
                    String name2 = scan.nextLine();

                    System.out.println("Enter the basic salary: ");
                    double basic2 = scan.nextDouble();
                    scan.nextLine();

                    System.out.println("Enter PAN number: ");
                    String pan3 = scan.nextLine();

                    System.out.println("Enter designation: ");
                    String desig3 = scan.nextLine();

                    System.out.println("Enter department: ");
                    String dept2 = scan.nextLine();

                    String type3 = "Manager";

                    System.out.println("Enter House Rent Insurance amount: ");
                    double hra2 = scan.nextDouble();
                    scan.nextLine();

                    System.out.println("Enter Health Insurance amount: ");
                    double hi2 = scan.nextDouble();
                    scan.nextLine();

                    System.out.println("Enter Dearness allowance amount: ");
                    double da2 = scan.nextDouble();

                    System.out.println("Enter team size: ");
                    int team = scan.nextInt();
                    scan.nextLine();

                    System.out.println("Enter performance bonus: ");
                    double bonus = scan.nextDouble();

                    System.out.println("Enter Education allowance: ");
                    double edu = scan.nextDouble();
                    scan.nextLine();

                    LocalDate joinDate = LocalDate.now();

                    Manager m1 = new Manager(name2, pan3, joinDate, desig3, id3, dept2, type3, basic2, hra2, hi2, da2, bonus, team, edu);

                    processor.addEmployee(m1);
                    break;
                
                case 4:
                    System.out.println("Enter employee ID: ");
                    String id4 = scan.nextLine();

                    System.out.println("Enter name: ");
                    String name3 = scan.nextLine();

                    System.out.println("Enter PAN number: ");
                    String pan4 = scan.nextLine();

                    System.out.println("Enter department: ");
                    String dept3 = scan.nextLine();

                    System.out.println("Enter designation: ");
                    String desig4 = scan.nextLine();

                    String type4 = "Contract employee";
                    LocalDate date = LocalDate.now();

                    System.out.println("Enter project fee: ");
                    double fee = scan.nextDouble();
                    scan.nextLine();

                    System.out.println("Enter the contract months: ");
                    int months = scan.nextInt();
                    scan.nextLine();

                    System.out.println("Enter completion bonus: ");
                    double compBonus = scan.nextDouble();
                    scan.nextLine();

                    ContractEmployee c1 = new ContractEmployee(name3, pan4, date, desig4, id4, dept3, type4, fee, compBonus, months);
                    processor.addEmployee(c1);
                    break;

                case 5:
                    //Employee[] allEmployees = {f1, p1, m1, c1 };
                    //List employees = processor.getEmps();
                    for(Employee e: processor.getEmployees()){
                        e.displayPaySlip();
                        System.out.println("-----------------"); 
                    }
                    break;

                case 6:
                    processor.processAllPayrolls();
                    break;

                case 7:
                    System.out.println("Exiting program...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again");
                    break;


            }
        }while(choice !=7);

    }
    
}
