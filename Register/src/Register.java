import javax.swing.*;

public class Register {

            public static String registeredUsername = "";
            public static String registeredPassword = "";
            public static String registeredFirstName = "";
            public static String registeredLastName = "";
            public static String registeredCellNr = "";

            public static void main(String[] args) {

                String[] options = {"Register", "Login"};
                int button = JOptionPane.showOptionDialog(null, "Choose an option", "Registration/Login System",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                if (button == 0) {
                    registerUser();
                } else if (button == 1) {
                    Login.returnLoginStatus(registeredUsername, registeredPassword, registeredFirstName, registeredLastName, registeredCellNr);
                } else {
                    JOptionPane.showMessageDialog(null, "No option selected.");
                }
            }

    public static void registerUser() {
        String username = "";
        boolean correctUsername = false;


        for (int name = 0; name < 3; name++) {
            username = JOptionPane.showInputDialog(null, "Enter Username (Max 5 characters, include an underscore):");


            if (checkUserName(username)) {
                correctUsername = true;
                break;
            } else {
                JOptionPane.showMessageDialog(null,
                        "Username is not correctly formatted. Please ensure that your username contains an underscore and is no more than 5 characters in length.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (!correctUsername) {
            JOptionPane.showMessageDialog(null, "Exceeded maximum attempts for username input. Registration failed.",
                    "Registration Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit registration process if username is invalid after 3 attempts
        }

        String firstName = JOptionPane.showInputDialog(null, "Please enter First Name:");
        String lastName = JOptionPane.showInputDialog(null, "Please enter Surname Name:");
        String cellNr = "";//chatgpt assistance
        int cellAttempts = 0;

        while (cellAttempts < 2) {
            cellNr = JOptionPane.showInputDialog(null, "Enter Cell Phone Number (10 digits):");

            if (cellNr != null && cellNr.matches("\\d{10}")) {
                break; // valid number, exit loop
            } else {
                cellAttempts++;
                if (cellAttempts < 2) {
                    JOptionPane.showMessageDialog(null, "Invalid number. Please enter exactly 10 digits. Attempt " + cellAttempts + " of 2.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        assert cellNr != null;
        if (!cellNr.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(null, "Invalid cell number entered twice. Registration cancelled.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // exit registration
        }

        String password = "";
        boolean correctPassword = false;


        for (int n = 0; n < 3; n++) {
            password = JOptionPane.showInputDialog(null, "Enter Password (Min 8 characters, 1 uppercase, 1 number, 1 special character):");


            if (checkPasswordComplexity(password)) {
                correctPassword = true;
                break;
            } else {
                JOptionPane.showMessageDialog(null,
                        "Password is not correctly formatted. Ensure it contains at least 8 characters, a number, and a special character.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (!correctPassword) {
            JOptionPane.showMessageDialog(null, "Exceeded maximum attempts for password input. Registration failed.",
                    "Registration Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String confirmPassword = JOptionPane.showInputDialog(null, "Confirm Password:");

        if (password.equals(confirmPassword)) {
            registeredUsername = username;
            registeredPassword = password;
            registeredFirstName = firstName;
            registeredLastName = lastName;
            registeredCellNr = cellNr;
            JOptionPane.showMessageDialog(null, "Registration Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Passwords do not match. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public static boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }


    public static boolean checkPasswordComplexity(String password) {
        // Password should have at least 8 characters, contain a capital letter, a number, and a special character
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") && // Contains at least one uppercase letter
                password.matches(".*\\d.*") && // Contains at least one number
                password.matches(".*[!@#$%^&(),.?\":{}|<>].*"); // Contains at least one special character
    }

        }

