package com.example.API.Repository;

import com.example.API.Entity.Message;
import com.example.API.Entity.MessageImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageImageRepository extends JpaRepository<MessageImage, Integer> {

    List<MessageImage> findByMessageId(Integer messageId);
    List<MessageImage> findByMessage(Message message);

}

