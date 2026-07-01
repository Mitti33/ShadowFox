# 🌸 My Learnings — ShadowFox Java Development Internship
### June 2026 · Mrittika Kundu

> *"Every project taught me something I didn't know I didn't know."*

This file is a reflection of the concepts, skills, and mindset shifts I picked up over the course of this internship — across five projects, four weeks, and a lot of debugging. 🐛➡️✅

---

## 🗺️ The Journey at a Glance

| Week | Project | Core Theme |
|------|---------|------------|
| 1️⃣ | Calculator App | Java fundamentals, Swing basics |
| 1️⃣ | Contact Management System | OOP, validation, JTable |
| 2️⃣ | Inventory Management System | Architecture patterns, MVC |
| 2️⃣ | Library Management System | Databases, JDBC, APIs |
| 3️⃣ & 4️⃣ | Real-Time Chat Application | Networking, concurrency, JavaFX |

---

## 💡 Core Learnings by Theme

---

### 🧮 1. Java Fundamentals Go Deeper Than You Think

Starting with the **Calculator App**, I thought I already knew Java. I was wrong — or at least, I didn't know it *well enough* yet.

- Implementing the **Shunting Yard algorithm** to handle operator precedence taught me that even a simple calculator has non-trivial logic hiding underneath.
- Working with `Math` library functions for scientific calculations (trig, log, power) made me realise how much Java's standard library offers — and how rarely textbooks cover it practically.
- Building **both** a console and GUI version of the same app was a great lesson in **separation of logic from presentation** — the math didn't care whether it was Swing or console driving it.

> 🔑 **Key takeaway:** Clean logic is reusable logic. If your core functions are tangled with UI code, you end up rewriting everything twice.

---

### 🏗️ 2. Object-Oriented Design is About Decisions, Not Just Syntax

The **Contact Management System** was my first project where OOP stopped being a textbook concept and started being a set of *decisions I had to make*.

- Deciding what a `Contact` object should know vs. what should live in the manager class.
- Learning that **validation belongs at the model level** — not scattered across UI event listeners. A phone number shouldn't need a button click to be validated; the object should reject bad data on its own.
- Understanding **encapsulation in practice**: private fields with controlled setters aren't boilerplate — they're protection against your own future mistakes.

> 🔑 **Key takeaway:** Good OOP means each class has one clear job and defends its own data. When you get this right, bugs become easier to find because there's only one place they can live.

---

### 🎨 3. Architecture Patterns Exist for a Reason

The **Inventory Management System** was where I first deliberately applied **design patterns** instead of just writing whatever worked.

- **MVC architecture** — separating the `Product` model, `InventoryManager` logic, and `MainFrame` view meant I could change how things looked without touching how they worked.
- **Singleton pattern** — `InventoryManager.getInstance()` ensured every part of the UI was always looking at the same data. Before I understood this, I had mysterious bugs where one panel showed stale data.
- **Custom `AbstractTableModel`** — instead of manually syncing a list with a JTable, I learned to let the table *ask* the model for data. The `fireTableDataChanged()` pattern changed how I think about UI updates entirely.
- **Conditional rendering** — subclassing `DefaultTableCellRenderer` to highlight low-stock rows taught me that UI logic can be cleanly encapsulated too, not just business logic.

> 🔑 **Key takeaway:** Patterns aren't rules to follow — they're solutions to problems that smart people already solved. Once you understand the *problem* they solve, using them feels natural rather than forced.

---

### 🗄️ 4. Real Applications Need Real Persistence

The **Library Management System** was my first encounter with actual databases in a project I built myself.

- **JDBC and SQLite** — writing raw SQL through Java taught me what ORMs are *abstracting*. You appreciate the tool more when you've done it without it.
- **DAO pattern** — keeping all SQL inside `BookDAO`, `UserDAO`, and `BorrowRecordDAO` meant the rest of the code never cared *how* data was stored, only *what* it got back. Swapping SQLite for another database would touch zero business logic.
- **Layered architecture** — Main → Service → DAO → Database. Each layer only talks to the one below it. This felt like overkill for a console app until I had to debug something and realised exactly where to look instantly.
- **Google Books API integration** — making a real HTTP call, parsing a JSON response, and saving the result to a local database felt like a huge step up. It connected what I'd learned about Java to how the real web actually works.
- **`java.time.LocalDate` for fine calculation** — working with dates properly (not string comparisons!) taught me that Java's modern time API is genuinely pleasant to use.

> 🔑 **Key takeaway:** The DAO pattern isn't about being clever — it's about keeping your database concerns in one place so the rest of your code can stay clean and testable.

---

### 🌐 5. Networking Is Just Threads + Streams + Timing

The **Real-Time Chat Application** was the most challenging and most rewarding project. It threw every concept I'd learned at me simultaneously.

