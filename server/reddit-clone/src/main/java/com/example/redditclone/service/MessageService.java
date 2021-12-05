package com.example.redditclone.service;

import com.example.redditclone.Model.Message;

import java.util.List;

public interface MessageService {
    Message save(Message message);
    int countNewMessages(String senderId, String recipientId);
    List<Message> getMessages(String senderId, String recipientId);

}
