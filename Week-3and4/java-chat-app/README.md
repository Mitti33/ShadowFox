# Java Chat App 💬

> A real-time multi-client chat application built with Java Socket Programming and JavaFX.
Supports multiple users, chat rooms, private messaging, and file transfer over raw TCP sockets.

<p align = "center">
  <img width="400" height="450" alt="Screenshot 2026-06-27 204853" src="https://github.com/user-attachments/assets/2c5f7661-7dbd-496e-b273-471c38948e4d" />
  <img width="596" height="450" alt="Screenshot 2026-06-27 210103" src="https://github.com/user-attachments/assets/e8908d18-cc83-4e86-b974-250f32518589" />
  <br> <br> <br>
  <img width="900" height="500" alt="Screenshot 2026-06-28 114324" src="https://github.com/user-attachments/assets/a91fa3ca-7842-4769-8240-9690c7d94f2e" />
</p>

---

## Features

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

## Architecture

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

## File Transfer Protocol

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

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 23 |
| UI | JavaFX 21 |
| Networking | Java Sockets (TCP) |
| Concurrency | ConcurrentHashMap, volatile |
| Serialization | Java ObjectInputStream / ObjectOutputStream |
| Build tool | Apache Maven 3.9 |

---

## Project Structure

```
java-chat-app/
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   └── com/chatapp/
        │       ├── common/
        │       │   └── Message.java          # Shared serializable data model
        │       ├── server/
        │       │   ├── ChatServer.java       # Entry point, accepts connections
        │       │   └── ClientHandler.java    # Per-client thread handler
        │       └── client/
        │           ├── ChatApp.java          # JavaFX entry point, login screen
        │           └── ChatWindow.java       # Main chat UI and socket logic
        └── resources/
            └── com/chatapp/client/
                └── chat.css                  # Black and rose pink theme
```

---

## How to Run

### Prerequisites
- Java 23 or higher
- Apache Maven 3.9 or higher

### Step 1 — Clone the repository
```bash
git clone https://github.com/Mitti33/ShadowFox.git
cd ShadowFox/Week-3and4/java-chat-app
```

### Step 2 — Build the project
```bash
mvn clean compile
```

### Step 3 — Start the server
Open a terminal and run:
```bash
mvn exec:java
```
You should see:
```
Chat server started on port 12345
```

### Step 4 — Launch the client
Open a second terminal and run:
```bash
mvn javafx:run
```
A login window will appear. Enter a username and select a room.

### Step 5 — Connect multiple clients
Open more terminals and run `mvn javafx:run` in each.
Every client connects to the same server and can chat in real time.

### Connecting from another device (LAN)
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

## Usage Guide

| Action | How to do it |
|---|---|
| Send a message | Type in the input field and press Enter or click Send |
| Switch rooms | Click General, Tech, or Random in the sidebar |
| Private message | Type `@username message` and press Enter |
| Send a file to room | Click 📎 and select a file |
| Send a file privately | Type `@username` in the field, then click 📎 |
| Save a received file | Click the message row showing `[Click to Save]` |

---

## Engineering Concepts Demonstrated

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

## Known Limitations and Future Improvements

- Server IP is hardcoded in `ChatWindow.java` — future: make it
  configurable from the login screen
- File transfer loads entire file into memory — future: chunked
  streaming for files larger than 5MB
- No message persistence — history is lost when the server restarts
- No authentication — any username can be entered without a password
- Rooms are predefined — future: allow dynamic room creation

---

## Author

**Mitti** — ShadowFox Java Development Internship, June 2026

GitHub: [Mitti33](https://github.com/Mitti33)