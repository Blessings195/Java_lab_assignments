import java.util.ArrayList;
public class ArrayListOfBook {
    public static void main(String[] args){

        ArrayList<Book> alb = new ArrayList<>();
        ArrayList<Book>alb2 = new ArrayList<>();
        try {
            Book b1 = new Book();
            alb.add(b1);
        }

        catch (InvalidPriceException | InvalidBookGenreException ig) {
            System.out.println("Error in b1: " + ig.getMessage());
        }
        
        try {
            Book b2 = new Book("Rich Dad Poor Dad", "NH99987", "Robert.T.Kiyosaki",
         250.50, "Finance");
            alb.add(b2);
        }
        
        catch (InvalidPriceException | InvalidBookGenreException ig) {
            System.out.println("Error in b2: " + ig.getMessage());
        }

        try {
            Book b = new Book("Something New", "NMK56993", "Amanda Abram", 240.99, "Romance");
            Book b3 = new Book(b);
            alb.add(b3);
        }
        catch  (InvalidPriceException | InvalidBookGenreException ig){
            System.out.println("Error in b3: " + ig.getMessage());
        }
        try {
            Book b4 = new Book();
            b4.title = "Harry Potter and The Chamber of Secrets";
            b4.ISBN = "NH288740";
            b4.price = 460.89;
            alb.add(b4);
        }
        catch  (InvalidPriceException | InvalidBookGenreException ig){
            System.out.println("Error in b4: " + ig.getMessage());
        }
        try {
            Book b5 = new Book("The Importance of Getting Revenge", "MK7649376", "Amanda Abram", 500.99, "Romance");
            alb2.add(b5);
        }

        catch  (InvalidPriceException | InvalidBookGenreException ig){
            System.out.println("Error in b5: " + ig.getMessage());
        }
        try {
            Book b6 = new Book("The Last Vampire", "KM376649", "Christopher Pike",380.99,  "Paranormal");
            alb2.add(b6);

        }
        catch  (InvalidPriceException | InvalidBookGenreException ig){
            System.out.println("Error in b6: " + ig.getMessage());
        }

        alb.addAll(alb2);

        //Print the book details
        System.out.println("Book details from ArrayList: ");
        for(Book bl: alb){
            System.out.println("\nTitle: " + bl.title);
            System.out.println("Author: " + bl.author);
            System.out.println("Price: " + bl.price);
            System.out.println("Genre: " + bl.genre);

        }

        //Find the average price of the books
        if (!alb.isEmpty()){
            double[] sum = {0};
            alb.forEach(book -> sum[0] += book.price);
            double average = sum[0] / alb.size();
            System.out.printf("\nAverage: %.2f", average);
        } else{
            System.out.println("No valid books to calculate average.");
        }

        //Find the books in the genre 'Fantasy'
        System.out.println("\n\nBooks in the \"Fantasy\" genre: ");
        alb.forEach(f->{if (f.genre.equalsIgnoreCase("Fantasy")) System.out.println(f.title);});


        } 


    }
