import java.time.LocalDate;
public class InvestmentAccount extends Account{
    private double returnRate = 0.10;
    public InvestmentAccount(LocalDate date, double balance, String accountType, int pin, Customer owner){
        super(date, "IA", balance, accountType, pin, owner);
    }

    public double getRate(){
        return returnRate;
    }
    @Override
    public void deposit(double amount) throws InvalidAmountException{
        super.deposit(amount);
        System.out.println("Successfully invested: " + amount);
    }
    public double applyReturn(double rate) throws InsufficientBalanceException{
        if(balance <= 0){
            throw new InsufficientBalanceException("Insufficient Balance.");
        }
        double profit = balance * returnRate;
        balance += profit;
        return balance;
    }  
}
