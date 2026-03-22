import java.util.ArrayList;
public class Bank {
    private ArrayList<Customer> customers;
    public Bank() {
        customers = new ArrayList<>();
    }

    public void addCustomer(Customer c) {
        customers.add(c);
    }

    public ArrayList<Customer> getCustomers(){
    return customers;
    }

}
