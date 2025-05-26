package org.example;

import javax.swing.*;

public class Login {
    private static final int MAX_ATTEMPTS = 3;

    public static User handleLogin() {
        if (UserStorage.getUserCount() == 0) {
            JOptionPane.showMessageDialog(null,
                    "No users registered yet. Please register first.");
            return null;
        }

        String username = JOptionPane.showInputDialog("Enter your username:");
        if (username == null || username.trim().isEmpty()) {
            return null; // User cancelled or empty
        }

        username = username.trim(); // Normalize
        User user = UserStorage.getUser(username);

        if (user == null) {
            JOptionPane.showMessageDialog(null, "Username or password incorrect, please try again.");
            return null;
        }

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            JPasswordField pf = new JPasswordField();
            int result = JOptionPane.showConfirmDialog(null, pf,
                    "Password attempt " + attempt + " of " + MAX_ATTEMPTS + "\nEnter password for " + username + ":",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result != JOptionPane.OK_OPTION) return null;

            String password = new String(pf.getPassword());

            if (user.getPassword().equals(password)) {
                JOptionPane.showMessageDialog(null,
                        "Welcome " + user.getFirstName() + " " + user.getLastName() + "it is great to see you again.");
                return user;
            }

            if (attempt < MAX_ATTEMPTS) {
                JOptionPane.showMessageDialog(null, "Incorrect password. Please try again.");
            }
        }

        JOptionPane.showMessageDialog(null,
                "Maximum attempts reached. Please try again later.");
        return null;
    }
}
