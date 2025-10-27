package com.example.app_jewelry.entity;

public class Conversation {
    private int conversationId;
    private int userId;
    private String createdAt;

    public Conversation(int conversationId, int userId, String createdAt) {
        this.conversationId = conversationId;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
