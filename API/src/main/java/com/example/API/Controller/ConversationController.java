package com.example.API.Controller;

import com.example.API.Service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<Integer> getConversationId(@PathVariable Integer userId) {
        Integer conversationId = conversationService.getConversationIdByUserId(userId);
        if (conversationId != null) {
            return ResponseEntity.ok(conversationId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

