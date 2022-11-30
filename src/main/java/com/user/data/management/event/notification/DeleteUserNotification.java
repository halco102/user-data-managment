package com.user.data.management.event.notification;

import com.user.data.management.event.service.AbstractSendNotification;
import com.user.data.management.event.service.KafkaSender;
import org.springframework.stereotype.Component;

@Component
public class DeleteUserNotification extends AbstractSendNotification<Long> {

    public DeleteUserNotification(KafkaSender<Long> kafkaSender) {
        super(kafkaSender);
    }

}
