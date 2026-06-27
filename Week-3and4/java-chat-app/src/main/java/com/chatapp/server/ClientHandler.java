package com.chatapp.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.chatapp.common.Message;

public class ClientHandler implements Runnable {

    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private String username;
    private volatile String currentRoom;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input  = new ObjectInputStream(socket.getInputStream());

            Message joinMessage = (Message) input.readObject();
            this.username    = joinMessage.getSender();
            this.currentRoom = joinMessage.getRoom();

            ChatServer.connectedUsers.put(username, this);

            System.out.println(username + " joined room: " + currentRoom);

            broadcastToRoom(new Message("JOIN", "Server", currentRoom,
                    username + " has joined " + currentRoom + "!"));

            broadcastUserList();

            while (true) {
                Message message = (Message) input.readObject();
                handleMessage(message);
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(username + " disconnected.");
        } finally {
            cleanup();
        }
    }

    private void handleMessage(Message message) {
        if (message.getType().equals("MESSAGE")) {
            broadcastToRoom(message);

        } else if (message.getType().equals("PRIVATE")) {
            sendPrivateMessage(message);

        } else if (message.getType().equals("JOIN")) {
            String oldRoom   = this.currentRoom;
            this.currentRoom = message.getRoom();

            broadcastToRoom(new Message("LEAVE", "Server", oldRoom,
                    username + " has left " + oldRoom + "."));

            broadcastToRoom(new Message("JOIN", "Server", this.currentRoom,
                    username + " has joined " + this.currentRoom + "!"));
        }
    }

    private void broadcastToRoom(Message message) {
        for (ClientHandler user : ChatServer.connectedUsers.values()) {
            if (user.currentRoom.equals(message.getRoom())) {
                user.sendMessage(message);
            }
        }
    }

    private void broadcastUserList() {
        String userListStr = String.join(",",
                ChatServer.connectedUsers.keySet());

        Message userListMsg = new Message(
                "USER_LIST", "Server", "all", userListStr);

        for (ClientHandler user : ChatServer.connectedUsers.values()) {
            user.sendMessage(userListMsg);
        }
    }

    private void sendPrivateMessage(Message message) {
        String recipient = message.getRoom();
        ClientHandler recipientHandler =
                ChatServer.connectedUsers.get(recipient);

        if (recipientHandler != null) {
            recipientHandler.sendMessage(message);
            if (!recipient.equals(this.username)) {
                sendMessage(message);
            }
        } else {
            sendMessage(new Message("SERVER", "Server", username,
                    "User '" + recipient + "' not found."));
        }
    }

    public void sendMessage(Message message) {
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
            System.out.println("Failed to send to " + username);
        }
    }

    private void cleanup() {
        try {
            ChatServer.connectedUsers.remove(username);

            broadcastToRoom(new Message("LEAVE", "Server", currentRoom,
                    username + " has left the room."));

            broadcastUserList();

            if (socket != null) socket.close();
        } catch (IOException e) {
            System.out.println("Error closing socket for " + username);
        }
    }
}