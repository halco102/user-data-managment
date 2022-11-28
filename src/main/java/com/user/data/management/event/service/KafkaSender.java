package com.user.data.management.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaSender<T> {

    private final KafkaTemplate<String, T> kafkaTemplate;

    public void sendMessage(String topic, T message) {
        log.info("Topic " + topic + " message " + message);

        ListenableFuture<SendResult<String, T>> future = kafkaTemplate.send(topic, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, T>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Message was not sent" + ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, T> result) {
                log.info("Message was successful " + message + " result " + result.getRecordMetadata().toString());
            }
        });

    }

}
