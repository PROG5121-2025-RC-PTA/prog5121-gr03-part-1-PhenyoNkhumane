package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class UserStorage {
    private static final String USERS_FILE = "users.json";

    public static void initialize() {
        try {
            new FileReader(USERS_FILE).close();
        } catch (Exception e) {
            saveAllUsers(new JSONArray()); // Create file if doesn't exist
        }
    }

    public static boolean saveUser(User user) {
        JSONArray users = loadAllUsers();

        // Check for existing username
        for (Object obj : users) {
            JSONObject userJson = (JSONObject) obj;
            if (userJson.get("username").equals(user.getUsername())) {
                // User already exists, maybe update? or just return false
                return false; // User exists, do not add duplicate
            }
        }

        JSONObject userJson = new JSONObject();
        userJson.put("username", user.getUsername());
        userJson.put("password", user.getPassword());
        userJson.put("firstName", user.getFirstName());
        userJson.put("lastName", user.getLastName());
        userJson.put("cellNumber", user.getCellNumber());
        users.add(userJson);
        saveAllUsers(users);
        return true; // User saved successfully
    }

    public static List<User> getAllUsers() {
        JSONArray users = loadAllUsers();
        List<User> userList = new ArrayList<>();
        for (Object obj : users) {
            JSONObject userJson = (JSONObject) obj;
            userList.add(new User(
                    (String) userJson.get("username"),
                    (String) userJson.get("password"),
                    (String) userJson.get("firstName"),
                    (String) userJson.get("lastName"),
                    (String) userJson.get("cellNumber")
            ));
        }
        return userList;
    }


    public static int getUserCount() {
        return loadAllUsers().size();
    }

    private static JSONArray loadAllUsers() {
        try (FileReader reader = new FileReader(USERS_FILE)) {
            return (JSONArray) new JSONParser().parse(reader);
        } catch (Exception e) {
            return new JSONArray();
        }
    }

    private static void saveAllUsers(JSONArray users) {
        try (FileWriter writer = new FileWriter(USERS_FILE)) {
            writer.write(users.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();  // Or log the error
        }
    }


    public static User getUser(String username) {
        JSONArray users = loadAllUsers();
        for (Object obj : users) {
            JSONObject userJson = (JSONObject) obj;
            if (userJson.get("username").equals(username)) {
                return new User(
                        (String) userJson.get("username"),
                        (String) userJson.get("password"),
                        (String) userJson.get("firstName"),
                        (String) userJson.get("lastName"),
                        (String) userJson.get("cellNumber")
                );
            }
        }
        return null;
    }
    public static boolean usernameExists(String username) {
        JSONArray users = loadAllUsers();
        for (Object obj : users) {
            JSONObject userJson = (JSONObject) obj;
            if (userJson.get("username").equals(username)) {
                return true;
            }
        }
        return false;
    }

}
