package com.example.API.Service;

import com.example.API.Repository.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversationService {
    @Autowired
    private ConversationRepository conversationRepository;

    public Integer getConversationIdByUserId(Integer userId) {
        return conversationRepository.findConversationIdByUserId(userId);
    }
}
