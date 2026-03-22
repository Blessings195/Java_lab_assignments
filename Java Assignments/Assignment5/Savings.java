import java.time.LocalDate;
import java.time.LocalDateTime;
public class Savings extends Account {
    private double interestRate = 0.05;

    public Savings(LocalDate createdOn, double balance, String accountType, int pin, Customer owner){
        super(createdOn, "SA", balance, accountType, pin, owner);
    }

    public double addInterest(){
        /*if(balance <= 0){
            throw new InsufficientBalanceException("Insufficient balance.");
        }*/
        double interest = balance*interestRate;
        balance += interest;
        return balance;
    }

    @Override
    public void deposit(double amount) throws InvalidAmountException{
        if(amount<=0){
            throw new InvalidAmountException("Deposit amount must be positive.");
        }
        balance += amount;
        addInterest();

        System.out.println("Deposited" + " " + amount + " " + "successfully.");
        addTransaction(LocalDateTime.now() + "- Deposited: " + amount);
    }
 
}