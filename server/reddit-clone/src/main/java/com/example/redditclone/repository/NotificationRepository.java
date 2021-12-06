package com.example.redditclone.repository;

import com.example.redditclone.Model.Notification;
import com.example.redditclone.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    int countAllByActiveTrueAndUser(User user);
    List<Notification> getAllByUser(User user);
    void deleteAllByUser(User user);
}
