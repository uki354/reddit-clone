package com.example.redditclone.repository;

import com.example.redditclone.Model.Message;
import com.example.redditclone.Model.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {

    int countBySenderIdAndRecipientIdAndStatus(String senderId, String recipientId, MessageStatus status);
    List<Message> findByChatId(String chatId);
    @Query(value = "update Message m set m.status = :status where m.chatId = :chatId ")
    void updateMessages(String chatId, MessageStatus status);
}
