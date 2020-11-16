package notate;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Initializer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        context.getBean(Notebook.class).notateSomething();
    }
}
