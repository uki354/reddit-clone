package com.example.redditclone.controller;


import com.example.redditclone.dto.NotificationDTO;
import com.example.redditclone.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/all")
    public ResponseEntity<List<NotificationDTO>> getNotificationsForUser(@RequestParam String username){
        return new ResponseEntity<>(notificationService.getNotificationDTOsForUser(username), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Integer>  getCountOnActiveNotifications(@RequestParam String username){
        return new ResponseEntity<>(notificationService.getCountNotificationsForUser(username),HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAllNotificationsForUser(){
        notificationService.deleteNotificationsForUser();
    }







}
