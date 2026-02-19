public class DivisibilityTest {
    public static void main(String[] args) {
        int n = 1;
        while(n <= 50) {
            if (n % 2 == 0) {
                System.out.println(n);
            }
            if (n % 3 == 0) {
                System.out.println(n);
            }
            if (n % 5 == 0) {
                System.out.println(n);
            }
            n++;
        }

    }
}
