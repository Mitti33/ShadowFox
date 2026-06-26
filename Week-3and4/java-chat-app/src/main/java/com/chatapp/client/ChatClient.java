package com.chatapp.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import com.chatapp.common.Message;

public class ChatClient {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String username;
    private String currentRoom;

    public ChatClient(String username, String room) {
        this.username    = username;
        this.currentRoom = room;
    }

    public void start() {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            System.out.println("Connected to server!");

            output = new ObjectOutputStream(socket.getOutputStream());
            input  = new ObjectInputStream(socket.getInputStream());

            Message joinMessage = new Message("JOIN", username, currentRoom, "");
            output.writeObject(joinMessage);
            output.flush();

            Thread readerThread = new Thread(this::readMessages);
            readerThread.setDaemon(true);
            readerThread.start();

            writeMessages();

        } catch (IOException e) {
            System.out.println("Could not connect to server: " + e.getMessage());
        }
    }

    private void readMessages() {
        try {
            while (true) {
                Message message = (Message) input.readObject();
                System.out.println(message.toString());
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Disconnected from server.");
        }
    }

    private void writeMessages() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type your messages below:");
        System.out.println("Use @username message for private messages");
        System.out.println("Use /join roomname to switch rooms");

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) continue;

            if (input.startsWith("@")) {
                handlePrivateMessage(input);
            } else if (input.startsWith("/join ")) {
                handleRoomSwitch(input);
            } else {
                sendMessage(new Message("MESSAGE", username, currentRoom, input));
            }
        }
    }

    private void handlePrivateMessage(String input) {
        String[] parts = input.split(" ", 2);
        if (parts.length < 2) {
            System.out.println("Usage: @username message");
            return;
        }
        String recipient = parts[0].substring(1);
        String content   = parts[1];
        sendMessage(new Message("PRIVATE", username, recipient, content));
    }

    private void handleRoomSwitch(String input) {
        String newRoom = input.substring(6).trim();
        if (!ChatClient.isValidRoom(newRoom)) {
            System.out.println("Available rooms: General, Tech, Random");
            return;
        }
        currentRoom = newRoom;
        sendMessage(new Message("JOIN", username, currentRoom, ""));
        System.out.println("Switched to room: " + currentRoom);
    }

    private void sendMessage(Message message) {
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
            System.out.println("Failed to send message: " + e.getMessage());
        }
    }

    public static boolean isValidRoom(String room) {
        return room.equals("General") ||
               room.equals("Tech")    ||
               room.equals("Random");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your username: ");
        String username = scanner.nextLine().trim();

        System.out.println("Available rooms: General, Tech, Random");
        System.out.print("Enter room to join: ");
        String room = scanner.nextLine().trim();

        if (!isValidRoom(room)) {
            System.out.println("Invalid room. Joining General by default.");
            room = "General";
        }

        new ChatClient(username, room).start();
    }
}