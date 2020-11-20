package config;

import com.sun.org.apache.xpath.internal.operations.Or;
import integration.Order;
import integration.OrderState;
import org.springframework.context.ConfigurableApplicationContext;
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
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.StandardIntegrationFlow;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.transformer.FileToStringTransformer;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import service.ReadFile;

import java.io.File;
import java.util.*;

@Configuration
@EnableIntegration
@ComponentScan({"integration", "config", "service"})
@IntegrationComponentScan
public class BasicIntegrationConfig {

    private final static String csvFile = "C:/Users/Влад/Desktop/input.csv";

    @Bean("outputChannel1")
    DirectChannel outputChannel1() {
        return new DirectChannel();
    }
    @Bean("outputChannel2")
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
        outputChannel1.subscribe(x -> System.out.println(x));

        DirectChannel outputChannel2 = ctx.getBean("outputChannel2", DirectChannel.class);
        outputChannel2.subscribe(x -> System.out.println(x));
        // запускаем выполнение flow
        Set<Order> set = ctx.getBean(BasicIntegrationConfig.I.class).getOrders(BasicIntegrationConfig.csvFile);
        Iterator<Order> iterator = set.iterator();
        while(iterator.hasNext()){

            Order order = iterator.next();
            ctx.getBean(BasicIntegrationConfig.I.class).process(order);

        }
    }
}
