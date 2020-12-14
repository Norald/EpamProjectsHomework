package com.epam.springcloud.notification;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class NotificationRepository {
    private final Set<Notification> notifications = new HashSet<>();

    public void addNotify(String user) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setNotifyBy(Notification.Notifier.EMAIL);
        notifications.add(notification);
    }

    public Set<Notification> getAllNotifications(){
        return notifications;
    }
}
