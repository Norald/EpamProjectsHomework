package controller;

import config.WebAppConfig;
import exception.*;
import model.*;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import service.FacultyService;
import service.UserService;
import testing.TestConfig;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {WebAppConfig.class, TestConfig.class})
@ComponentScan({"controller", "model", "db", "pdf", "service"})
@WebAppConfiguration
public class AuthControllerIT {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    UserService userService;

    @Autowired
    FacultyService facultyService;

    private User user;
    private UserDetails userDetailsEng;
    private UserDetails userDetailsUkr;
    private UserResult userResult;
    private SubjectExam subjectExam;
    private SubjectExam subjectExam2;
    private Faculty faculty;
    private Admission admission;

    private MockHttpSession mockHttpSession;

    @Before
    public void setup() {
        Mockito.reset(userService);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockHttpSession = new MockHttpSession(webApplicationContext.getServletContext());
        user = new User();
        user.setEmail("dp260296pvm@gmail.com");
        user.setPassword("111111");
        user.setBlocked(false);
        user.setId(1);
        user.setIdn(111111);
        user.setUserRoleId(2);

        userDetailsEng = new UserDetails();
        userDetailsEng.setName("Name");
        userDetailsEng.setSurname("Surname");
        userDetailsEng.setPatronymic("Patronymic");
        userDetailsEng.setRegion("Region");
        userDetailsEng.setCity("City");
        userDetailsEng.setSchoolName("School");
        userDetailsEng.setAverage_certificate(111);

        userDetailsUkr = new UserDetails();
        userDetailsUkr.setName("Ім`я");
        userDetailsUkr.setSurname("Прізвище");
        userDetailsUkr.setPatronymic("По-батькові");
        userDetailsUkr.setRegion("Регіон");
        userDetailsUkr.setCity("Місто");
        userDetailsUkr.setSchoolName("Школа");
        userDetailsUkr.setAverage_certificate(111);

        subjectExam = new SubjectExam();
        subjectExam.setId(1);
        subjectExam.setName("Math");
        subjectExam.setDescription("Math");

        subjectExam2 = new SubjectExam();
        subjectExam2.setId(2);
        subjectExam2.setName("History");
        subjectExam2.setDescription("History");

        userResult = new UserResult();
        userResult.setResult(1);
        userResult.setSubjectExam(subjectExam);
        userResult.setUserId(1);
        userResult.setDateOfExam(new Date(System.currentTimeMillis()));

        admission = new Admission();
        admission.setId(1);
        admission.setApproved(false);
        admission.setDate(new Date(System.currentTimeMillis()));
        admission.setFacultyId(1);
        admission.setUserId(1);

        faculty = new Faculty();
        faculty.setId(1);
        faculty.setBudgetAmount(10);
        faculty.setTotalAmount(20);
        faculty.setName("Math faculty");
        faculty.setDescription("Math faculty");
    }

