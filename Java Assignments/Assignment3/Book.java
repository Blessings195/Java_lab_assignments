public class Book {
    public String title;
    public double price;
    public String ISBN;
    public String genre;
    public String author;

    public Book() throws InvalidPriceException, InvalidBookGenreException{
        title = "Harry Potter and The Philosopher's Stone";
        if (price<0){
            throw new InvalidPriceException("Price cannot be negative!");
        }
        price = 400.99;
        ISBN = "NH30998";
        genre = "Fantasy";
        author = "J.K Rowling";
        if(!genre.equalsIgnoreCase("Fantasy") && !genre.equalsIgnoreCase("Romance") && !genre.equalsIgnoreCase("Finance") && !genre.equalsIgnoreCase("Fiction")){
            throw new InvalidBookGenreException("Invalid genre! Choose one of the existing ones.");
        }
    }

    public Book (String title, String ISBN, String author, double price, String genre)throws InvalidPriceException, InvalidBookGenreException{
        this.title = title;
        this.ISBN = ISBN;
        this.author = author;
        if (price<0){
            throw new InvalidPriceException("Price cannot be negative!");
        }
        this.price = price;
        this.genre = genre;
        if(!genre.equalsIgnoreCase("Fantasy") && !genre.equalsIgnoreCase("Romance") && !genre.equalsIgnoreCase("Finance") && !genre.equalsIgnoreCase("Fiction")){
            throw new InvalidBookGenreException("Invalid genre! Choose one of the existing ones.");
        }
    }
    //Book b2 = new Book(Book);

    //Copy constructor
    public Book(Book b) throws InvalidPriceException, InvalidBookGenreException{
        title = b.title;
        ISBN = b.ISBN;
        author = b.author;
        price = b.price;
        genre = b.genre;
        if (price<0){
            throw new InvalidPriceException("Price cannot be negative!");
        }
        if(!genre.equalsIgnoreCase("Fantasy") && !genre.equalsIgnoreCase("Romance") && !genre.equalsIgnoreCase("Finance") && !genre.equalsIgnoreCase("Fiction")){
            throw new InvalidBookGenreException("Invalid genre! Choose one of the existing ones.");
        }
    }



}
