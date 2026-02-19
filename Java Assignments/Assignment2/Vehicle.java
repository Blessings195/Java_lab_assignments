//import java.util.Date;

public class Vehicle {
    public String brandName;
    public String modelName;
    public java.time.Year yearOfMfg;
    public String color;
    public char fuelType; //E = electric, C = CNG, D = diesel, P = petrol
    public float mileage;
    public double price;
    public int seats;
    private String mfgCode;
    private int noOfServices;


    //constructor (default)
    public Vehicle(){
        brandName = "Toyota";
        modelName = "Camry";
        yearOfMfg = java.time.Year.of(2020);
        color = "Blue";
        fuelType = 'P';
        seats = 5;
        price = 2500000.99;
    }
    //Other constructors
    public Vehicle(String brandName, String modelName, double price, String color){
        this.brandName = brandName;
        this.modelName = modelName;;
        this.price = price;
        this.color = color;
    }

    public Vehicle(char fuelType, double price, String mfgCode, String modelName) {
        this.fuelType = fuelType;
        this.price = price;
        this.mfgCode = mfgCode;
        this.modelName = modelName;
    }

    //methods

    public void start() {
        System.out.println("Start ignition by pressing the button.");
        System.out.println("Your initial speed is 10 kmph");
    };
    public void stop(){
        System.out.println("Press the button to stop.");
        System.out.println("Your vehicle has stopped.");
    }
    public void drive() {
        System.out.println("Step on the gas pedal and hit the road!");
    }
    public int accelerate(int initSp) {
        return initSp += 20;
    }
    public float calcMileage(char fuelT) {

        switch(fuelT){
            case 'P':
                return 18.0f;
            case 'E':
                return 0.0f;
            case 'D':
                return 23.0f;
            default:
                System.out.println("Not applicable.");
        }
        return 0;

    }

    //getter and setters
    public void setMfgCode(String mCode){
        mfgCode = mCode;
    }
    public String getMfgCode() {
        return mfgCode;
    }
    public void setNoOfServices(int services){
        noOfServices = services;
    }
    public int getNoOfServices(){
        return noOfServices;
    }


    public static void printVehicleDetails(Vehicle newV){
        System.out.println("================================");
        System.out.println("Vehicle details: ");
        System.out.println("Brand name: " + newV.brandName);
        System.out.println("Model name: " + newV.modelName);
        System.out.println("Year of Manufacture: " + newV.yearOfMfg);
        System.out.println("Color: " + newV.color);
        System.out.println("Fuel type: " + newV.fuelType);
        System.out.println("Manufacturing code: " + newV.mfgCode);

        System.out.println("=================================");

    }



}