#### 🔌 TCP Sockets
- A `ServerSocket` opens a door. `accept()` waits for someone to knock. `new Socket()` on the client side is the knock. Simple mental model, deep implications.
- Every message I send is just bytes over a stream. The *protocol* — what those bytes mean — is something I designed myself.

#### 🧵 Concurrency
- The thread-per-client model clicked for me when I realised: if one client's thread blocks reading, it shouldn't freeze everyone else's chat. Each client deserves their own world.
- `ConcurrentHashMap` vs `HashMap` — I hit an actual race condition before I understood why the concurrent version existed. Fixing it made the concept stick permanently.
- `volatile` keyword — the most subtle bug I fixed all internship. Without it, one thread updated `currentRoom` and another thread read a stale cached value. Messages leaked into the wrong room. One word fixed it.

#### 🎭 JavaFX Threading
- `Platform.runLater()` was confusing until I understood the rule: **only one thread is allowed to touch the UI**. Everything else must ask politely and get in line.
- The reader thread and writer thread pattern — running them independently so typing a message never freezes receiving one — was the first time I felt like I truly understood concurrent I/O.

#### 📦 Protocol Design
- Designing the `Message` class as a `Serializable` object was a deliberate choice. Instead of parsing strings like `"TYPE|SENDER|ROOM|CONTENT"`, I let Java's serialization handle it. The trade-off: both sides must share the same class. That's why `Message` lives in `common/`.
- The file transfer protocol — storing `fileName` + `fileData` in the same object and letting serialization handle the byte array — taught me that sometimes the simplest design is the right one.

> 🔑 **Key takeaway:** Concurrency bugs are the hardest to find because they don't happen every time. The solution is to design your shared state carefully *upfront*, not patch it after the bug appears.

---

## 🛠️ Skills I Picked Up Along the Way

### Tools & Practices

- **Git & Conventional Commits** — writing `feat:`, `fix:`, `chore:`, `docs:` prefixes made my commit history readable as a story, not a log.
- **Maven** — managing dependencies, compiling, and running with a single command. Never manually adding JARs again.
- **Debugging mindset** — I learned to *read* stack traces instead of panic at them. The error message usually tells you exactly what went wrong and where.
- **Self-auditing** — asking "what happens if two users do this at the same time?" or "what happens when the client crashes?" before the bug happens, not after.

### Java-Specific

| Concept | Where I learned it |
|---|---|
| `Serializable` interface | Chat app — Message objects |
| `volatile` keyword | Chat app — currentRoom field |
| `ConcurrentHashMap` | Chat app — user registry |
| `AbstractTableModel` | Inventory app — table binding |
| `DefaultTableCellRenderer` | Inventory app — low stock highlighting |
| `Platform.runLater()` | Chat app — JavaFX thread model |
| `java.time.LocalDate` | Library app — fine calculation |
| JDBC + DAO pattern | Library app — SQLite persistence |
| HTTP + JSON parsing | Library app — Google Books API |
| Shunting Yard algorithm | Calculator app — expression parsing |

---

## 🪞 Honest Reflections

**What was harder than expected:**
The `volatile` bug in the chat app. I spent a long time thinking the broadcast logic was wrong before I understood it was a CPU caching issue. That one bug taught me more about concurrency than anything I'd read.

**What was easier than expected:**
JavaFX once I understood the layout system. `BorderPane`, `VBox`, `HBox` are intuitive once you stop fighting them. The CSS support is a genuine surprise — it felt like web development.

**What I wish I'd known at the start:**
That architecture decisions made in Week 1 compound. A class that does too many things in Week 1 becomes a class you're afraid to touch by Week 4. Design for clarity from day one.

**What I'm most proud of:**
Building the file transfer protocol from scratch — not using a library, not copying an example, but actually thinking through the metadata + payload design and implementing it. When it worked for the first time, it felt real.

---

## 🚀 What's Next

The chat app has a list of known improvements I want to come back to:

- [ ] Make server IP configurable from the login screen
- [ ] Chunked file streaming for files larger than 5MB
- [ ] Message persistence — save chat history to a database
- [ ] User authentication — usernames with passwords
- [ ] Dynamic room creation

Beyond this internship, these projects have pointed me clearly toward **backend systems and networked applications** as the area I want to go deeper in. The combination of concurrency, protocol design, and system architecture feels like exactly the kind of problem I want to keep solving.

---

## 🙏 Thanks

To **Mr. Bharatwaaj** for the technical guidance, especially during the chat app week when things got genuinely hard.

To **Mr. Aakash** for the coordination and patience, especially during exam season when balancing everything was a real challenge.

To **ShadowFox** for the project-based structure — building five things in four weeks taught me more than a semester of theory.

---

*Made with a lot of `System.out.println` debugging and eventually replaced with actual understanding.* 😄

**— Mrittika Kundu, June 2026**
