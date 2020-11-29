package com.homework.epam;

import com.homework.epam.service.Notebook;
import com.homework.epam.service.impl.RandomNotebook;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
@ComponentScan({"com.homework.epam"})
public class TestAopJAspectConfigRunner {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(TestAopJAspectConfigRunner.class);
        ctx.refresh();
        RandomNotebook randomNotebook = (RandomNotebook) ctx.getBean(RandomNotebook.class);
        randomNotebook.notateSomething();
    }
}
