import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
public class Account {
    protected String accountNumber;
    protected double balance;
    protected Customer owner;
    protected LocalDate createdOn;
    protected String accountType;
    protected int pin;
    protected static int counter = 1000;
    protected ArrayList<String> transactionHistory;

    public Account(LocalDate createdOn, String prefix, double balance, String accountType, int pin, Customer owner){
        this.accountNumber = generateAccNumber(prefix);
        this.balance = balance;
        this.owner = owner;
        this.accountType = accountType;
        this.pin = pin;
        this.createdOn = createdOn;
        this.transactionHistory = new ArrayList<>();
    }
    public void setAccNumber(String number){
        this.accountNumber = number;
    }
    public String getAccNumber(){
        return accountNumber;
    }
    public double getBalance(){
        return balance;
    }
    public String getAccountType(){
        return accountType;
    }
     public void setPin(int p){
        pin = p;
    }
    public int getPin(){
        return pin;
    }
    //Account methods

    //Deposit
    public void deposit(double amount) throws InvalidAmountException{
        if(amount<=0){
            throw new InvalidAmountException("Deposit amount must be positive.");
        }
        balance += amount;
        System.out.println("Deposited" + " " + amount + " " + "successfully.");
        addTransaction(LocalDateTime.now() + "- Deposited: " + amount);
    }
    //Withdraw
    public void withdraw(double amount) throws InsufficientBalanceException, InvalidAmountException{
        if(amount<=0){
            throw new InvalidAmountException("Amount must be positive.");
        }
        else if (amount> balance){
            throw new InsufficientBalanceException("Insufficient balance.");
        }
        balance -= amount;
        System.out.println("You have successfully withdrawn "+ amount);
        addTransaction(LocalDateTime.now() + " - Withdrawn: " + amount);
    }
    //overloaded method (for loan account)
    //displaing the balance
    public void displayBalance() {
        System.out.println("Balance: " + balance);
    }

    public int changePin(int newPin){
        pin = newPin;
        System.out.println("PIN updated successully.");
        return pin;
    }

    //Account number generation
    public static String generateAccNumber(String prefix){
        counter++;
        return prefix + counter;
    }
    //Transcation history
    //add transaction
    public void addTransaction(String message){
        transactionHistory.add(message);
    }

    //display
    public void showTransactionHistory(){

        if(transactionHistory.isEmpty()){
            System.out.println("No transactions yet.");
            return;
        }

         System.out.println("Transaction History:");

        for(String t : transactionHistory){
            System.out.println(t);
        }
    }

}
