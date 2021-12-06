package com.example.redditclone.repository;

import com.example.redditclone.Model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom,Integer> {

    Optional<ChatRoom> findBySenderIdAndRecipientId(String senderId, String recipientId);
}
