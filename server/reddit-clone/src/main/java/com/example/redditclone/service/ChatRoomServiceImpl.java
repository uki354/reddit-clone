package com.example.redditclone.service;

import com.example.redditclone.Model.ChatRoom;
import com.example.redditclone.repository.ChatRoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;



@Service
@AllArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService{

    private final ChatRoomRepository chatRoomRepository;

    @Override
    public String getChatId(String senderId, String recipientId, boolean createIfNotExist){

        return  chatRoomRepository
                .findBySenderIdAndRecipientId(senderId, recipientId)
                .map(ChatRoom::getChatId)
                .orElseGet(() -> {
                    if(!createIfNotExist){
                        return "";
                    }
                   String chatId = String.format("%s_%s",senderId,recipientId);
                    ChatRoom senderRecipient = ChatRoom.builder()
                            .chatId(chatId)
                            .senderId(senderId)
                            .recipientId(recipientId)
                            .build();

                    ChatRoom recipientSender = ChatRoom.builder()
                            .chatId(chatId)
                            .senderId(senderId)
                            .recipientId(recipientId)
                            .build();

                    chatRoomRepository.save(senderRecipient);
                    chatRoomRepository.save(recipientSender);

                    return chatId;

                });
    }
}
