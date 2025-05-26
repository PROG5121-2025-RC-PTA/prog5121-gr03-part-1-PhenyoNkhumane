package org.example;

import javax.swing.*;

public class ChatApp {
    public static void main(String[] args) {
        // Initialize storage files
        UserStorage.initialize();
        MessageStorage.initialize();

        while (true) {
            String[] options = {"Register", "Login", "Exit"};
            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Welcome to QuickChat",
                    "Main Menu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]
            );
            if (choice == JOptionPane.CLOSED_OPTION) {
                JOptionPane.showMessageDialog(null, "Goodbye!");
                System.exit(0);
            }

            switch (choice) {
                case 0 -> handleRegistration();
                case 1 -> handleLogin();
                case 2 -> {
                    JOptionPane.showMessageDialog(null, "Thank you for using QuickChat!");
                    System.exit(0);
                }
                default -> JOptionPane.showMessageDialog(null, "Invalid selection");
            }
        }
    }

    private static void handleRegistration() {
        User newUser = Register.registerUser();
        if (newUser != null) {
            JOptionPane.showMessageDialog(null,
                    "Registration successful!\n" +
                            "Username: " + newUser.getUsername() + "\n" +
                            "Name: " + newUser.getFirstName() + " " + newUser.getLastName());
        }
    }

    private static void handleLogin() {
        if (UserStorage.getUserCount() == 0) {
            JOptionPane.showMessageDialog(null,
                    "No users registered yet. Please register first.");
            return;
        }

        User loggedInUser = Login.handleLogin();
        if (loggedInUser != null) {
            MessageService.runQuickChat(loggedInUser);
        }
    }
}