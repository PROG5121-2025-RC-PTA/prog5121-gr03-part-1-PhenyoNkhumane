package org.example;

public class User {
    private final String username;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String cellNumber;

    public User(String username, String password, String firstName,
                String lastName, String cellNumber) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cellNumber = cellNumber;
    }

    // Getters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getCellNumber() { return cellNumber; }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + username + ")";
    }
}