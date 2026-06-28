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
