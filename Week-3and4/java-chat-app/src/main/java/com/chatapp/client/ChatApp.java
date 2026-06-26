package com.chatapp.client;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChatApp extends Application {

    @Override
    public void start(Stage primaryStage) {

        Label titleLabel = new Label("Java Chat App");
        titleLabel.getStyleClass().add("title-label");

        Label subtitleLabel = new Label("Connect and chat in real time");
        subtitleLabel.getStyleClass().add("subtitle-label");

        Label usernameLabel = new Label("Username");
        usernameLabel.getStyleClass().add("field-label");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username...");
        usernameField.getStyleClass().add("input-field");

        Label roomLabel = new Label("Select Room");
        roomLabel.getStyleClass().add("field-label");

        ComboBox<String> roomComboBox = new ComboBox<>();
        roomComboBox.getItems().addAll("General", "Tech", "Random");
        roomComboBox.setValue("General");
        roomComboBox.getStyleClass().add("combo-box");

        Label errorLabel = new Label("");
        errorLabel.getStyleClass().add("error-label");

        Button joinButton = new Button("Join Chat");
        joinButton.getStyleClass().add("primary-button");
        joinButton.setMaxWidth(Double.MAX_VALUE);

        joinButton.setOnAction(e -> {
            String username = usernameField.getText().trim();

            if (username.isEmpty()) {
                errorLabel.setText("Please enter a username.");
                return;
            }

            if (username.length() < 2) {
                errorLabel.setText("Username must be at least 2 characters.");
                return;
            }

            if (username.contains(" ")) {
                errorLabel.setText("Username cannot contain spaces.");
                return;
            }

            String room = roomComboBox.getValue();
            errorLabel.setText("");

            ChatWindow chatWindow = new ChatWindow(username, room);
            chatWindow.show(primaryStage);
        });

        usernameField.setOnAction(e -> joinButton.fire());

        VBox layout = new VBox(12);
        layout.getStyleClass().add("login-container");
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));
        layout.getChildren().addAll(
                titleLabel,
                subtitleLabel,
                usernameLabel,
                usernameField,
                roomLabel,
                roomComboBox,
                errorLabel,
                joinButton
        );

        Scene scene = new Scene(layout, 400, 420);
        scene.getStylesheets().add(
                getClass().getResource("/com/chatapp/client/chat.css")
                          .toExternalForm()
        );

        primaryStage.setTitle("Java Chat App");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}