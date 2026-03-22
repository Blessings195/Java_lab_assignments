import java.time.LocalDate;
import java.time.LocalDateTime;
public class LoanAccount extends Account {
    public LoanAccount(LocalDate createdOn, double loanAmount, String accountType, int pin,  Customer owner){
        super(createdOn, "LA", loanAmount, accountType, pin, owner);
    }
    
    //Pay EMI
    public double payEMI(double amount) throws InvalidAmountException{
        if(amount<= 0){
            throw new InvalidAmountException("EMI amount must be greater than 0.");
        }
        balance -= amount;
        System.out.println("You have paid: " + amount);
        addTransaction(LocalDateTime.now() + "EMI paid: " + amount);
        return balance;
    }
    @Override
    public void withdraw(double ammount){
        System.out.println("You cannot withdraw from a loan account.");
    }
}
