package com.chatapp.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;

import com.chatapp.common.Message;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ChatWindow {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5 MB

    private String username;
    private String currentRoom;

    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    private ListView<String> messageList;
    private ListView<String> userList;
    private Label roomHeaderLabel;
    private TextField messageField;
    private Stage stage;

    public ChatWindow(String username, String room) {
        this.username = username;
        this.currentRoom = room;
    }

    public void show(Stage stage) {

        this.stage = stage;

        // room header bar 
        roomHeaderLabel = new Label("# " + currentRoom);
        roomHeaderLabel.getStyleClass().add("room-header");

        // message area 
        messageList = new ListView<>();
        messageList.getStyleClass().add("message-list");
        VBox.setVgrow(messageList, Priority.ALWAYS);

        // input bar 
        messageField = new TextField();
        messageField.setPromptText("Type a message...");
        messageField.getStyleClass().add("input-field");
        HBox.setHgrow(messageField, Priority.ALWAYS);

        Button sendButton = new Button("Send");
        sendButton.getStyleClass().add("primary-button");

        Button fileButton = new Button("📎");
        fileButton.getStyleClass().add("file-button");

        HBox inputBar = new HBox(8, messageField, fileButton, sendButton);
        inputBar.getStyleClass().add("input-bar");
        inputBar.setAlignment(Pos.CENTER);

        // right side: header + messages + input 
        VBox chatArea = new VBox(0, roomHeaderLabel, messageList, inputBar);
        chatArea.getStyleClass().add("chat-area");
        VBox.setVgrow(messageList, Priority.ALWAYS);

        // sidebar 
        Label roomsLabel = new Label("ROOMS");
        roomsLabel.getStyleClass().add("sidebar-section-label");

        Button generalBtn = new Button("# General");
        Button techBtn = new Button("# Tech");
        Button randomBtn  = new Button("# Random");

        generalBtn.getStyleClass().add("room-button");
        techBtn.getStyleClass().add("room-button");
        randomBtn.getStyleClass().add("room-button");

        generalBtn.setMaxWidth(Double.MAX_VALUE);
        techBtn.setMaxWidth(Double.MAX_VALUE);
        randomBtn.setMaxWidth(Double.MAX_VALUE);

        generalBtn.setOnAction(e -> switchRoom("General"));
        techBtn.setOnAction(e -> switchRoom("Tech"));
        randomBtn.setOnAction(e -> switchRoom("Random"));

        Label onlineLabel = new Label("ONLINE");
        onlineLabel.getStyleClass().add("sidebar-section-label");

        userList = new ListView<>();
        userList.getStyleClass().add("user-list");
        VBox.setVgrow(userList, Priority.ALWAYS);

        VBox sidebar = new VBox(8,
                roomsLabel,
                generalBtn, techBtn, randomBtn,
                onlineLabel,
                userList);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPrefWidth(160);

        // root layout 
        BorderPane root = new BorderPane();
        root.setLeft(sidebar);
        root.setCenter(chatArea);
        root.getStyleClass().add("root-pane");

        // buttons and field action 
        sendButton.setOnAction(e -> handleSend());
        messageField.setOnAction(e -> handleSend());
        fileButton.setOnAction(e -> handleFileSelect());

        // scene and stage 
        Scene scene = new Scene(root, 800, 550);
        scene.getStylesheets().add(
                getClass().getResource("/com/chatapp/client/chat.css")
                          .toExternalForm()
        );

        stage.setTitle("Java Chat - " + username);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMinWidth(600);
        stage.setMinHeight(400);

        stage.setOnCloseRequest(e -> disconnect());

        connectToServer();
    }

    private void connectToServer() {
        new Thread(() -> {
            try {
                socket = new Socket(SERVER_ADDRESS, SERVER_PORT);

                output = new ObjectOutputStream(socket.getOutputStream());
                input  = new ObjectInputStream(socket.getInputStream());

                Message joinMsg = new Message("JOIN", username, currentRoom, "");
                output.writeObject(joinMsg);
                output.flush();

                listenForMessages();

            } catch (IOException e) {
                Platform.runLater(() ->
                    messageList.getItems().add(
                        "Could not connect to server: " + e.getMessage()
                    )
                );
            }
        }).start();
    }

    private void listenForMessages() {
        try {
            while (true) {
                Message message = (Message) input.readObject();
                handleIncomingMessage(message);
            }
        } catch (IOException | ClassNotFoundException e) {
            Platform.runLater(() ->
                messageList.getItems().add("Disconnected from server.")
            );
        }
    }

    private void handleIncomingMessage(Message message) {
        Platform.runLater(() -> {
            String type    = message.getType();
            String sender  = message.getSender();
            String content = message.getContent();

            if (type.equals("USER_LIST")) {
                userList.getItems().clear();
                if (!content.isEmpty()) {
                    String[] users = content.split(",");
                    for (String user : users) {
                        userList.getItems().add("● " + user);
                    }
                }

            } else if (type.equals("JOIN") || type.equals("LEAVE")) {
                messageList.getItems().add("⚡ " + content);
                messageList.scrollTo(messageList.getItems().size() - 1);

            } else if (type.equals("PRIVATE")) {
                String recipient = message.getRoom();
                String dmLine;
                if (sender.equals(username)) {
                    dmLine = "🔒 [DM to " + recipient + "] " + content;
                } else {
                    dmLine = "🔒 [DM from " + sender + "] " + content;
                }
                messageList.getItems().add(dmLine);
                messageList.scrollTo(messageList.getItems().size() - 1);

            } else if (type.equals("FILE")) {
                String fileName = message.getFileName();
                String displayLine = "📎 " + sender + " sent a file: " + fileName;
                int index = messageList.getItems().size();
                messageList.getItems().add(displayLine + "  [Click to Save]");
                messageList.scrollTo(index);

                messageList.setOnMouseClicked(event -> {
                    int selected = messageList.getSelectionModel()
                                            .getSelectedIndex();
                    if (selected == index) {
                        saveFileWithChooser(message, stage);
                    }
                });

            } else if (type.equals("PRIVATE_FILE")) {
                String fileName  = message.getFileName();
                String recipient = message.getRoom();
                String label     = sender.equals(username)
                    ? "📎 [File to "   + recipient + "] " + fileName
                    : "📎 [File from " + sender    + "] " + fileName;
                int index = messageList.getItems().size();
                messageList.getItems().add(label + "  [Click to Save]");
                messageList.scrollTo(index);

                messageList.setOnMouseClicked(event -> {
                    int selected = messageList.getSelectionModel()
                                            .getSelectedIndex();
                    if (selected == index) {
                        saveFileWithChooser(message, stage);
                    }
                });
            } else if (type.equals("SERVER")) {
                messageList.getItems().add("⚠ " + content);
                messageList.scrollTo(messageList.getItems().size() - 1);

            } else {
                messageList.getItems().add(sender + ": " + content);
                messageList.scrollTo(messageList.getItems().size() - 1);
            }
        });
    }

    private void handleFileSelect() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a file to send");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter(
                "Allowed Files", "*.png", "*.jpg", "*.jpeg",
                                "*.gif", "*.txt", "*.pdf")
        );

        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile == null) return;

        if (selectedFile.length() > MAX_FILE_SIZE) {
            messageList.getItems().add(
                "⚠ File too large. Maximum size is 5MB.");
            return;
        }

        try {
            byte[] fileData = Files.readAllBytes(selectedFile.toPath());
            String fileName = selectedFile.getName();
            String prefix = messageField.getText().trim();

            if (prefix.startsWith("@")) {
                String recipient = prefix.substring(1).trim();
                if (recipient.isEmpty()) {
                    messageList.getItems().add(
                        "⚠ Usage: type @username then click 📎");
                    return;
                }
                sendMessage(new Message(
                    "PRIVATE_FILE", username, recipient,
                    fileName, fileData));
            } else {
                sendMessage(new Message(
                    "FILE", username, currentRoom,
                    fileName, fileData));
            }

            messageField.clear();

        } catch (IOException e) {
            messageList.getItems().add("⚠ Could not read file.");
        }
    }

    private void saveFileWithChooser(Message message, Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.setInitialFileName(message.getFileName());

        File destination = fileChooser.showSaveDialog(stage);
        if (destination == null) return;

        try (FileOutputStream fos = new FileOutputStream(destination)) {
            fos.write(message.getFileData());
            messageList.getItems().add(
                "✔ Saved: " + destination.getAbsolutePath());
            messageList.scrollTo(
                messageList.getItems().size() - 1);
        } catch (IOException e) {
            messageList.getItems().add(
                "⚠ Could not save file: " + e.getMessage());
        }
    }

    private void handleSend() {
        String text = messageField.getText().trim();
        if (text.isEmpty()) return;

        if (text.startsWith("@")) {
            String[] parts = text.split(" ", 2);
            if (parts.length < 2) {
                messageList.getItems().add("⚠ Usage: @username message");
                return;
            }
            String recipient = parts[0].substring(1);
            String content = parts[1];
            sendMessage(new Message("PRIVATE", username, recipient, content));

        } else {
            sendMessage(new Message("MESSAGE", username, currentRoom, text));
        }

        messageField.clear();
    }

    private void switchRoom(String newRoom) {
        if (newRoom.equals(currentRoom)) return;
        currentRoom = newRoom;
        roomHeaderLabel.setText("# " + currentRoom);
        messageList.getItems().add("-- Switched to " + currentRoom + " --");
        sendMessage(new Message("JOIN", username, currentRoom, ""));
    }

    private void sendMessage(Message message) {
        new Thread(() -> {
            try {
                output.writeObject(message);
                output.flush();
            } catch (IOException e) {
                Platform.runLater(() ->
                    messageList.getItems().add("⚠ Failed to send message.")
                );
            }
        }).start();
    }

    private void disconnect() {
        try {
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.out.println("Error on disconnect: " + e.getMessage());
        }
    }
}