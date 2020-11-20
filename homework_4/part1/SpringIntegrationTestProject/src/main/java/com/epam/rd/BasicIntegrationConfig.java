package com.epam.rd;

import com.epam.rd.integration.Order;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.GenericSelector;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

import java.util.*;

@Configuration
@EnableIntegration
@ComponentScan({"com.epam.rd"})
@IntegrationComponentScan
public class BasicIntegrationConfig {
    private final static Logger logger = LogManager.getLogger(BasicIntegrationConfig.class.getName());

    private final static String csvFile = "C:/Users/Влад/Desktop/input.csv";

    @Bean
    DirectChannel outputChannel1() {
        return new DirectChannel();
    }

    @Bean
    DirectChannel outputChannel2() {
        return new DirectChannel();
    }

    @MessagingGateway
    public interface I {
        @Gateway(requestChannel = "orderIFlow.input") // absent
        void process(Order order);
        @Gateway(requestChannel = "outputChannel2")
        Set<Order> getOrders(String csvFile);
    }

    @Bean
    public IntegrationFlow orderIFlow() {
        return flow -> flow
                .handle("ordersStorage", "process")
                .filter(onlyGoodStatusOrders())
                .channel("outputChannel1");
    }

    @Bean
    public GenericSelector<Order> onlyGoodStatusOrders() {
        return new GenericSelector<Order> (){
            @Override
            public boolean accept(Order order) {
                return !order.getState().contains("CANCELED");
            }
        };
    }

    @Bean
    public IntegrationFlow fileReadFlow() {
        return IntegrationFlows.from("outputChannel2")
                .handle("readFile", "getOrders")
                .get();
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(BasicIntegrationConfig.class);
        ctx.refresh();

        DirectChannel outputChannel1 = ctx.getBean("outputChannel1", DirectChannel.class);
        outputChannel1.subscribe(x -> logger.info(x));

        DirectChannel outputChannel2 = ctx.getBean("outputChannel2", DirectChannel.class);
        outputChannel2.subscribe(x -> logger.info(x));
        // запускаем выполнение flow
        Set<Order> set = ctx.getBean(BasicIntegrationConfig.I.class).getOrders(BasicIntegrationConfig.csvFile);
        Iterator<Order> iterator = set.iterator();
        while(iterator.hasNext()){

            Order order = iterator.next();
            ctx.getBean(BasicIntegrationConfig.I.class).process(order);

        }
    }
}
