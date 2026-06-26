package com.chatapp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServer {

    public static final int PORT = 12345;

    public static final ConcurrentHashMap<String, ClientHandler> connectedUsers
            = new ConcurrentHashMap<>();

    public static final ConcurrentHashMap<String, String[]> rooms
            = new ConcurrentHashMap<>();

    public static void main(String[] args) {

        initRooms();

        System.out.println("Chat server started on port " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New connection from: "
                        + clientSocket.getInetAddress().getHostAddress());

                ClientHandler handler = new ClientHandler(clientSocket);
                Thread thread = new Thread(handler);
                thread.start();
            }

        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    private static void initRooms() {
        rooms.put("General", new String[]{"General", "Main chat room"});
        rooms.put("Tech",    new String[]{"Tech",    "Technology discussion"});
        rooms.put("Random",  new String[]{"Random",  "Anything goes"});
    }
}