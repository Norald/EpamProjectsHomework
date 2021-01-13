package com.epam.homework.MessagingTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MessagingTestApplication implements ApplicationRunner {
	private static Logger log = LoggerFactory.getLogger(Sender.class);

	public static void main(String[] args) {
		SpringApplication.run(MessagingTestApplication.class, args);
	}

	@Autowired
	private Sender sender;

	@Override
	public void run(ApplicationArguments args) throws Exception{
		Message message1 = new Message(1, 3);
		Message message2 = new Message(2, 3);
		Message message3 = new Message(3, 3);
		Message message4 = new Message(4, 3);
		Message message5 = new Message(5, 3);
		Message message6 = new Message(6, 3);
		Message message7 = new Message(7, 3);
		Message message8 = new Message(8, 3);
		Message message9 = new Message(9, 3);
		Message message10 = new Message(10, 3);
		Message message11 = new Message(11, 3);
		Message message12 = new Message(12, 3);
		Message message13 = new Message(13, 3);
		Message message14 = new Message(14, 3);

		sender.sendMessage(message1);
		sender.sendMessage(message2);
		sender.sendMessage(message3);
		sender.sendMessage(message4);
		sender.sendMessage(message5);
		sender.sendMessage(message6);
		sender.sendMessage(message7);
		sender.sendMessage(message8);
		sender.sendMessage(message9);
		sender.sendMessage(message10);
		sender.sendMessage(message11);
		sender.sendMessage(message12);
		sender.sendMessage(message13);
		sender.sendMessage(message14);
	}
}
