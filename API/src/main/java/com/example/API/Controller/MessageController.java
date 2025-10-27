package com.example.API.Controller;

import com.example.API.DTO.Reponse.MessageResponse;
import com.example.API.DTO.Request.CreateMessageRequest;
import com.example.API.Service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/conversation/{conversationId}")
    public ResponseEntity<List<MessageResponse>> getMessages(@PathVariable Integer conversationId) {
        List<MessageResponse> messages = messageService.getMessagesByConversationId(conversationId);
        return ResponseEntity.ok(messages);
    }
    @PostMapping("/send")
    public ResponseEntity<MessageResponse> sendMessage(@RequestBody CreateMessageRequest request) {
        MessageResponse response = messageService.sendMessage(request);
        return ResponseEntity.ok(response);
    }
}
