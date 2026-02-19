public class MainForVehicle {
    public static void main(String[] args) {
        Vehicle v = new Vehicle(); //Vehicle() is a special constructor method
        v.mileage = v.calcMileage(v.fuelType);
        v.setMfgCode("KA05EF9101");
        Vehicle v1 = new Vehicle("Toyota", "Nexon EV", 2500000.99, "Red");
        v1.setMfgCode("TOY234501");
        v1.seats = 5;
        v1.fuelType = 'P';
        v1.mileage = v1.calcMileage(v1.fuelType);
        Vehicle v2 = new Vehicle('D', 2400000.59, "MAH00123","Honda City" );
        v2.mileage = v2.calcMileage(v2.fuelType);
        v2.brandName = "Toyota";

        Vehicle[] fleet = new Vehicle[]{v, v1, v2};

        printTabHeader();

        for (Vehicle V : fleet){
            displayRow(V);
            //int newSpeed = V.accelerate(20);
            //System.out.println("New speed: " + newSpeed + " kmph");
            System.out.println("------------------------------------------------------------");
        }
        System.out.println("\n============================================================");

       for (Vehicle newV : fleet) {
        printVehicleDetails(newV);
       }
       
       for (Vehicle vehicle: fleet){
        System.out.println("\n");
        vehicle.start();
        vehicle.stop();
        vehicle.drive();
       }
    }

    public static void printTabHeader() {
        System.out.println("Vehicle Summary Report: ");
        //System.out.println("\n");
        System.out.println("\n============================================================");
        System.out.printf(
            "%-15s %-15s %-10s %-10s%n",
            "\nMfgCode ", "Model", "Fuel", "Mileage"
        );
        System.out.println("============================================================");
    }
    public static void displayRow(Vehicle v) {
        System.out.printf(
            "%-15s %-15s %-10s %-10.2f%n",
            v.getMfgCode(), v.modelName, v.fuelType, v.mileage
        );
    }

        public static void printVehicleDetails(Vehicle newV){
        System.out.println("\n");
        System.out.println("================================");
        System.out.println("Vehicle details: ");
        System.out.println("Brand name: " + newV.brandName);
        System.out.println("Model name: " + newV.modelName);
        System.out.println("Year of Manufacture: " + newV.yearOfMfg);
        System.out.println("Color: " + newV.color);
        System.out.println("Fuel type: " + newV.fuelType);
        System.out.println("Manufacturing code: " + newV.getMfgCode());

        System.out.println("=================================");

    }
}
