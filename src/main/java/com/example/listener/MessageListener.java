package com.example.listener;

import com.example.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.RecordTooLargeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageListener {

    private final MessageService messageService;

    @Autowired
    public MessageListener(MessageService messageService) {
        this.messageService = messageService;
    }

    @StreamListener(Processor.INPUT)
    public void message(Message<String> event) {
        boolean rc = messageService.processRecord(event);
        if (rc) {
            // This line of code gets triggered even if there's a RecordTooLargeException
            // or a TimeoutException
            log.info("Message sent successfully");
        } else {
            log.error("Error sending the message");
        }
    }
}
