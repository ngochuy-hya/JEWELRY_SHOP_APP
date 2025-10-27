package com.example.API.Repository;

import com.example.API.Entity.Message;
import com.example.API.Entity.MessageImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query("SELECT m FROM Message m WHERE m.conversation.id = :conversationId ORDER BY m.sentAt ASC")
    List<Message> findAllByConversationId(@Param("conversationId") Integer conversationId);
    List<Message> findByConversationId(Integer conversationId);

}

