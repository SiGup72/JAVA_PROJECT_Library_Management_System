package library.models;

public class Book {

    private String bookid;
    private String title;
    private String author;
    private String genre;

    // Default constructor
    public Book() {
        this.bookid = "B000";
        this.title = "Untitled";
        this.author = "Unknown";
        this.genre = "General";
    }

    // Parameterized constructor
    public Book(String bookid, String title, String author, String genre) {
        this.bookid = bookid;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    // Getters
    public String getBookId() { return bookid; }
    public String getTitle()  { return title; }
    public String getAuthor() { return author; }
    public String getGenre()  { return genre; }

    // Setters
    public void setBookId(String bookid) { this.bookid = bookid; }
    public void setTitle(String title)   { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setGenre(String genre)   { this.genre = genre; }

    // Display method
    public void display() {
        System.out.println("ID: " + bookid + " | Title: " + title + " | Author: " + author + " | Genre: " + genre);
    }

}
