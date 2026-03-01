import java.util.Scanner;

public class Vector {
    private double[] components;

    public Vector(double[] components) throws InvalidDimensionException{
            if (components.length != 2 && components.length != 3) {
            throw new InvalidDimensionException("Only 2D or 3D vectors are allowed.");
        }
        if(components.length == 0){
            System.out.println("Vector components cannot be empty.");
        }
        this.components = components;
    }

    // Get dimension
    public int getDimension() {
        return components.length;
    }
    public void checkDimension(Vector v1) throws  InvalidDimensionException{
        if (this.getDimension() != v1.getDimension()){
            throw new InvalidDimensionException("Vectors must have same dimension.");
        }
    }

    // Addition
    public Vector add(Vector other) throws InvalidDimensionException {
        /*if (this.getDimension() != other.getDimension()) {
            throw new InvalidDimensionException("Vectors must have same dimension for addition.");
        }*/
        checkDimension(other);

        double[] result = new double[components.length];
        for (int i = 0; i < components.length; i++) {
            result[i] = this.components[i] + other.components[i];
        }

        return new Vector(result);
    }

        // Subtraction
    public Vector subtract(Vector other) throws InvalidDimensionException {
        /*if (this.getDimension() != other.getDimension()) {
            throw new InvalidDimensionException("Vectors must have same dimension for subtraction.");
        }*/
        checkDimension(other);

        double[] result = new double[components.length];
        for (int i = 0; i < components.length; i++) {
            result[i] = this.components[i] - other.components[i];
        }

        return new Vector(result);
    }

    // Dot Product
    public double dotProduct(Vector other) throws InvalidDimensionException {
        /*if (this.getDimension() != other.getDimension()) {
            throw new InvalidDimensionException("Vectors must have same dimension for dot product.");
        }*/
        checkDimension(other);
        double result = 0;
        for (int i = 0; i < components.length; i++) {
            result += this.components[i] * other.components[i];
        }

        return result;
    }

    // Display vector
    public void display() {
        System.out.print("(");
        for (int i = 0; i < components.length; i++) {
            System.out.print(components[i]);
            if (i < components.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println(")");
    }

    public static void main(String[] args){

        //Negative case
        try{
            double[] vec1 = {2,5};
            double [] vec2 = {9, 10, 11};
            Vector vector1 = new Vector(vec1);
            Vector vector2 = new Vector(vec2);


            Vector sum = vector1.add(vector2);
            Vector difference = vector1.subtract(vector2);

            System.out.print("Addition Result: ");
            sum.display();

            System.out.print("Subtraction Result: ");
            difference.display();

            //Vector vec1 = new Vector(double[]vec {2,5,7});
        }catch (InvalidDimensionException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid input! Please enter numeric values.");
        }

        //Positive Case
        Scanner scanner = new Scanner(System.in);
        try {

            //Scanner scanner = new Scanner(System.in);
            System.out.print("Enter dimension (2 or 3): ");
            int dimension = scanner.nextInt();

            if (dimension != 2 && dimension != 3) {
                throw new InvalidDimensionException("Dimension must be 2 or 3.");
            }

            double[] v1Components = new double[dimension];
            double[] v2Components = new double[dimension];

            System.out.println("Enter components of Vector 1:");
            for (int i = 0; i < dimension; i++) {
                v1Components[i] = scanner.nextDouble();
            }

            System.out.println("Enter components of Vector 2:");
            for (int i = 0; i < dimension; i++) {
                v2Components[i] = scanner.nextDouble();
            }

            Vector v1 = new Vector(v1Components);
            Vector v2 = new Vector(v2Components);

            Vector sum = v1.add(v2);
            Vector difference = v1.subtract(v2);
            double dot = v1.dotProduct(v2);

            System.out.print("Addition Result: ");
            sum.display();

            System.out.print("Subtraction Result: ");
            difference.display();

            System.out.println("Dot Product: " + dot);

        } catch (InvalidDimensionException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid input! Please enter numeric values.");
        }

        scanner.close();
}
    
}

