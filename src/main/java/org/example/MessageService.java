package org.example;

import javax.swing.*;
import java.util.Date;
import java.util.List;

public class MessageService {
    private static final int MAX_MESSAGE_LENGTH = 250;

    public static void runQuickChat(User currentUser) {
        // Load all messages once at start
        List<Message> allMessages = MessageStorage.getUserMessages(currentUser.getCellNumber());

        while (true) {
            String[] options = {"Send Message", "View My Messages", "Search Messages", "Delete Message", "Logout"};
            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Welcome to QuickChat, " + currentUser.getFirstName() + "!\n" +
                            "Your number: " + currentUser.getCellNumber(),
                    "QuickChat Menu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            switch (choice) {
                case 0 -> { // Send Message
                    Message newMsg = sendNewMessage(currentUser);
                    if (newMsg != null) {
                        MessageStorage.saveMessage(newMsg);
                        allMessages.add(newMsg);
                    }
                }
                case 1 -> showMessages(allMessages, currentUser);
                case 2 -> searchMessages(allMessages);
                case 3 -> {
                    allMessages = deleteMessage(allMessages, currentUser);
                }
                case 4 -> {
                    // No extra saving needed here because messages saved immediately on add/delete
                    return;
                }
                default -> JOptionPane.showMessageDialog(null, "Invalid option");
            }
        }
    }

    private static Message sendNewMessage(User sender) {
        String recipient = JOptionPane.showInputDialog("Enter recipient's SA number (+27XXXXXXXXX):");
        if (recipient == null || !recipient.matches("^\\+27\\d{9}$")) {
            JOptionPane.showMessageDialog(null, "Invalid SA number format");
            return null;
        }

        String content = JOptionPane.showInputDialog("Enter your message (max " + MAX_MESSAGE_LENGTH + " chars):");
        if (content == null || content.length() > MAX_MESSAGE_LENGTH) {
            JOptionPane.showMessageDialog(null, "Message too long or empty");
            return null;
        }

        String messageId = generateMessageId();
        String hash = generateMessageHash(messageId, content);

        Message newMessage = new Message(
                messageId,
                sender.getCellNumber(),
                recipient,
                content,
                hash,
                new Date()
        );

        JOptionPane.showMessageDialog(null, "Message sent!\nID: " + messageId + "\nHash: " + hash);
        return newMessage;
    }

    private static String generateMessageId() {
        return java.util.UUID.randomUUID().toString().substring(0, 10);
    }

    private static String generateMessageHash(String messageId, String content) {
        String[] words = content.split(" ");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
        return (messageId.substring(0, 2) + ":" + firstWord + lastWord).toUpperCase();
    }

    private static void showMessages(List<Message> messages, User currentUser) {
        StringBuilder sb = new StringBuilder();
        boolean found = false;

        for (Message msg : messages) {
            if (msg.getSender().equals(currentUser.getCellNumber()) ||
                    msg.getRecipient().equals(currentUser.getCellNumber())) {
                sb.append(formatMessage(msg)).append("\n\n");
                found = true;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(null, "No messages found");
            return;
        }

        JOptionPane.showMessageDialog(null, new JScrollPane(new JTextArea(sb.toString())));
    }

    private static String formatMessage(Message msg) {
        return String.format(
                "ID: %s\nHash: %s\nFrom: %s\nTo: %s\nTime: %tc\n\n%s",
                msg.getId(),
                msg.getHash(),
                msg.getSender(),
                msg.getRecipient(),
                msg.getTimestamp(),
                msg.getContent()
        );
    }

    private static void searchMessages(List<Message> messages) {
        String[] options = {"Message ID", "Recipient", "Content"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Search messages by:",
                "Search Options",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == JOptionPane.CLOSED_OPTION) return;

        String searchTerm = JOptionPane.showInputDialog("Enter search term:");
        if (searchTerm == null || searchTerm.trim().isEmpty()) return;

        String lowerTerm = searchTerm.toLowerCase();
        StringBuilder sb = new StringBuilder("Search Results:\n\n");
        boolean found = false;

        for (Message msg : messages) {
            boolean match = switch (choice) {
                case 0 -> msg.getId().contains(searchTerm);
                case 1 -> msg.getRecipient().contains(searchTerm);
                case 2 -> msg.getContent().toLowerCase().contains(lowerTerm);
                default -> false;
            };

            if (match) {
                sb.append(formatMessage(msg)).append("\n\n");
                found = true;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(null, "No matching messages found.");
            return;
        }

        JOptionPane.showMessageDialog(null, new JScrollPane(new JTextArea(sb.toString())));
    }

    private static List<Message> deleteMessage(List<Message> messages, User currentUser) {
        String messageId = JOptionPane.showInputDialog("Enter your message ID to delete:");
        if (messageId == null || messageId.trim().isEmpty()) return messages;

        boolean deleted = MessageStorage.deleteMessageById(messageId, currentUser.getCellNumber());
        if (deleted) {
            JOptionPane.showMessageDialog(null, "Message deleted.");
            // Reload messages from storage after deletion
            return MessageStorage.getUserMessages(currentUser.getCellNumber());
        } else {
            JOptionPane.showMessageDialog(null, "Could not delete message. Make sure you are the sender and ID is correct.");
            return messages;
        }
    }
}