    @Test
    public void testStartPageIT() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.TEXT_HTML)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testLoginWrongEmailOrPasswordException() throws Exception {
        Mockito.when(userService.findUser(Mockito.anyString(), Mockito.anyString())).thenThrow(WrongEmailOrPasswordException.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/login").param("login", user.getEmail()).param("pass", user.getPassword())).andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void testLoginUserBlockedException() throws Exception {
        user.setBlocked(true);
        Mockito.when(userService.findUser(Mockito.anyString(), Mockito.anyString())).thenThrow(UserBlockedException.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/login").param("login", user.getEmail()).param("pass", user.getPassword())).andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void testRegistrationSuchEmailExist() throws Exception {
        Mockito.doThrow(SuchEmailExistException.class).when(userService).doRegistration(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        mockMvc.perform(MockMvcRequestBuilders.post("/doRegistration")
                .param("email", user.getEmail()).param("pass1", user.getPassword())
                .param("pass2", user.getPassword()).param("idn", String.valueOf(user.getIdn()))
                .param("name", userDetailsEng.getName()).param("surname", userDetailsEng.getSurname())
                .param("patronymic", userDetailsEng.getPatronymic()).param("city", userDetailsEng.getCity())
                .param("region", userDetailsEng.getRegion()).param("school_name", userDetailsEng.getSchoolName())
                .param("average_certificate_point", String.valueOf(userDetailsEng.getAverageCertificate())).param("name_ua", userDetailsUkr.getName())
                .param("surname_ua", userDetailsUkr.getSchoolName()).param("patronymic_ua", userDetailsUkr.getPatronymic())
                .param("city_ua", userDetailsUkr.getCity()).param("region_ua", userDetailsUkr.getRegion()).param("school_name_ua", userDetailsUkr.getSchoolName()))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void testRegistrationSuchIdnExist() throws Exception {
        Mockito.doThrow(SuchIdnExistException.class).when(userService).doRegistration(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        mockMvc.perform(MockMvcRequestBuilders.post("/doRegistration")
                .param("email", user.getEmail()).param("pass1", user.getPassword())
                .param("pass2", user.getPassword()).param("idn", String.valueOf(user.getIdn()))
                .param("name", userDetailsEng.getName()).param("surname", userDetailsEng.getSurname())
                .param("patronymic", userDetailsEng.getPatronymic()).param("city", userDetailsEng.getCity())
                .param("region", userDetailsEng.getRegion()).param("school_name", userDetailsEng.getSchoolName())
                .param("average_certificate_point", String.valueOf(userDetailsEng.getAverageCertificate())).param("name_ua", userDetailsUkr.getName())
                .param("surname_ua", userDetailsUkr.getSchoolName()).param("patronymic_ua", userDetailsUkr.getPatronymic())
                .param("city_ua", userDetailsUkr.getCity()).param("region_ua", userDetailsUkr.getRegion()).param("school_name_ua", userDetailsUkr.getSchoolName()))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void testRegistrationPasswordsDontMatch() throws Exception {
        Mockito.doThrow(PasswordDontMatchException.class).when(userService).doRegistration(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        mockMvc.perform(MockMvcRequestBuilders.post("/doRegistration")
                .param("email", user.getEmail()).param("pass1", user.getPassword())
                .param("pass2", user.getPassword()).param("idn", String.valueOf(user.getIdn()))
                .param("name", userDetailsEng.getName()).param("surname", userDetailsEng.getSurname())
                .param("patronymic", userDetailsEng.getPatronymic()).param("city", userDetailsEng.getCity())
                .param("region", userDetailsEng.getRegion()).param("school_name", userDetailsEng.getSchoolName())
                .param("average_certificate_point", String.valueOf(userDetailsEng.getAverageCertificate())).param("name_ua", userDetailsUkr.getName())
                .param("surname_ua", userDetailsUkr.getSchoolName()).param("patronymic_ua", userDetailsUkr.getPatronymic())
                .param("city_ua", userDetailsUkr.getCity()).param("region_ua", userDetailsUkr.getRegion()).param("school_name_ua", userDetailsUkr.getSchoolName()))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void testRegistrationEmptyParameters() throws Exception {
        Mockito.doThrow(EmptyParametersException.class).when(userService).doRegistration(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        mockMvc.perform(MockMvcRequestBuilders.post("/doRegistration")
                .param("email", user.getEmail()).param("pass1", user.getPassword())
                .param("pass2", user.getPassword()).param("idn", String.valueOf(user.getIdn()))
                .param("name", userDetailsEng.getName()).param("surname", userDetailsEng.getSurname())
                .param("patronymic", userDetailsEng.getPatronymic()).param("city", userDetailsEng.getCity())
                .param("region", userDetailsEng.getRegion()).param("school_name", userDetailsEng.getSchoolName())
                .param("average_certificate_point", String.valueOf(userDetailsEng.getAverageCertificate())).param("name_ua", userDetailsUkr.getName())
                .param("surname_ua", userDetailsUkr.getSchoolName()).param("patronymic_ua", userDetailsUkr.getPatronymic())
                .param("city_ua", userDetailsUkr.getCity()).param("region_ua", userDetailsUkr.getRegion()).param("school_name_ua", userDetailsUkr.getSchoolName()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    public void testSendAdmissionAndParticipateController() throws Exception {
        user.setEmail("dp260296pvm@gmail.com");
        String facultyId = "1";
        String facultyName = "Math faculty";
        Map<String, Date> mapOfAdmissions = new HashMap<>();
        mapOfAdmissions.put(facultyName, new Date(System.currentTimeMillis()));

        mockHttpSession.setAttribute("language", "en");
        mockHttpSession.setAttribute("admissions map", mapOfAdmissions);

        Mockito.when(userService.participateUser(facultyId, user.getEmail(), "en")).thenReturn(mapOfAdmissions);
        mockMvc.perform(MockMvcRequestBuilders.post("/app/participate")
                .param("faculty_id", facultyId).session(mockHttpSession))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/app/admissions"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.request().sessionAttribute("language", Matchers.is(Matchers.notNullValue())))
                .andExpect(MockMvcResultMatchers.request().sessionAttribute("admissions map", Matchers.is(Matchers.notNullValue())));
        mockHttpSession.invalidate();
    }

    @Test
    public void testAddMarkController() throws Exception {
        mockHttpSession.setAttribute("email", user.getEmail());
        Mockito.doNothing().when(userService).addUserMark(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        mockMvc.perform(MockMvcRequestBuilders.post("/app/add_mark")
                .param("mark", String.valueOf(userResult.getResult())).param("marksSelect", "3").session(mockHttpSession))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/app/marks"))
                .andExpect(MockMvcResultMatchers.request().sessionAttribute("email", Matchers.is(Matchers.notNullValue())));
        mockHttpSession.invalidate();
    }

    @Test
    public void testAdminFacultyAdmissionsController() throws Exception {
        List<SubjectExam> examList = new ArrayList<>();
        examList.add(subjectExam);

        List<SubjectExam> examFullList = new ArrayList<>();
        examFullList.add(subjectExam);
        examFullList.add(subjectExam2);
        examFullList.removeAll(examList);

        Mockito.when(facultyService.getFacultyDemendsWithName(Mockito.anyString(), Mockito.anyString())).thenReturn(examList);
        Mockito.when(facultyService.getAllSubjectExams(Mockito.anyString())).thenReturn(examFullList);
        Mockito.when(facultyService.findFacultyById(Mockito.anyString(), Mockito.anyString())).thenReturn(faculty);
        mockHttpSession.setAttribute("faculty", faculty);
        mockHttpSession.setAttribute("examList", examList);
        mockHttpSession.setAttribute("examAvailableList", examFullList);

        mockMvc.perform(MockMvcRequestBuilders.post("/app/admin/faculty_admissions")
                .session(mockHttpSession))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.request().sessionAttribute("faculty", Matchers.is(Matchers.notNullValue())))
                .andExpect(MockMvcResultMatchers.request().sessionAttribute("examList", Matchers.is(Matchers.notNullValue())))
                .andExpect(MockMvcResultMatchers.request().sessionAttribute("examAvailableList", Matchers.is(Matchers.notNullValue())));
        mockHttpSession.invalidate();
    }

}
