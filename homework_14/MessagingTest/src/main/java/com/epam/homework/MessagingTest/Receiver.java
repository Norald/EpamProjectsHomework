package com.epam.homework.MessagingTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.epam.homework.MessagingTest.ActiveMQConfig.ORDER_QUEUE_RECEIVER;
import static com.epam.homework.MessagingTest.ActiveMQConfig.ORDER_QUEUE_SENDER;

@Component
public class Receiver {
    private static Logger log = LoggerFactory.getLogger(Receiver.class);
    private List<Message> messageList = new ArrayList<>();

    @Autowired
    private JmsTemplate jmsTemplate;

    @JmsListener(destination = ORDER_QUEUE_RECEIVER)
    public void receive(@Payload Message message) throws InterruptedException {
        log.info("Message received: " + message);
        messageList.add(message);
        log.info(String.valueOf(messageList.size()));
        if(messageList.size()>13){
            for (int i = 0; i < messageList.size(); i++) {
                log.info(String.valueOf(messageList.get(i)));
                jmsTemplate.convertAndSend(ORDER_QUEUE_SENDER, new MessageResult(messageList.get(i).getId(), (messageList.get(i).getValueOne()+messageList.get(i).getValueTwo())));
            }
        }
    }
}
