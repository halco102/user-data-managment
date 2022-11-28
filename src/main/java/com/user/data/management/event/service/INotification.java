package com.user.data.management.event.service;

public interface INotification <T>{

    void sendNotification(String topic, T object);

}
