import javax.swing.*;

public class Login {
    public static boolean loginUser(String username, String password, String registeredUsername, String registeredPassword) {
        return username.equals(registeredUsername) && password.equals(registeredPassword);
    }

    public static void returnLoginStatus(String registeredUsername, String registeredPassword,
                                         String registeredFirstName, String registeredLastName,
                                         String registeredCellNr) {
        int attempts = 0;
        boolean isLoggedIn = false;

        while (attempts < 3 && !isLoggedIn) {
            String username = JOptionPane.showInputDialog(null, "Enter Username:");
            String password = JOptionPane.showInputDialog(null, "Enter Password:");

            if (loginUser(username, password, registeredUsername, registeredPassword)) {
                JOptionPane.showMessageDialog(null,
                        "Welcome " + registeredFirstName + ", " + registeredLastName +
                                "\nCell: " + registeredCellNr +
                                "\nIt is great to see you again.",
                        "Login Success", JOptionPane.INFORMATION_MESSAGE);
                isLoggedIn = true;
            } else {
                attempts++;
                if (attempts < 3) {
                    JOptionPane.showMessageDialog(null, "Username or password incorrect, please try again. Attempt " + attempts + " of 3.",
                            "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        if (!isLoggedIn) {
            JOptionPane.showMessageDialog(null, "You have exceeded the maximum login attempts. Please try again later.",
                    "Login Locked", JOptionPane.ERROR_MESSAGE);
        }
    }
}