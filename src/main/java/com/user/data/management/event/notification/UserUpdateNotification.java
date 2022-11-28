package com.user.data.management.event.notification;

import message.PostedBy;
import com.user.data.management.event.service.AbstractSendNotification;
import com.user.data.management.event.service.KafkaSender;
import org.springframework.stereotype.Component;

@Component
public class UserUpdateNotification extends AbstractSendNotification<PostedBy> {

    public UserUpdateNotification(KafkaSender<PostedBy> kafkaSender) {
        super(kafkaSender);
    }

    @Override
    public void sendNotification(String topic, PostedBy object) {
        super.sendNotification(topic, object);
    }
}
