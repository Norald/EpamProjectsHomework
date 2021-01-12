package com.epam.homework.MessagingTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.epam.homework.MessagingTest.ActiveMQConfig.ORDER_QUEUE_RECEIVER;
import static com.epam.homework.MessagingTest.ActiveMQConfig.ORDER_QUEUE_SENDER;


@Service
public class Sender {
    private static Logger log = LoggerFactory.getLogger(Sender.class);

    private List<Message> messageList = new ArrayList<>();

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessage(Message message){
        messageList.add(message);
        log.info("Sending message: "+ message);
        jmsTemplate.convertAndSend(ORDER_QUEUE_RECEIVER, message);
    }

    @JmsListener(destination = ORDER_QUEUE_SENDER)
    public void receive(@Payload MessageResult message){
        log.info("Reply Received: "+ message);
        for (int i = 0; i < messageList.size(); i++) {
            if(messageList.get(i).getId()==message.getId()){
                log.info("Request with : "+ messageList.get(i)+" ,Response with id "+message+ " result: " + (message.getResult() == (messageList.get(i).getValueOne()+messageList.get(i).getValueTwo())));
            }
        }
    }
}
