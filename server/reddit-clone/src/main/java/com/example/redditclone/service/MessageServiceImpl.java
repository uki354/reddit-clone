package com.example.redditclone.service;

import com.example.redditclone.Model.Message;
import com.example.redditclone.Model.MessageStatus;
import com.example.redditclone.exception.RedditCloneException;
import com.example.redditclone.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements  MessageService {

    private final MessageRepository messageRepository;
    private final ChatRoomService chatRoomService;


    public Message save(Message message){
        message.setStatus(MessageStatus.SENT);
        messageRepository.save(message);
        return message;
    }

    public int countNewMessages(String senderId, String recipientId){
        return messageRepository.countBySenderIdAndRecipientIdAndStatus(
                senderId,recipientId,MessageStatus.DELIVERED
        );
    }

    public List<Message> getMessages(String senderId, String recipientId){
        String chatId = chatRoomService.getChatId(senderId,recipientId,false);
        List<Message> messages = messageRepository.findByChatId(chatId);

        if(messages.size() > 0 ){
            messageRepository.updateMessages(chatId,MessageStatus.SEEN);
        }
        return messages;
    }


    public Message findById(String id){
        return messageRepository.findById(id).map(message -> {
            message.setStatus(MessageStatus.SEEN);
            return messageRepository.save(message);
        }).orElseThrow(() -> new RedditCloneException("Message not found with id " + id));
    }




}
