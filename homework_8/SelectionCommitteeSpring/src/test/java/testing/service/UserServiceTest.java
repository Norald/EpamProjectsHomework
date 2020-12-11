package testing.service;

import db.dao.FacultyDao;
import db.dao.UserDao;
import exception.*;
import model.SubjectExam;
import model.User;
import model.UserDetails;
import model.UserResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    private final UserDao userDao = Mockito.mock(UserDao.class);
    private final FacultyDao facultyDao = Mockito.mock(FacultyDao.class);

    private User user;
    private UserDetails userDetailsEng;
    private UserDetails userDetailsUkr;
    private UserResult userResult;
    private SubjectExam subjectExam;

    @InjectMocks
    @Spy
    UserService userService;

    @Before
    public void init(){
        user= new User();
        user.setEmail("dp260296pvm@gmail.com");
        user.setPassword("111111");
        user.setBlocked(false);
        user.setId(1);
        user.setIdn(111111);
        user.setUserRoleId(2);

        userDetailsEng = new UserDetails();
        userDetailsEng.setName("asd");
        userDetailsEng.setSurname("asd");
        userDetailsEng.setPatronymic("asd");
        userDetailsEng.setRegion("asd");
        userDetailsEng.setCity("asd");
        userDetailsEng.setSchoolName("asd");
        userDetailsEng.setAverage_certificate(111);

        userDetailsUkr = new UserDetails();
        userDetailsUkr.setName("фів");
        userDetailsUkr.setSurname("фів");
        userDetailsUkr.setPatronymic("фів");
        userDetailsUkr.setRegion("фів");
        userDetailsUkr.setCity("фів");
        userDetailsUkr.setSchoolName("фів");
        userDetailsUkr.setAverage_certificate(111);

        subjectExam = new SubjectExam();
        subjectExam.setId(1);
        subjectExam.setName("Math");
        subjectExam.setDescription("Math");

        userResult = new UserResult();
        userResult.setResult(1);
        userResult.setSubjectExam(subjectExam);
        userResult.setUserId(1);
        userResult.setDateOfExam(new Date(System.currentTimeMillis()));
    }

    @Test
    public void findUserByEmailTest(){
        Mockito.when(userDao.findUser(Mockito.anyString())).thenReturn(user);
        User userCreated = userService.findUser(user.getEmail());
        Assert.assertEquals(user.getEmail(), userCreated.getEmail());
    }

    @Test(expected = WrongEmailOrPasswordException.class)
    public void findUserByEmailAndPasswordAndWrongEmailOrPasswordTest(){
        Mockito.when(userDao.findUser(Mockito.anyString(),Mockito.anyString())).thenThrow(new WrongEmailOrPasswordException());
        userService.findUser(user.getEmail(), user.getPassword());
    }

    @Test(expected = UserBlockedException.class)
    public void findUserByEmailAndPasswordAndUserBlockedTest(){
        user.setBlocked(true);
        Mockito.when(userDao.findUser(Mockito.anyString(),Mockito.anyString())).thenReturn(user);
        User userTest = userService.findUser(user.getEmail(), user.getPassword());
        if(userTest.isBlocked()){
            throw new UserBlockedException();
        }
    }

    @Test
    public void findUserByEmailAndPasswordTest(){
        Mockito.when(userDao.findUser(Mockito.anyString(),Mockito.anyString())).thenReturn(user);
        User userTest = userService.findUser(user.getEmail(), user.getPassword());
        Assert.assertEquals(user, userTest);
    }

    @Test(expected = SuchEmailExistException.class)
    public void doRegistrationSuchEmailExistsExceptionTest(){
        Mockito.when(userDao.findUser(Mockito.anyString())).thenReturn(user);
        Mockito.when(userDao.findUserByIdn(Mockito.anyString())).thenReturn(user);
        Mockito.doNothing().when(userDao).addUser(Mockito.anyString(), Mockito.anyLong(), Mockito.anyString());
        userService.doRegistration(user.getEmail(), user.getPassword(), user.getPassword(), String.valueOf(user.getIdn()), userDetailsEng.getName(), userDetailsEng.getSurname(), userDetailsEng.getPatronymic(), userDetailsEng.getCity(), userDetailsEng.getRegion(), userDetailsEng.getSchoolName(), String.valueOf(userDetailsEng.getAverageCertificate()), userDetailsUkr.getName(), userDetailsUkr.getSurname(),userDetailsUkr.getPatronymic(),userDetailsUkr.getCity(),userDetailsUkr.getRegion(),userDetailsUkr.getSchoolName());
    }

    @Test(expected = SuchIdnExistException.class)
    public void doRegistrationSuchIdnExistsExceptionTest(){
        Mockito.when(userDao.findUser(Mockito.anyString())).thenReturn(null);
        Mockito.when(userDao.findUserByIdn(Mockito.anyString())).thenReturn(user);
        Mockito.doNothing().when(userDao).addUser(Mockito.anyString(), Mockito.anyLong(), Mockito.anyString());
        userService.doRegistration(user.getEmail(), user.getPassword(), user.getPassword(), String.valueOf(user.getIdn()), userDetailsEng.getName(), userDetailsEng.getSurname(), userDetailsEng.getPatronymic(), userDetailsEng.getCity(), userDetailsEng.getRegion(), userDetailsEng.getSchoolName(), String.valueOf(userDetailsEng.getAverageCertificate()), userDetailsUkr.getName(), userDetailsUkr.getSurname(),userDetailsUkr.getPatronymic(),userDetailsUkr.getCity(),userDetailsUkr.getRegion(),userDetailsUkr.getSchoolName());
    }

    @Test(expected = PasswordDontMatchException.class)
    public void doRegistrationPasswordDontMatchExceptionTest(){
        Mockito.when(userDao.findUser(Mockito.anyString())).thenReturn(null);
        Mockito.when(userDao.findUserByIdn(Mockito.anyString())).thenReturn(null);
        Mockito.doNothing().when(userDao).addUser(Mockito.anyString(), Mockito.anyLong(), Mockito.anyString());
        userService.doRegistration(user.getEmail(), user.getPassword(), user.getPassword()+"123", String.valueOf(user.getIdn()), userDetailsEng.getName(), userDetailsEng.getSurname(), userDetailsEng.getPatronymic(), userDetailsEng.getCity(), userDetailsEng.getRegion(), userDetailsEng.getSchoolName(), String.valueOf(userDetailsEng.getAverageCertificate()), userDetailsUkr.getName(), userDetailsUkr.getSurname(),userDetailsUkr.getPatronymic(),userDetailsUkr.getCity(),userDetailsUkr.getRegion(),userDetailsUkr.getSchoolName());
    }

    @Test
    public void addUserTest(){
        Mockito.doNothing().when(userDao).addUser(Mockito.anyString(), Mockito.anyLong(), Mockito.anyString());
        userService.addUser(user.getEmail(), user.getIdn(), user.getPassword());
    }

    @Test
    public void findUserByIdnTest(){
        Mockito.when(userDao.findUserByIdn(Mockito.anyString())).thenReturn(user);
        User userCreated = userService.findUserByIdn(String.valueOf(user.getIdn()));
        Assert.assertEquals(user.getIdn(), userCreated.getIdn());
    }

    @Test
    public void addUserDetailsTest(){
        Mockito.doNothing().when(userDao).addUserDetails(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        userDao.addUserDetails(user.getId(), userDetailsEng.getName(), userDetailsEng.getSurname(), userDetailsEng.getPatronymic(), userDetailsEng.getCity(), userDetailsEng.getRegion(), userDetailsEng.getSchoolName(), userDetailsEng.getAverageCertificate(), userDetailsUkr.getName(), userDetailsUkr.getSurname(), userDetailsUkr.getPatronymic(), userDetailsUkr.getCity(), userDetailsUkr.getRegion(), userDetailsUkr.getSchoolName());
    }

    @Test
    public void addUserMarkTest(){
        Mockito.doNothing().when(userDao).addUserMark(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt());
        userDao.addUserMark(user.getId(), 1, 1);
    }

    @Test
    public void findUserResultsTest(){
        List<UserResult> list = new ArrayList<>();
        list.add(userResult);
        Mockito.when(userDao.findUserResult(Mockito.anyString(), Mockito.anyString())).thenReturn(list);
        List<UserResult> listRes = userDao.findUserResult(user.getEmail(), "en");
        Assert.assertEquals(list, listRes);

    }

    @Test
    public void findUserDetailsTest(){
        Mockito.when(userDao.findUserDetails(Mockito.any(), Mockito.anyString())).thenReturn(userDetailsEng);
        UserDetails userDetailsCreated = userService.findUserDetails(user, "en");
        Assert.assertEquals(userDetailsEng, userDetailsCreated);
    }
}
