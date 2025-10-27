package com.example.API.Service;

import com.example.API.DTO.Reponse.MessageResponse;
import com.example.API.DTO.Request.CreateMessageRequest;
import com.example.API.Entity.Conversation;
import com.example.API.Entity.Message;
import com.example.API.Entity.MessageImage;
import com.example.API.Repository.ConversationRepository;
import com.example.API.Repository.MessageImageRepository;
import com.example.API.Repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageImageRepository messageImageRepository;
    private final ConversationRepository conversationRepository;

    public List<MessageResponse> getMessagesByConversationId(Integer conversationId) {
        List<Message> messages = messageRepository.findAllByConversationId(conversationId);

        return messages.stream().map(m -> {
            List<String> imageUrls = messageImageRepository.findByMessageId(m.getId())
                    .stream()
                    .map(MessageImage::getImageUrl)
                    .collect(Collectors.toList());

            return new MessageResponse(
                    m.getId(),
                    m.getSender(),
                    m.getContent(),
                    m.getSentAt().toString(),
                    imageUrls
            );
        }).collect(Collectors.toList());
    }

    // ✅ Gửi tin nhắn mới
    public MessageResponse sendMessage(CreateMessageRequest request) {
        Conversation conversation = conversationRepository.findById(request.getConversationId())
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        Message message = new Message();
        message.setConversation(conversation);
        message.setSender(request.getSender());
        message.setContent(request.getContent());
        message.setSentAt(LocalDateTime.now());

        messageRepository.save(message);

        if (request.getImageUrls() != null) {
            for (String url : request.getImageUrls()) {
                MessageImage img = new MessageImage();
                img.setMessage(message);
                img.setImageUrl(url);
                img.setCreatedAt(LocalDateTime.now());
                messageImageRepository.save(img);
            }
        }

        List<String> imageUrls = messageImageRepository.findByMessageId(message.getId())
                .stream()
                .map(MessageImage::getImageUrl)
                .collect(Collectors.toList());

        return new MessageResponse(
                message.getId(),
                message.getSender(),
                message.getContent(),
                message.getSentAt().toString(),
                imageUrls
        );
    }
}
