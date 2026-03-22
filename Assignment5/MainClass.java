import java.time.LocalDate;
import java.util.Scanner;
public class MainClass {
    public static void main(String[] args){
        Bank bank = new Bank();
        Scanner scan = new Scanner(System.in);
        int choice;
        do{
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("ACCOUNT OPERATIONS");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("1. Open an Account");
            System.out.println("2. Check balance");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Change PIN");
            System.out.println("6. View transcation history");
            System.out.println("7. View your details");
            System.out.println("8. Exit");
            //Bank bank = new Bank();
            System.out.println("Enter your choice: ");
            choice = scan.nextInt();
            scan.nextLine();

            switch(choice) {
                case 1:
                        System.out.println("Enter the following details: ");

                        System.out.println("\nName: ");
                        String n = scan.nextLine();

                        System.out.println("\nDate of birth (yyyy-mm-dd): ");
                        LocalDate date = LocalDate.parse(scan.nextLine());

                        System.out.println("\nPassport number: ");
                        String pass = scan.nextLine();

                        System.out.println("\nOccupation: ");
                        String occ = scan.nextLine();

                        System.out.println("\nPhone number: ");
                        Long num = scan.nextLong();
                        scan.nextLine();

                        System.out.println("\nAddress: ");
                        String add = scan.nextLine();

                        Customer c1 = new Customer(n, date, "Regular", add, occ, pass, num);
                        System.out.println("\nAccount type (Savings/Loan/FD/Investment):");
                        String type = scan.nextLine();
                        if (type.equalsIgnoreCase("Savings")){
                            System.out.println("\nSet your PIN: ");
                            int pin = scan.nextInt();
                            scan.nextLine();
                            Savings s1 = new Savings(LocalDate.now(),25.0, "Savings Account", pin, c1);
                            c1.addAccount(s1);
                        }else if (type.equalsIgnoreCase("Loan")){
                            System.out.println("\nEnter loan amount: ");
                            double loan = scan.nextDouble();
                            scan.nextLine();
                            //PIN
                            System.out.println("\nSet your PIN: ");
                            int pin = scan.nextInt();
                            scan.nextLine();
                            LoanAccount l1 = new LoanAccount(LocalDate.now(), loan, "Loan Account", pin, c1);
                            c1.addAccount(l1);
                        }else if(type.equalsIgnoreCase("FD")){
                            System.out.println("\nSet your PIN: ");
                            int pin = scan.nextInt();
                            scan.nextLine();
                            FixedDeposit f1 = new FixedDeposit(LocalDate.now(), 25.0, "Fixed Deposit Account", pin, c1);
                            c1.addAccount(f1);
                        }else{
                            System.out.println("\nSet your PIN: ");
                            int pin = scan.nextInt();
                            scan.nextLine();
                            InvestmentAccount v1 = new InvestmentAccount(LocalDate.now(), 25.0, "Investment Account", pin, c1);
                            c1.addAccount(v1);
                        }
                        System.out.println("Account created successfully.");
                        bank.addCustomer(c1);

                        c1.displayCustomer(c1);
                        break;
                case 2:
                        System.out.println("Enter account number: ");
                        String accNumber = scan.nextLine();

                        boolean found = false;
                        for(Customer c: bank.getCustomers()){
                            for(Account acc: c.getAccounts()){
                                if(acc.getAccNumber().equals(accNumber)){
                                    System.out.println("Balance: " + acc.getBalance());
                                    found = true;
                                    break;
                                }
                            }
                            if(found) break;
                        }
                        if(!found){
                            System.out.println("Account not found. Please try again.");
                        }
                        break;

                case 3:
                    System.out.println("Enter account number to deposit in: ");
                    String accNum = scan.nextLine();

                    boolean flag = false;
                        for(Customer c: bank.getCustomers()){
                            for(Account acc: c.getAccounts()){
                                if(acc.getAccNumber().equals(accNum)){
                                    flag = true;
                                    try {
                                        System.out.println("Enter deposit amount: ");
                                        double amount = scan.nextDouble();
                                        scan.nextLine();
                                        if(acc instanceof LoanAccount){
                                            scan.nextLine();
                                            ((LoanAccount) acc).payEMI(amount);
                                            acc.displayBalance();
                                        }
                                        else if(acc instanceof Savings){
                                            ((Savings) acc).deposit(amount);
                                            acc.displayBalance();
                                        }
                                        else if(acc instanceof InvestmentAccount){
                                            ((InvestmentAccount)acc).deposit(amount);
                                            ((InvestmentAccount) acc).applyReturn(((InvestmentAccount) acc).getRate());
                                            acc.displayBalance();
                                        }
                                    } catch (InvalidAmountException  | InsufficientBalanceException e) {
                                        System.out.println("Error: " + e.getMessage());
                                    }
                                    break;
                                }
                            }
                            if (flag) break;

                        }
                        if (!flag){
                                System.out.println("Account not found.Please try again.");
                        }
                    break;

                case 4:
                    boolean f = false;
                    System.out.println("Enter your account number: ");
                    String numberInput = scan.nextLine();

                    for (Customer c: bank.getCustomers()){
                        for(Account acc: c.getAccounts()){
                            if (acc.getAccNumber().equals(numberInput)){
                                f = true;
                                System.out.println("Enter your PIN: ");
                                int pinInput = scan.nextInt();
                                scan.nextLine();
                                if(acc.getPin() == pinInput){

                                    try {
                                        System.out.println("Enter withdraw amount: ");
                                        double amount = scan.nextDouble();
                                        scan.nextLine();

                                        acc.withdraw(amount);
                                        acc.displayBalance();
                                    } 
                                    catch ( InsufficientBalanceException | InvalidAmountException e) {
                                            System.out.println("Error: " + e.getMessage());
                                    }
                                }

                                else{
                                    System.out.println("Invalid PIN. Please try again. ");
                                }
                                break;
                            }
                        }
                        if(f) break;

                    }

                    if (!f){
                        System.out.println("Invalid account. Please try again");
                    }

                    break;

                case 5:
                    boolean flag1 = false;
                    System.out.println("Enter your account number: ");
                    String numberInput2 = scan.nextLine();

                    for (Customer c: bank.getCustomers()){
                        for(Account acc: c.getAccounts()){
                            if (acc.getAccNumber().equals(numberInput2)){
                                flag1 = true;
                                System.out.println("Enter your old PIN: ");
                                int oldPin = scan.nextInt();
                                scan.nextLine();
                                if(acc.getPin() == oldPin){
                                    try {
                                        System.out.println("Enter your new PIN: ");
                                        int newPin = scan.nextInt();
                                        scan.nextLine();

                                        acc.changePin(newPin);
                                    } 
                                    catch (Exception e) {
                                            System.out.println("Error: " + e.getMessage());
                                    }
                                }
                                else{
                                    System.out.println("Invalid PIN. Please try again. ");
                                }
                                break;
                            }
                        }
                        if(flag1) break;

                    }

                    if (!flag1){
                        System.out.println("Invalid account. Please try again");
                    }

                    break;

                case 6:
                    System.out.println("Enter account number: ");
                    String accNumHist = scan.nextLine();

                    boolean foundHist = false;

                    for(Customer c : bank.getCustomers()){
                        for(Account acc : c.getAccounts()){

                            if(acc.getAccNumber().equals(accNumHist)){

                                acc.showTransactionHistory();
                                foundHist = true;
                                break;
                            }
                        }
                        if(foundHist) break;
                    }

                    if(!foundHist){
                        System.out.println("Account not found. Please try again.");
                    }

                    break;

                case 7:
                    System.out.println("Enter account number: ");
                    String accNumDet = scan.nextLine();

                    boolean foundDet = false;

                    for(Customer c : bank.getCustomers()){
                        for(Account acc : c.getAccounts()){

                            if(acc.getAccNumber().equals(accNumDet)){

                                c.displayCustomer(c);
                                foundDet = true;
                                break;
                            }
                        }
                        if(foundDet) break;
                    }

                    if(!foundDet){
                        System.out.println("Account not found. Please try again.");
                    }

                    break;

                case 8:
                    System.out.println("Exiting program...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;

            }

        }while (choice!= 8);
        
    scan.close();
    }
}
