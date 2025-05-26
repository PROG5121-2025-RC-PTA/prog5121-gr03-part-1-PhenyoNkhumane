package org.example;

import java.util.Date;

public class Message {
    private final String id;
    private final String sender;     // sender's cell number
    private final String recipient;  // recipient's cell number
    private final String content;
    private final String hash;
    private final Date timestamp;

    public Message(String id, String sender, String recipient,
                   String content, String hash, Date timestamp) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
        this.hash = hash;
        this.timestamp = timestamp;
    }

    // Getters
    public String getId() { return id; }
    public String getSender() { return sender; }
    public String getRecipient() { return recipient; }
    public String getContent() { return content; }
    public String getHash() { return hash; }
    public Date getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'' +
                ", content='" + content + '\'' +
                ", hash='" + hash + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
