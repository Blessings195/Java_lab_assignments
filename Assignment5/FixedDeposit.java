import java.time.LocalDate;
public class FixedDeposit extends Account{
    public FixedDeposit(LocalDate date, double balance, String accountType, int pin, Customer owner){
        super(date, "FD", balance, accountType, pin, owner);
    }

    @Override
    public void withdraw(double amount) throws InsufficientBalanceException{
        throw new InsufficientBalanceException("Cannot withdraw before maturity period");
    }
}
