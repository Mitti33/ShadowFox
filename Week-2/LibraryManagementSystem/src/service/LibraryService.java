package service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.json.JSONObject;

import dao.BookDAO;
import dao.BorrowRecordDAO;
import dao.UserDAO;
import model.Book;
import model.BorrowRecord;
import model.User;

public class LibraryService {
    private final BookDAO bookDAO;
    private final UserDAO userDAO;
    private final BorrowRecordDAO borrowDAO;

    public LibraryService() {
        this.bookDAO = new BookDAO();
        this.userDAO = new UserDAO();
        this.borrowDAO = new BorrowRecordDAO();

        // ensure tables exist
        bookDAO.createTable();
        userDAO.createTable();
        borrowDAO.createTable();
    }

    // book management
    public void addBook(String title, String author) {
        bookDAO.addBook(new Book(title, author));
    }

    public void addUser(String name, String email) {
        userDAO.addUser(new User(name, email));
    }

    // borrow logic with rule: prevent double borrowing
    public boolean borrowBook(int userId, int bookId) {
        for (BorrowRecord r : borrowDAO.getAllRecords()) {
            if (r.getBookId() == bookId && r.getReturnDate() == null) {
                System.out.println("Book is already borrowed!");
                return false;
            }
        }
        borrowDAO.addBorrowRecord(new BorrowRecord(userId, bookId, LocalDate.now()));
        return true;
    }

    public void returnBook(int recordId) {
        borrowDAO.returnBook(recordId, LocalDate.now());
    }

    // overdue fine calculation (₹10 per day after 14 days)
    public long calculateFine(int recordId) {
        for (BorrowRecord r : borrowDAO.getAllRecords()) {
            if (r.getId() == recordId && r.getReturnDate() != null) {
                LocalDate dueDate = r.getBorrowDate().plusDays(14);
                long daysLate = ChronoUnit.DAYS.between(dueDate, r.getReturnDate());
                return daysLate > 0 ? daysLate * 10 : 0;
            }
        }
        return 0;
    }

    // Google Books API integration
    public Book fetchBookByISBN(String isbn) {
        try {
            String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn;
            HttpURLConnection conn = (HttpURLConnection) URI.create(apiUrl).toURL().openConnection();
            conn.setRequestMethod("GET");

            StringBuilder response = new StringBuilder();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = in.readLine()) != null) response.append(line);
            }

            JSONObject json = new JSONObject(response.toString());
            JSONObject volumeInfo = json.getJSONArray("items")
                                        .getJSONObject(0)
                                        .getJSONObject("volumeInfo");

            String title = volumeInfo.getString("title");
            String author = volumeInfo.getJSONArray("authors").getString(0);

            Book book = new Book(title, author);
            bookDAO.addBook(book);
            return book;

        } catch (java.io.IOException e) {
            System.err.println("Error fetching book from API: " + e.getMessage());
            return null;
        }
    }

    // lists
    public List<Book> listBooks() { return bookDAO.getAllBooks(); }
    public List<User> listUsers() { return userDAO.getAllUsers(); }
    public List<BorrowRecord> listBorrowRecords() { return borrowDAO.getAllRecords(); }
}
