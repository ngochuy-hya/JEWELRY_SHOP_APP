package com.example.API.Repository;

import com.example.API.Entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConversationRepository extends JpaRepository<Conversation, Integer> {
    @Query("SELECT c.id FROM Conversation c WHERE c.user.userId = :userId")
    Integer findConversationIdByUserId(@Param("userId") Integer userId);

}