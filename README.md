# Java Development Internship at ShadowFox (June 2026)

This repository contains the projects of my Java Development Internship at Shadowfox. The objective is to strengthen core Java concepts, Object-Oriented Programming principles, problem-solving skills, and GUI development using Java Swing.

---

## 📌 Technologies Used

- Java
- Object-Oriented Programming (OOP)
- Java Swing
- Collections Framework (ArrayList)
- Event Handling
- Git & GitHub

---

# 📂 Projects

---

# Week-1

---

## 1️. Calculator Application

> A menu-driven calculator application developed in Java.

### Features

### Console Version
- Basic Arithmetic Operations
  - Addition
  - Subtraction
  - Multiplication
  - Division
  - Modulus
<p align="center">
  <img width="400" height="400" alt="image" src="https://github.com/user-attachments/assets/56337a1d-cd72-439e-999a-a2de5bb35f82" />
</p>

### Scientific Calculator
- Square Root
- Power
- Logarithm
- Trigonometric Functions

### Temperature Converter
- Celsius to Fahrenheit
- Fahrenheit to Celsius

### Currency Converter
- Multiple currency conversion options

### GUI Version
- Interactive Swing-based interface
- User-friendly layout
- Multiple functional panels
<p align="center">
<img width="500" height="350" alt="image" src="https://github.com/user-attachments/assets/bdcc20bf-25b4-4cbe-acfd-ee3070da88d0" />
</p>

### Concepts Used
- Methods
- Conditional Statements
- Loops
- Switch Case
- Java Math Library
- Swing GUI

---

## 2️. Contact Management System

> A desktop-based Contact Management System developed using Java Swing and OOP principles.

### Features

- Add Contact

- View Contacts

- Search Contact

- Edit Contact

- Delete Contact

- Duplicate Phone Number Prevention

- Phone Number Validation

- Email Validation

- JTable-based Contact Display

---

### Contact Information Stored

Each contact contains:

- Name
- Phone Number
- Email Address

---

### GUI Modules

#### Main Dashboard
Provides navigation to all operations.

#### Add Contact
Allows users to add new contacts with validation checks.

#### View Contacts
Displays all contacts in a JTable format.

#### Search Contact
Searches contacts using phone number.

#### Edit Contact
Updates existing contact information.

#### Delete Contact
Removes contacts from the system.

---

<h2 align="center">Contact Management System Screenshots</h2>

<p align="center">
    <img width="70%" alt="Screenshot 2026-06-08 185717" src="https://github.com/user-attachments/assets/308dcb57-44f6-4135-9c63-30ba3a1ea875" />
</p>

<p align="center">
    <b>Main Dashboard</b>
</p>

<p align="center">
    <img width="45%" alt="Screenshot 2026-06-08 185905" src="https://github.com/user-attachments/assets/04e5f106-498d-404b-a2e0-710d20eac7de" />
    <img width="45%" alt="Screenshot 2026-06-08 190333" src="https://github.com/user-attachments/assets/1bd75147-f764-460d-a5a6-b17fe42342f9" />
    
</p>

<p align="center">
    <b>Add Contact</b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <b>Edit Contact</b>
</p>

<p align="center">
    <img width="45%" alt="Screenshot 2026-06-08 191310" src="https://github.com/user-attachments/assets/b490f0f1-d294-462a-9d3a-34f5ad4e2d3b" />
    <img width="45%" height="473" alt="Screenshot 2026-06-08 190156" src="https://github.com/user-attachments/assets/b13fa1eb-53a1-4a07-8d56-3ea07eaef4e1" />
</p>

<p align="center">
    <b>Search Contact</b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <b>View Contacts</b>
</p>

---

### Validation Implemented

- Empty Field Validation
- Phone Number Validation
- Email Validation
- Duplicate Phone Number Check

---

### Concepts Used

- Classes and Objects
- Constructors
- Encapsulation
- ArrayList
- CRUD Operations
- Event Handling
- Java Swing
- JTable
- Exception Prevention through Validation

---

# Week-2

---

## 1. Inventory Management System

> A desktop GUI application built with Java Swing that lets users manage product inventory through a clean, structured interface. Designed around core software engineering patterns including the Singleton, data binding via a custom table model, and O(n) search with conditional formatting for low-stock alerts.

