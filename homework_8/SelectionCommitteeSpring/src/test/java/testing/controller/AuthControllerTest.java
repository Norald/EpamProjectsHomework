package testing.controller;

import config.WebAppConfig;
import exception.WrongEmailOrPasswordException;
import model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;
import testing.service.UserService;

import java.util.HashMap;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {WebAppConfig.class})
//@WebAppConfiguration
public class AuthControllerTest {
    private final UserService userService = Mockito.mock(UserService.class);
    private final AuthController authController = new AuthController(userService);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(authController).setControllerAdvice(new GlobalControllerExceptionHandler()).build();

    @Test
    public void testStartPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.TEXT_HTML)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/app/home").accept(MediaType.TEXT_HTML)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Expecting redirect
     * @throws Exception
     */
    @Test
    public void testChangeLanguage() throws Exception {
        String lang = "en";
        mockMvc.perform(MockMvcRequestBuilders.get("/changeLang").accept(MediaType.TEXT_HTML).param("lang",lang)).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    public void testLogin() throws Exception {
        String login = "dp260296pvm@gmail.com";
        String pass = "111111";
        User user = new User();
        user.setEmail(login);
        user.setPassword(pass);
        user.setBlocked(false);
        user.setId(1);
        user.setIdn(111111);
        user.setUserRoleId(2);
        Mockito.when(userService.findUser(login,pass)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/login").param("login", login).param("pass", pass))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:app/home"));

        Mockito.verify(userService, Mockito.times(1)).findUser(login, pass);
        Mockito.verifyNoMoreInteractions(userService);
    }

    @Test(expected = WrongEmailOrPasswordException.class)
    public void testLoginWrongEmailOrPasswordException() throws Exception {
        String login = "dp260296pvm@gmail.com";
        String pass = "111111";
        User user = new User();
        user.setEmail(login);
        user.setPassword(pass);
        user.setBlocked(false);
        user.setId(1);
        user.setIdn(111111);
        user.setUserRoleId(2);
        Mockito.when(userService.findUser(login,pass)).thenThrow(new WrongEmailOrPasswordException());
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/login").param("login", login).param("pass", pass));
        }catch (NestedServletException e){
            throw new WrongEmailOrPasswordException();
        }
    }

    @Test
    public void doRegistrationTest() throws Exception{
        String email = "test@gmail.com";
        String pass1 = "1234";
        String pass2 = "1234";
        long idn = 111111;
        String english_name = "asdasdasd";
        String english_surname = "asdasdasd";
        String english_patronymic = "asdasdasd";
        String english_city= "asdasdasd";
        String english_region= "asdasdasd";
        String english_school_name= "asdasdasd";
        int average_certificate_point= 111;
        String ukrainian_name = "іфіфвфів";
        String ukrainian_surname = "іфіфвфів";
        String ukrainian_patronymic = "іфіфвфів";
        String ukrainian_city = "іфіфвфів";
        String ukrainian_region = "іфіфвфів";
        String ukrainian_school_name = "іфіфвфів";
        User user = new User();
        user.setEmail(email);
        user.setPassword(pass1);
        user.setBlocked(false);
        user.setId(1);
        user.setIdn(idn);
        user.setUserRoleId(2);

        mockMvc.perform(MockMvcRequestBuilders.post("/doRegistration")
                .param("email", email).param("pass1", pass1)
                .param("pass2", pass2).param("idn", String.valueOf(idn))
                .param("name", english_name).param("surname", english_surname)
                .param("patronymic", english_patronymic).param("city", english_city)
                .param("region", english_region).param("school_name", english_school_name)
                .param("average_certificate_point", String.valueOf(average_certificate_point)).param("name_ua", ukrainian_name)
                .param("surname_ua", ukrainian_surname).param("patronymic_ua", ukrainian_patronymic)
                .param("city_ua", ukrainian_city).param("region_ua", ukrainian_region).param("school_name_ua", ukrainian_school_name))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:start"));
    }

    @Test
    public void doLogoutTest() throws Exception{
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("email", "dp260296pvm@gmail.com");
        mockMvc.perform(MockMvcRequestBuilders.post("/app/logout").accept(MediaType.TEXT_HTML).sessionAttrs(sessionattr))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }
}
