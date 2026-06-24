import java.util.Scanner;

import model.Book;
import model.BorrowRecord;
import model.User;
import service.LibraryService;

public class Main {
    public static void main(String[] args) {
        LibraryService service = new LibraryService();
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Library Menu ===");
            System.out.println("1. Add Book");
            System.out.println("2. Add User");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. List All Books");
            System.out.println("6. List All Users");
            System.out.println("7. List Borrow Records");
            System.out.println("8. Calculate Fine");
            System.out.println("9. Add Book by ISBN (Google Books API)");
            System.out.println("10. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter author: ");
                    String author = sc.nextLine();
                    service.addBook(title, author);
                    System.out.println("Book added!");
                    break;

                case 2:
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter email: ");
                    String email = sc.nextLine();
                    service.addUser(name, email);
                    System.out.println("User added!");
                    break;

                case 3:
                    System.out.print("Enter userId: ");
                    int userId = sc.nextInt();
                    System.out.print("Enter bookId: ");
                    int bookId = sc.nextInt();
                    if (service.borrowBook(userId, bookId)) {
                        System.out.println("Book borrowed!");
                    }
                    break;

                case 4:
                    System.out.print("Enter borrow record id: ");
                    int recordId = sc.nextInt();
                    service.returnBook(recordId);
                    System.out.println("Book returned!");
                    break;

                case 5:
                    for (Book b : service.listBooks()) {
                        System.out.println(b.getId() + ": " + b.getTitle() + " by " + b.getAuthor());
                    }
                    break;

                case 6:
                    for (User u : service.listUsers()) {
                        System.out.println(u.getId() + ": " + u.getName() + " (" + u.getEmail() + ")");
                    }
                    break;

                case 7:
                    for (BorrowRecord r : service.listBorrowRecords()) {
                        System.out.println(r.getId() + ": User " + r.getUserId() +
                                           " borrowed Book " + r.getBookId() +
                                           " on " + r.getBorrowDate() +
                                           (r.getReturnDate() != null ? ", returned on " + r.getReturnDate() : ""));
                    }
                    break;

                case 8:
                    System.out.print("Enter borrow record id: ");
                    int fineRecordId = sc.nextInt();
                    long fine = service.calculateFine(fineRecordId);
                    System.out.println("Fine: ₹" + fine);
                    break;

                case 9:
                    System.out.print("Enter ISBN: ");
                    String isbn = sc.nextLine();
                    Book fetched = service.fetchBookByISBN(isbn);
                    if (fetched != null) {
                        System.out.println("Book added from API: " + fetched.getTitle() + " by " + fetched.getAuthor());
                    } else {
                        System.out.println("Could not fetch book info.");
                    }
                    break;

                case 10:
                    running = false;
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
        sc.close();
    }
}
