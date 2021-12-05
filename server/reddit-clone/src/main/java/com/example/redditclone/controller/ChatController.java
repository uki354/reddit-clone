package com.example.redditclone.controller;

import com.example.redditclone.Model.Message;
import com.example.redditclone.service.ChatRoomService;
import com.example.redditclone.service.MessageService;
import com.example.redditclone.utility.ChatNotification;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatRoomService chatRoomService;
    private final MessageService messageService;

    @MessageMapping("/chat")
    public void processMessage(@Payload Message message){
        String chatId = chatRoomService
                .getChatId(message.getSenderId(),message.getRecipientId(), true);
        message.setChatId(chatId);

        Message saved = messageService.save(message);
        messagingTemplate.convertAndSendToUser(message.getRecipientId(),"/queue/messages",
                new ChatNotification(saved.getId(),
                        saved.getSenderId(),
                        saved.getSenderName()));

    }

    @GetMapping("/messages/{senderId}/{recipientId}/count")
    public ResponseEntity<Integer> countNewMessages(@PathVariable String senderId, @PathVariable String recipientId){
        return new ResponseEntity<>(messageService.countNewMessages(senderId,recipientId), HttpStatus.OK);

    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<?> findChatMessages(@PathVariable String senderId, @PathVariable String recipientId){
        return new ResponseEntity<>(messageService.getMessages(senderId,recipientId),HttpStatus.OK);
    }






}
