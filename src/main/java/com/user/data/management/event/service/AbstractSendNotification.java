package com.user.data.management.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractSendNotification<T> implements INotification<T>{

    private final KafkaSender<T> kafkaSender;

    @Override
    public final void sendNotification(String topic, T object) {
        if (object == null) {
            log.error("T is null");
            throw new NullPointerException("T is null");
        }

        kafkaSender.sendMessage(topic, object);
    }

}
