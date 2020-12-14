package controller;

import exception.WrongEmailOrPasswordException;
import model.User;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import service.UserService;

import java.util.HashMap;

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
     *
     * @throws Exception
     */
    @Test
    public void testChangeLanguage() throws Exception {
        String lang = "en";
        mockMvc.perform(MockMvcRequestBuilders.get("/changeLang").accept(MediaType.TEXT_HTML).param("lang", lang)).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
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
        Mockito.when(userService.findUser(login, pass)).thenReturn(user);

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
        Mockito.when(userService.findUser(Mockito.anyString(), Mockito.anyString())).thenThrow(new WrongEmailOrPasswordException());
        mockMvc.perform(MockMvcRequestBuilders.post("/login").param("login", login).param("pass", pass));

    }

    @Test
    public void doRegistrationTest() throws Exception {
        String email = "test@gmail.com";
        String pass1 = "1234";
        String pass2 = "1234";
        long idn = 111111;
        String english_name = "Name";
        String english_surname = "Surname";
        String english_patronymic = "Patronymic";
        String english_city = "City";
        String english_region = "Region";
        String english_school_name = "English school name";
        int average_certificate_point = 111;
        String ukrainian_name = "Ім`я";
        String ukrainian_surname = "Прізвище";
        String ukrainian_patronymic = "По-батькові";
        String ukrainian_city = "Місто";
        String ukrainian_region = "РЕгіон";
        String ukrainian_school_name = "Назва школи";
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
    public void doLogoutTest() throws Exception {
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("email", "dp260296pvm@gmail.com");
        mockMvc.perform(MockMvcRequestBuilders.post("/app/logout").accept(MediaType.TEXT_HTML).sessionAttrs(sessionattr))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }
}
