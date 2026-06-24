package model;
public class Book{
    private int id;
    private String title;
    private String author;

    // constructor used to fetch books
    public Book(int id, String title, String author){
        this.id = id;
        this.title = title;
        this.author = author;
    }

    // constructor used to add books
    public Book(String title, String author){
        this.title = title;
        this.author = author;
    }

    //getters
    public int getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public String getAuthor(){
        return author;
    }

    //setters
    public void setId(int id){
        this.id = id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setAuthor(String author){
        this.author = author;
    }
}