<p align="center">
  <img width="850" height="516" alt="Screenshot 2026-06-14 020650" src="https://github.com/user-attachments/assets/75ab0543-1762-4af5-8bd4-f5350b1cd950" />
  <br> <br> <br>
  <img width="850" height="250" alt="Screenshot 2026-06-14 020736" src="https://github.com/user-attachments/assets/f80165f6-a438-4979-a056-9c55a047d296" />
</p>

---

### Features

**Core CRUD Operations**

- Add new products with a name, category, unit price, and quantity
- Update any existing product's fields directly from the form panel
- Delete products with a confirmation dialog to prevent accidental removal
- All form fields reset automatically after a successful add or update

**Data Validation**

- Negative stock is blocked at two levels: the `Product` setter throws `IllegalArgumentException` for any quantity below zero, and the form validates input before it ever reaches the model
- Price and quantity fields are type-checked before submission; non-numeric input produces a clear error message
- All fields are required - partial submissions are rejected

**Live Search and Filter**

- The search bar filters the table on every keystroke with no button press required, using an O(n) linear scan across product name and ID
- A "Low Stock Only" checkbox filters the view to show only products below the threshold quantity, and can be combined with the text search simultaneously

**Barcode Lookup**

- A dedicated Barcode ID field accepts a numeric product ID and instantly scrolls to and selects the matching row in the table
- As the user types, the lookup runs on each keystroke using the same O(n) search algorithm, giving real-time feedback in the status bar

**Low Stock Alerts**

- Any product with a quantity below 5 is highlighted in red across the entire row, implemented via a custom `DefaultTableCellRenderer` subclass
- The highlighting updates automatically whenever data changes, with no manual refresh required
- The header stat strip also tracks and displays the current count of low-stock items

**Live Inventory Stats**

- The header bar displays total product count, total inventory value (price x quantity summed across all products), and low-stock item count
- All three values recalculate and repaint after every add, update, or delete operation

**Instant Total Value Calculation**

- The "Total Value" column in the table (unit price x quantity) is computed on the fly inside the table model and updates the moment a product is edited

---

### Architecture

The project follows this structure:

```
src/
├── Main.java                        Entry point — launches on the Event Dispatch Thread
├── Product.java                     A single inventory item
│── InventoryManager.java            Singleton: owns the master product list and all CRUD logic
├── InventoryTableModel.java         Extends AbstractTableModel - binds Product objects to table rows
├── LowStockRenderer.java            Extends DefaultTableCellRenderer - applies red row formatting
└── MainFrame.java                   Main JFrame: assembles all panels, wires all event listeners
```

**Singleton Pattern** — `InventoryManager` enforces a single shared instance via a private constructor and a static `getInstance()` method. This guarantees all parts of the UI operate on the same product list.

**Data Binding** — `InventoryTableModel` extends `AbstractTableModel` and overrides `getValueAt(row, col)` to map each `Product` field to a table column. Calling `fireTableDataChanged()` after any mutation signals Swing to repaint all cells without manual coordination.

**Conditional Formatting** — `LowStockRenderer` overrides `getTableCellRendererComponent()` and is applied to every column. On each cell render, it reads the row's `Product` quantity and sets the background and foreground colours accordingly.

---

### Usage

| Action | How |
|---|---|
| Add a product | Fill in Name, Category, Price, Quantity and click ADD |
| Edit a product | Click any table row to auto-fill the form, modify fields, click UPDATE |
| Delete a product | Select a row, click DELETE, confirm the dialog |
| Search by name | Type in the Search bar — results filter live |
| Search by ID / barcode | Type a numeric ID in the Barcode ID field — the matching row is selected |
| View low stock only | Tick the "!! Low Stock Only" checkbox |
| Clear the form | Click CLEAR or click an empty area of the table |

---

### Design Decisions

**Why Swing over JavaFX?** Swing ships with every standard JDK installation and requires no additional setup or module configuration, making it immediately runnable in any standard Java environment.

**Why no database?** The scope of this project is demonstrating GUI patterns and object-oriented design. The in-memory `ArrayList` inside `InventoryManager` can be replaced with JDBC or any ORM without changing a single line of the view or controller layers, because the Singleton acts as a clean abstraction boundary.

**Search algorithm choice:** O(n) linear scan is the correct choice at this data scale. A `HashMap<Integer, Product>` would give O(1) ID lookups, but would require maintaining a second data structure in sync with the list. At typical inventory sizes (hundreds to low thousands of rows), the linear scan completes in under a millisecond and the added complexity of a dual structure is not justified.

---

## Library Management System

