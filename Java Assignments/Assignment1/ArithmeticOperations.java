import java.util.Scanner;
//import java.util.Scannercanner;

public class ArithmeticOperations {
    public int num1, num2;
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
        ArithmeticOperations op = new ArithmeticOperations();

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter first number: ");
        op.num1 = scan.nextInt();
        System.out.println("Enter second number: ");
        op.num2 = scan.nextInt();

        int sum = op.addNums(op.num1, op.num2);
        int diff = op.subtractNums(op.num1, op.num2);
        long prod = op.multiplyNums(op.num1, op.num2);
        float div = op.divideNums(op.num1, op.num2);
        int rem = op.calcMod(op.num1, op.num2);

        System.out.println("Sum: " + sum + " " + "Difference: " + diff + " " + "Product: " + prod + " " + "Division: " + div + " " + "Modulus: " + rem);

        System.out.println("Sum of the numbers: " + op.addNums(op.num1, op.num2) + " " + "Difference: " + op.subtractNums(op.num1, op.num2) + " " + "Product: " + op.multiplyNums(op.num1, op.num2) + " " + "Division: " + op.divideNums(op.num1, op.num2) + " " + "Modulus: " + op.calcMod(op.num1, op.num2));
        scan.close();
    }

}
