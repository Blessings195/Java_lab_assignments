
import java.time.LocalDate;
import java.util.ArrayList;

public class Customer {
    private String name;
    private LocalDate dob;
    private String category;
    private String address;
    private String occupation;
    private String customerId;
    private String passportNumber;
    private static int counter = 100;
    private static String prefix = "C";
    //private String accountType;
    private ArrayList<Account> accounts;
    private Long phoneNumber;

    //Constructors
    public Customer (String name, LocalDate dob, String category, String address,
                    String occupation, String passportNumber, Long number) {
                        this.name = name;
                        this.dob = dob;
                        this.category = category;
                        this.address = address;
                        this.customerId = generateCustID(prefix);
                        this.occupation = occupation;
                        this.passportNumber = passportNumber;
                        this.phoneNumber = number;
                        this.accounts = new ArrayList<>();
                    }

    public Customer(String name, String passportNumber, String occupation, Long number, LocalDate dob,
        String category, ArrayList<Account> accounts){
        this.name = name;
        this.occupation = occupation;
        this.dob = dob;
        this.customerId = generateCustID(prefix);
        this.category = category;
        this.passportNumber = passportNumber;
        this.phoneNumber = number;
        this.accounts = new ArrayList<>();
    }

    //Methods to access private members
    //DOB
    void setDob(LocalDate DOB){
        dob = DOB;
    }
    LocalDate getDob(){
        return dob;
    }
    //Name
    void setName(String n){
        name = n;
    }
    String getName(){
        return name;
    }
    //Category
    void setCategory(String c){
        category = c;
    }
    String getCategory(){
        return category;
    }
    //Customer ID
    void setId(String id){
        customerId = id;
    }
    String getId(){
        return customerId;
    }
    //Address
    /*void setAddress(String d){
        address = d;
    }*/
    String getAddress(){
        return address;
    }
    //Occupation
    /*void setOccupation(String occupation){
        this.occupation = occupation;
    }*/
    String getOccupation(){
        return occupation;
    }
    public void addAccount(Account acc) {
        accounts.add(acc);
    }
    //passort number
    public void setPassport(String p){
        passportNumber = p;
    }
    String getPassport(){
        return passportNumber;
    }

    public ArrayList<Account> getAccounts(){
    return accounts;
    }
    public static String generateCustID(String prefix){
        counter++;
        return prefix + counter;
    }
    //Display the customer details
    void displayCustomer(Customer c){ 
        System.out.println("Your details: ");
        System.out.println("\nName: " + c.getName());
        System.out.println("\nCustomerID: " + c.getId());
        System.out.println("\nCategory: " + c.getCategory());
        System.out.println("\nOccupation: " + c.getOccupation());
        System.out.println("\nPassport Number: " + c.getPassport());
        System.out.println("\nAddress: " + c.getAddress());
        accounts.forEach(a-> System.out.println("\nAcounts: " + a.getAccountType() + " | Account number: " + a.getAccNumber()+"\n"));
        System.out.println("\n");

    }
}
