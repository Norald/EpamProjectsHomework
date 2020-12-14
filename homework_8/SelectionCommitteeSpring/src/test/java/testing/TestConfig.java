package testing;

import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import service.FacultyService;
import service.UserService;

@Configuration
public class TestConfig {
    @Bean
    public UserService userService() {
        return Mockito.mock(UserService.class);
    }

    @Bean
    public FacultyService facultyService() {
        return Mockito.mock(FacultyService.class);
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setUseCodeAsDefaultMessage(true);

        return messageSource;
    }

}
