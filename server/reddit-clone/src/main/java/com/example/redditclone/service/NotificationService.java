package com.example.redditclone.service;

import com.example.redditclone.Model.NotificationType;
import com.example.redditclone.Model.Post;
import com.example.redditclone.Model.User;
import com.example.redditclone.dto.NotificationDTO;

import java.util.List;

public interface NotificationService {

    void saveNotification(User doer, User owner, NotificationType type, Post post);
    List<NotificationDTO> getNotificationDTOsForUser(String username);
    int getCountNotificationsForUser(String username);
    void deleteNotificationsForUser();
}
