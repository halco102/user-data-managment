package com.user.data.management.event.service;

import com.user.data.management.event.constants.MessageTopic;
import com.user.data.management.event.notification.DeleteUserNotification;
import com.user.data.management.event.notification.UserUpdateNotification;
import com.user.data.management.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class NotificationContext {

    private final Map<MessageTopic, INotification> context = new HashMap<>();

    public NotificationContext(List<INotification> notifications) {

        for (INotification notification : notifications) {

            if (notification instanceof UserUpdateNotification)
                context.put(MessageTopic.USER_UPDATE_EVENT, notification);
            else if (notification instanceof DeleteUserNotification)
                context.put(MessageTopic.USER_DELETE_EVENT, notification);
            else
                throw new NotFoundException("Context was not found");

        }

    }

    public void sendMessage(MessageTopic messageTopic, Object object, String topic) {
        context.get(messageTopic).sendNotification(topic, object);
    }

}