> A console-based Library Management System built in Java, demonstrating real-world backend architecture with **JDBC**, **DAO pattern**, **SQLite persistence**, and **Google Books API integration**.

<p align="center">
  <img width="500" height="380" alt="Screenshot 2026-06-24 174838" src="https://github.com/user-attachments/assets/b029865b-f2e7-4bbf-a5f8-79b2610f23db" />
  <br> <br> <br>
  <img width="500" height="320" alt="Screenshot 2026-06-24 175104" src="https://github.com/user-attachments/assets/bf0167ab-9dcf-41e5-8f0e-a68610fc76cf" />
  <br> <br> <br>
  <img width="700" height="280" alt="Screenshot 2026-06-24 173942" src="https://github.com/user-attachments/assets/b62cb556-6eb9-4415-8d6b-2c66db683061" />
</p>

---

### Features
 
| Feature | Description |
|---|---|
| Book Management | Add books manually or auto-fetch via ISBN |
| User Management | Register and list library members |
| Borrow / Return | Issue and return books with date tracking |
| Fine Calculation | Automatically calculates overdue fines |
| Google Books API | Fetch book metadata by ISBN in real time |
| SQLite Database | Persistent local storage via JDBC |
 
---
 
### Project Structure
 
```
LibraryManagementSystem/
├── src/
│   ├── dao/                          Data Access Objects (DB layer)
│   │   ├── BookDAO.java
│   │   ├── UserDAO.java
│   │   └── BorrowRecordDAO.java
│   ├── model/                        Entity / POJO classes
│   │   ├── Book.java
│   │   ├── User.java
│   │   └── BorrowRecord.java
│   ├── service/                      Business logic layer
│   │   └── LibraryService.java
│   ├── util/                         Utilities
│   │   └── DatabaseConnection.java
│   ├── Main.java                     Entry point
│   └── library.db                    SQLite database file
├── lib/
│   ├── sqlite-jdbc-3.53.2.0.jar
│   └── json-20260522.jar
├── bin/
├── target/
└── pom.xml
```
 
---
 
### Architecture
 
This project follows a **layered architecture** pattern:
 
```
Main.java
    └── LibraryService        (Business Logic)
            ├── BookDAO       (DB operations for books)
            ├── UserDAO       (DB operations for users)
            └── BorrowRecordDAO  (DB operations for borrow records)
                    └── DatabaseConnection  (JDBC SQLite connection)
```
 
- **Model layer** — Plain Java objects representing `Book`, `User`, and `BorrowRecord`
- **DAO layer** — Each DAO handles all CRUD SQL for its entity, keeping DB logic isolated
- **Service layer** — `LibraryService` coordinates DAOs and enforces business rules (e.g. availability checks, fine calculation)
- **Main** — Drives the console menu loop and delegates to the service
---
 
### Prerequisites
 
- Java 8 or higher
- No Maven installation required (JARs are bundled in `/lib`)
### Running the Project
 
**Option 1 — Using VS Code / any IDE:**
Open the project and run `Main.java` directly.
 
**Option 2 — Command line:**
 
```bash
# Compile
javac -cp "lib/sqlite-jdbc-3.53.2.0.jar;lib/json-20260522.jar" -d bin src/**/*.java src/Main.java
 
# Run (Windows)
java -cp "bin;lib/sqlite-jdbc-3.53.2.0.jar;lib/json-20260522.jar" Main
 
# Run (Linux / macOS — use : instead of ;)
java -cp "bin:lib/sqlite-jdbc-3.53.2.0.jar:lib/json-20260522.jar" Main
```
 
> The SQLite database (`library.db`) is created automatically on first run inside `/src`.
 
---
 
### Menu Options
 
```
=== Library Menu ===
1.  Add Book
2.  Add User
3.  Borrow Book
4.  Return Book
5.  List All Books
6.  List All Users
7.  List Borrow Records
8.  Calculate Fine
9.  Add Book by ISBN (Google Books API)
10. Exit
```
 
---
 
### Google Books API Integration
 
