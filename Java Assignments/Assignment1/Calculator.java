import java.util.Scanner;
public class Calculator {
    int num1, num2, op;

    public int addNums(int n1, int n2) {
        return n1 + n2;

    }

    public int subtractNums(int n1, int n2) {
        return n1 - n2;

    }

    public long multiplyNums(int n1, int n2) {
        return n1 * n2;

    }
    public float divideNums(int n1, int n2) {
        return n1 / n2;

    }
    public int calcMod(int n1, int n2) {
        return n1 % n2;
    }

    public static void main(String[] args) {
        Calculator c = new Calculator();
        Scanner scan = new Scanner(System.in);
        do {
            //Display the menu
            System.out.println("\n~~~~ MENU-DRIVEN CALCULATOR ~~~~");
            System.out.println("1. Addition");
            System.out.println("2. Subtraction");
            System.out.println("3. Multiplication");
            System.out.println("4. Division");
            System.out.println("5. Modulus");
            System.out.println("6. Exit");

            System.out.println("Choose an operation: ");
            c.op = scan.nextInt();
            //Validate the user input and read the operands
            if (c.op >= 1 && c.op <= 5) {
                System.out.println("Enter the first number: ");
                c.num1 = scan.nextInt();

                System.out.println("Enter the second number: ");
                c.num2 = scan.nextInt();
            }

            switch(c.op) {
                case 0:
                    System.exit(0);
                case 1:
                     System.out.println("Sum: " + c.addNums(c.num1, c.num2));
                    break;
                case 2:
                    System.out.println("Difference: " + c.subtractNums(c.num1, c.num2));
                    break;
                case 3:
                    System.out.println("Product: " +c.multiplyNums(c.num1, c.num2));
                    break;
                case 4:
                    System.out.println("Division: " + c.divideNums(c.num1, c.num2));
                    break;
                case 5:
                    System.out.println("Modulus: " + c.calcMod(c.num1, c.num2));
                    break;
                case 6:
                    System.out.println("Exiting the calculator...");
                    break;
                default:
                    System.out.println("Invalid Choice. Please try again.");
            }
        } while(c.op != 6);
        scan.close(); //Close the scanner instance
    }
}




