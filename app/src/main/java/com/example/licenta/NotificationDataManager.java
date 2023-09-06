package com.example.licenta;

import java.util.ArrayList;
import java.util.List;

public class NotificationDataManager {

    private static NotificationDataManager instance;
    private List<AppNotification> notifications = new ArrayList<>();

    private NotificationDataManager() {}

    public static NotificationDataManager getInstance() {
        if (instance == null) {
            instance = new NotificationDataManager();
        }
        return instance;
    }

    public List<AppNotification> getNotifications() {
        return notifications;
    }

    public void addNotification(AppNotification notification) {
        notifications.add(notification);
    }
}
