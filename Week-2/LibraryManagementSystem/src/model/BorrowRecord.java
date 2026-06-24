package model;

import java.time.LocalDate;

public class BorrowRecord {
    private int id, userId, bookId;
    private LocalDate borrowDate, returnDate;

    // constructor for new borrow
    public BorrowRecord(int userId, int bookId, LocalDate borrowDate){
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
    }

    // constructor for fetching from DB
    public BorrowRecord(int id, int userId, int bookId, LocalDate borrowDate, LocalDate returnDate){
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    // getters
    public int getId(){
        return id;
    }
    public int getUserId(){
        return userId;
    }
    public int getBookId(){
        return bookId;
    }
    public LocalDate getBorrowDate(){
        return borrowDate;
    }
    public LocalDate getReturnDate(){
        return returnDate;
    }

    // setters
    public void setId(int id){
        this.id = id;
    }
    public void setUserId(int userId){
        this.userId = userId;
    }
    public void setBookId(int bookId){
        this.bookId = bookId;
    }
    public void setBorrowDate(LocalDate borrowDate){
        this.borrowDate = borrowDate;
    }
    public void setReturnDate(LocalDate returnDate){
        this.returnDate = returnDate;
    }
}
