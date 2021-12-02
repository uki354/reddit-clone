package com.example.redditclone.service;


import com.example.redditclone.Model.Notification;
import com.example.redditclone.Model.NotificationType;
import com.example.redditclone.Model.Post;
import com.example.redditclone.Model.User;
import com.example.redditclone.dto.NotificationDTO;
import com.example.redditclone.dto.PostDTO;
import com.example.redditclone.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class NotificationServiceImpl implements NotificationService{

    private final  NotificationRepository notificationRepository;
    private final UserService userService;
    private final AuthService authService;

    @Override
    public void saveNotification(User doer, User owner, NotificationType type, Post post){
        if(!doer.getUsername().equals(owner.getUsername())) {
            notificationRepository.save(Notification.builder()
                    .user(owner)
                    .doer(doer)
                    .type(type)
                    .post(post)
                    .active(true)
                    .build());
        }

    }
    @Override
    public List<NotificationDTO> getNotificationDTOsForUser(String username){
        User user = userService.findUser(username);
        List<Notification> notifications = notificationRepository.getAllByUser(user);
        return notifications.stream().map(this::mapToDTO).collect(Collectors.toList());

    }
    @Override
    public int getCountNotificationsForUser(String username){
        User user = userService.findUser(username);
        return notificationRepository.countAllByActiveTrueAndUser(user);
    }
    @Override
    public void deleteNotificationsForUser(){
        User user = authService.getCurrentUser();
        notificationRepository.deleteAllByUser(user);
    }







    private NotificationDTO mapToDTO(Notification notification){

        return NotificationDTO.builder()
                .content(getContentForType(notification.getType()))
                .postId(notification.getPost().getId())
                .user(notification.getUser().getUsername())
                .doer(notification.getDoer().getUsername())
                .build();


    }

    private String getContentForType(NotificationType type){
        switch (type){
            case LIKE_POST: return " just upvoted your post!";
            case COMMENT: return " just commented on your post!";
            case LIKE_COMMENT: return " just liked your comment!";
            default: return "";
        }
    }




}
