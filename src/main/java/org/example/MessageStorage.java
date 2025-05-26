package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageStorage {
    private static final String MESSAGES_FILE = "messages.json";

    public static void initialize() {
        try {
            new FileReader(MESSAGES_FILE).close();
        } catch (Exception e) {
            saveAllMessages(new JSONArray());
        }
    }

    public static void saveMessage(Message message) {
        JSONArray messages = loadAllMessages();
        messages.add(messageToJson(message));
        saveAllMessages(messages);
    }

    public static List<Message> getUserMessages(String cellNumber) {
        List<Message> userMessages = new ArrayList<>();
        JSONArray messages = loadAllMessages();

        for (Object obj : messages) {
            JSONObject msgJson = (JSONObject) obj;
            if (msgJson.get("sender").equals(cellNumber) ||
                    msgJson.get("recipient").equals(cellNumber)) {
                userMessages.add(jsonToMessage(msgJson));
            }
        }
        return userMessages;
    }

    private static JSONObject messageToJson(Message message) {
        JSONObject json = new JSONObject();
        json.put("id", message.getId());
        json.put("sender", message.getSender());
        json.put("recipient", message.getRecipient());
        json.put("content", message.getContent());
        json.put("hash", message.getHash());
        json.put("timestamp", message.getTimestamp().getTime());
        return json;
    }

    private static Message jsonToMessage(JSONObject json) {
        return new Message(
                (String) json.get("id"),
                (String) json.get("sender"),
                (String) json.get("recipient"),
                (String) json.get("content"),
                (String) json.get("hash"),
                new Date((long) json.get("timestamp"))
        );
    }

    private static JSONArray loadAllMessages() {
        try (FileReader reader = new FileReader(MESSAGES_FILE)) {
            return (JSONArray) new JSONParser().parse(reader);
        } catch (Exception e) {
            return new JSONArray();
        }
    }

    private static void saveAllMessages(JSONArray messages) {
        try (FileWriter writer = new FileWriter(MESSAGES_FILE)) {
            writer.write(messages.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean deleteMessageById(String messageId, String currentUserCellNumber) {
        JSONArray messages = loadAllMessages();
        boolean deleted = false;

        for (int i = 0; i < messages.size(); i++) {
            JSONObject msgJson = (JSONObject) messages.get(i);
            if (msgJson.get("id").equals(messageId)) {
                // Only allow deletion if sender matches current user
                if (!msgJson.get("sender").equals(currentUserCellNumber)) {
                    return false; // not allowed
                }
                messages.remove(i);
                deleted = true;
                break;
            }
        }

        if (deleted) {
            saveAllMessages(messages);
        }

        return deleted;
    }

}