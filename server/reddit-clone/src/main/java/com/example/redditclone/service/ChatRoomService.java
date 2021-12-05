package com.example.redditclone.service;

public interface ChatRoomService {

    String getChatId(String senderId, String recipientId, boolean createIfNotExist);
}
