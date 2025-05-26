package org.example;

import javax.swing.*;
import org.json.simple.JSONObject;

public class Register {
    private static final int MAX_ATTEMPTS = 3;

    public static User registerUser() {
        // Get username with validation
        String username = getValidInput(
                "Enter Username (maximum 5 characters, must contain '_'):",
                "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.",
                input -> {
                    input = input.trim();
                    if (!checkUserName(input)) return false;
                    if (UserStorage.usernameExists(input)) {
                        JOptionPane.showMessageDialog(null, "Username already exists. Please choose a different one.");
                        return false;
                    }
                    JOptionPane.showMessageDialog(null,"Username successfully captured.");
                    return true;
                },
                MAX_ATTEMPTS
        );
        if (username == null) return null;

        // Get personal details
        String firstName = JOptionPane.showInputDialog("Enter First Name:");
        if (firstName == null) return null;

        String lastName = JOptionPane.showInputDialog("Enter Last Name:");
        if (lastName == null) return null;

        // Get South African cell number with validation (DeepSeek assistance)
        String cellNumber = getValidInput(
                "Enter South African Cell Number (+27 followed by 9 digits):",
                "Cell phone number incorrectly formatted or does not contain international code.",
                input -> checkSACellNumber(input.trim()),
                2
        );
        JOptionPane.showMessageDialog(null,"Cell phone number successfully added.");
        if (cellNumber == null) return null;

        // Get password with validation
        String password = getValidInput("Enter Password (min 8 chars, 1 uppercase, 1 number, 1 special char):", "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.",
                Register::checkPasswordComplexity,
                MAX_ATTEMPTS
        );
        JOptionPane.showMessageDialog(null,"Password successfully captured.");
        if (password == null) return null;

        // Create and store user
        User newUser = new User(username, password, firstName, lastName, cellNumber);
        UserStorage.saveUser(newUser);

        JOptionPane.showMessageDialog(null,
                "Registration Successful!\n" +
                        "Username: " + username + "\n" +
                        "Name: " + firstName + " " + lastName);

        return newUser;
    }

    // Helper method for validated input
    private static String getValidInput(String prompt, String errorMessage,
                                        Validator validator, int maxAttempts) {
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            String input = JOptionPane.showInputDialog("Attempt " + attempt + " of " + maxAttempts + "\n" + prompt);
            if (input == null) return null;
            input = input.trim();

            if (validator.validate(input)) {
                return input;
            }

            if (attempt < maxAttempts) {
                JOptionPane.showMessageDialog(null, errorMessage);
            }
        }
        JOptionPane.showMessageDialog(null, "Maximum attempts reached. Registration cancelled.");
        return null;
    }

    // Validation methods
    public static boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public static boolean checkPasswordComplexity(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[!@#$%^&()].*");
    }

    public static boolean checkSACellNumber(String cellNumber) {
        return cellNumber.matches("^\\+27\\d{9}$");
    }

    // Functional interface for validation
    private interface Validator {
        boolean validate(String input);
    }
    //secure password (chatGpt)
    private static String promptPassword(String message) {
        JPasswordField pf = new JPasswordField();
        int result = JOptionPane.showConfirmDialog(null, pf, message, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            return new String(pf.getPassword());
        }
        return null;
    }
}
