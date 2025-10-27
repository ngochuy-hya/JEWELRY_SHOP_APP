package com.example.app_jewelry.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Message {
    private int messageId;
    private int conversationId;
    private String sender;
    private String content;
    private String sentAt;
    private List<String> imageUrls;

    // Constructors
    public Message() {}

    public Message(int messageId, int conversationId, String sender, String content, String sentAt, List<String> imageUrls) {
        this.messageId = messageId;
        this.conversationId = conversationId;
        this.sender = sender;
        this.content = content;
        this.sentAt = sentAt;
        this.imageUrls = imageUrls;
    }

    // Getters & Setters
    public int getMessageId() { return messageId; }
    public void setMessageId(int messageId) { this.messageId = messageId; }

    public int getConversationId() { return conversationId; }
    public void setConversationId(int conversationId) { this.conversationId = conversationId; }

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getSentAt() { return sentAt; }
    public void setSentAt(String sentAt) { this.sentAt = sentAt; }

    public List<String> getImageUrls() { return imageUrls; }
    public void setImageUrls(List<String> imageUrls) { this.imageUrls = imageUrls; }

    public String getFormattedTime() {
        try {
            SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            Date date = isoFormat.parse(sentAt);

            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            return outputFormat.format(date);
        } catch (Exception e) {
            return "";
        }
    }
}
