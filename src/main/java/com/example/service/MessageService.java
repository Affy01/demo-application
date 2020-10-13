package com.example.service;

import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final Processor processor;

    public MessageService(Processor processor) {
        this.processor = processor;
    }

    public boolean processRecord(Message<String> event) {
        return processor.output().send(event);
    }
}