Option **9** allows you to add a book by entering its ISBN. The system fetches the title, author, and other metadata directly from the [Google Books API](https://developers.google.com/books) and saves it to the local database — no manual entry needed.
 
> **Note:** The API is rate-limited. If you see a `429` error, wait a moment and try again.
 
---
 
### Tech Stack
 
| Technology | Purpose |
|---|---|
| Java | Core application language |
| SQLite | Local relational database |
| JDBC | Java–database connectivity |
| DAO Pattern | Separation of DB logic from business logic |
| Google Books API | Remote book metadata lookup |
| org.json | JSON parsing for API responses |
 
---
 
### Dependencies
 
| JAR | Version | Purpose |
|---|---|---|
| `sqlite-jdbc` | 3.53.2.0 | SQLite JDBC driver |
| `json` | 20260522 | JSON parsing (API responses) |
 
---

# Week-3 and Week-4

---

## Real-Time Chat Application with Java Socket Programming

> A real-time multi-client chat application built with Java Socket Programming and JavaFX.
Supports multiple users, chat rooms, private messaging, and file transfer over raw TCP sockets.

<p align="center">
https://github.com/user-attachments/assets/c5da4037-2e3c-4f96-ba3e-0eca5b061311
</p>
<br>
<p align = "center">
  <img width="400" height="450" alt="Screenshot 2026-06-27 204853" src="https://github.com/user-attachments/assets/2c5f7661-7dbd-496e-b273-471c38948e4d" />
  <img width="596" height="450" alt="Screenshot 2026-06-27 210103" src="https://github.com/user-attachments/assets/e8908d18-cc83-4e86-b974-250f32518589" />
  <br> <br> <br>
  <img width="900" height="500" alt="Screenshot 2026-06-28 114324" src="https://github.com/user-attachments/assets/a91fa3ca-7842-4769-8240-9690c7d94f2e" />
</p>

---

### Features

- Real-time messaging across multiple clients simultaneously
- Three predefined chat rooms — General, Tech, Random
- Private messaging using `@username message` syntax
- File transfer (images, text files, PDFs) up to 5MB
- Private file transfer using `@username` prefix with the 📎 button
- Online users sidebar — updates live on join and leave
- Room header bar showing current room at all times
- Graceful disconnect handling — server never crashes on client exit
- JavaFX UI with dark black and rose pink theme

---

### Architecture

```
Server JVM                          Client JVM
──────────────────────────────      ──────────────────────────
ChatServer                          ChatApp (JavaFX entry point)
  └── ServerSocket (port 12345)       └── Login Screen
  └── ConcurrentHashMap                     │
       <String, ClientHandler>        ChatWindow
  └── Room registry                    └── Socket connection
       <String, String[]>              └── Reader Thread
                                            └── listenForMessages()
ClientHandler (one per client)              └── Platform.runLater()
  └── Thread per connected user       └── Writer Thread
  └── ObjectInputStream                    └── handleSend()
  └── ObjectOutputStream                   └── handleFileSelect()
  └── volatile currentRoom
  └── broadcast / private routing
```

### Key Design Decisions

**Thread-per-client model** — each connected client gets its own
`ClientHandler` thread. Simple, readable, and sufficient for this
scale. The alternative (NIO with selectors) handles thousands of
connections but adds significant complexity without benefit here.

**ConcurrentHashMap for user registry** — the `connectedUsers` map
is accessed by every ClientHandler thread simultaneously. A regular
HashMap would cause race conditions. ConcurrentHashMap provides
thread-safe reads and writes without explicit locking.

**volatile currentRoom** — marked volatile so all threads always
read the latest value from main memory. Without this, room-switch
broadcasts leak into the wrong room due to CPU cache inconsistency.

**Platform.runLater()** — JavaFX only allows UI updates on the
JavaFX Application Thread. All incoming messages arrive on a
background socket thread, so every UI update is wrapped in
`Platform.runLater()` to queue it onto the correct thread.

**Serializable Message object** — instead of designing a custom
text protocol, the `Message` class implements `Serializable`.
Java's ObjectOutputStream/ObjectInputStream handle the
serialization automatically, including byte arrays for file data.

---

### File Transfer Protocol

Files are transferred as part of the standard `Message` object:

```
Message {
  type     : "FILE" or "PRIVATE_FILE"
  sender   : username of sender
  room     : target room (FILE) or recipient username (PRIVATE_FILE)
  fileName : original file name e.g. photo.png
  fileData : raw file content as byte[]
}
```

**Strategy — Metadata + Payload in one serialized object:**
The file name and size are stored as fields (metadata) alongside
the raw byte array (payload) in the same Message object.
Java serialization converts the entire object — including the
byte array — into a stream of bytes for transmission.
The receiver deserializes the object and reconstructs both the
metadata and the file content from the same stream.

**Size limit:** 5MB per file. Enforced on the client before
reading bytes into memory, preventing OutOfMemoryError on large files.

**Supported formats:** PNG, JPG, JPEG, GIF, TXT, PDF

---

### Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 23 |
| UI | JavaFX 21 |
| Networking | Java Sockets (TCP) |
| Concurrency | ConcurrentHashMap, volatile |
| Serialization | Java ObjectInputStream / ObjectOutputStream |
| Build tool | Apache Maven 3.9 |

---

### Project Structure

```
java-chat-app/
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   └── com/chatapp/
        │       ├── common/
        │       │   └── Message.java            Shared serializable data model
        │       ├── server/
        │       │   ├── ChatServer.java         Entry point, accepts connections
        │       │   └── ClientHandler.java      Per-client thread handler
        │       └── client/
        │           ├── ChatApp.java            JavaFX entry point, login screen
        │           └── ChatWindow.java         Main chat UI and socket logic
        └── resources/
            └── com/chatapp/client/
                └── chat.css                    Black and rose pink theme
```

---

### How to Run

<p align="center">
  https://github.com/user-attachments/assets/07bb7f36-9988-4251-8c91-ac952b46379d
</p>

#### Prerequisites
- Java 23 or higher
- Apache Maven 3.9 or higher

#### Step 1 — Clone the repository
```bash
git clone https://github.com/Mitti33/ShadowFox.git
cd ShadowFox/Week-3and4/java-chat-app
```

#### Step 2 — Build the project
```bash
mvn clean compile
```

#### Step 3 — Start the server
Open a terminal and run:
```bash
mvn exec:java
```
You should see:
```
Chat server started on port 12345
```

#### Step 4 — Launch the client
Open a second terminal and run:
```bash
mvn javafx:run
```
A login window will appear. Enter a username and select a room.

#### Step 5 — Connect multiple clients
Open more terminals and run `mvn javafx:run` in each.
Every client connects to the same server and can chat in real time.

#### Connecting from another device (LAN)
1. Find your machine's local IP address:
   - Windows: run `ipconfig` and look for IPv4 Address
2. In `ChatWindow.java`, change:
   ```java
   private static final String SERVER_ADDRESS = "localhost";
   ```
   to:
   ```java
   private static final String SERVER_ADDRESS = "your.local.ip";
   ```
3. Run `mvn clean compile` then `mvn javafx:run` on the other device

---

### Usage Guide

| Action | How to do it |
|---|---|
| Send a message | Type in the input field and press Enter or click Send |
| Switch rooms | Click General, Tech, or Random in the sidebar |
| Private message | Type `@username message` and press Enter |
| Send a file to room | Click 📎 and select a file |
| Send a file privately | Type `@username` in the field, then click 📎 |
| Save a received file | Click the message row showing `[Click to Save]` |

---

### Engineering Concepts Demonstrated

**TCP Handshake** — `new Socket(host, port)` on the client and
`serverSocket.accept()` on the server complete the three-way
handshake automatically. Every message travels over this
reliable, ordered byte stream.

**Threads and Runnable** — `ClientHandler implements Runnable`
so each client connection runs independently. The server's main
thread never blocks — it loops back to `accept()` immediately
after spawning each handler thread.

**Synchronization** — `ConcurrentHashMap` prevents race conditions
when multiple threads register or look up users simultaneously.
The `volatile` keyword on `currentRoom` ensures cross-thread
visibility without explicit locking.

**Blocking I/O solved with dual threads** — `ObjectInputStream
.readObject()` blocks until data arrives. Running it on a
dedicated reader thread means the UI (writer thread) stays
responsive — the user can type and send while simultaneously
receiving messages.

---

### Known Limitations and Future Improvements

- Server IP is hardcoded in `ChatWindow.java` — future: make it
  configurable from the login screen
- File transfer loads entire file into memory — future: chunked
  streaming for files larger than 5MB
- No message persistence — history is lost when the server restarts
- No authentication — any username can be entered without a password
- Rooms are predefined — future: allow dynamic room creation

---
<br>

## Learning Outcomes

Through these projects, I gained hands-on experience with:

- Core Java Programming
- Object-Oriented Design
- GUI Development using Swing
- Event-Driven Programming
- Data Management using Collections
- Software Project Structuring
- Version Control with Git and GitHub

---


## Author

**Mrittika Kundu**

B.Tech in Computer Science & Engineering  
Narula Institute of Technology

Java Development Internship
