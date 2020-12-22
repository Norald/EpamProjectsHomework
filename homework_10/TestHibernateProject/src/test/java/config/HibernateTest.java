package config;

import com.homework.epam.dao.SubjectExamDao;
import com.homework.epam.dao.UserDao;
import com.homework.epam.dao.UserResultDao;
import com.homework.epam.dto.SubjectExamDto;
import com.homework.epam.dto.UserDto;
import com.homework.epam.dto.UserResultDto;
import com.homework.epam.entity.SubjectExam;
import com.homework.epam.entity.User;
import com.homework.epam.entity.UserResult;
import com.homework.epam.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfiguration.class})
@ComponentScan("com.homework.epam")
public class HibernateTest {
    @Autowired
    private UserService userService;

    @Autowired
    private SubjectExamDao subjectExamDao;

    @Autowired
    private UserResultDao userResultDao;

    @Autowired
    UserDao userDao;

    private User user;

    @Before
    public void init(){
        user = new User();
        user.setEmail("123217844883@gmail.com");
        user.setPassword("1247888829");
        user.setIdn(12888879l);
        user.setBlocked(false);
        user.setUserRoleId(1);
    }

    @Test
    public void testCreateUserWithDao() {
        User created = userDao.create(user.getEmail(), user.getIdn(), user.isBlocked(), user.getUserRoleId(), user.getPassword());
        Assert.assertEquals(created.getEmail(), user.getEmail());
    }

    @Test
    public void testDeleteUserWithDao() {
        User fromDb = userDao.get("123217844883@gmail.com");
        userDao.delete(fromDb.getId());
    }

    @Test
    public void testCreateUserWithService(){
        UserDto userDto = createUserDto();
        UserDto createdUser = userService.create(userDto);
        Assert.assertEquals(userDto.getEmail(), createdUser.getEmail());
    }

    @Test
    public void testDeleteUserWithService() {
        UserDto userDto = createUserDto();
        UserDto createdUser = userService.create(userDto);
        userService.delete(createdUser.getId());
    }

    @Test
    public void testGetUserWithService() {
        UserDto userDto = createUserDto();
        UserDto createdUser = userService.create(userDto);
        Assert.assertEquals(userDto.getEmail(), userService.get(createdUser.getId()).getEmail());
    }

    @Test
    public void testUpdateUserWithService(){
        UserDto userDto = createUserDto();
        UserDto createdUser = userService.create(userDto);
        createdUser.setEmail("576.com");
        UserDto updatedUser = userService.update(createdUser);
        Assert.assertNotEquals(updatedUser.getEmail(), userDto.getEmail());
    }

    private UserDto createUserDto() {
        UserDto userDto = new UserDto();
        userDto.setEmail("5988.com");
        userDto.setPassword("5589879855");
        userDto.setIdn(75355999l);
        userDto.setBlocked(false);
        userDto.setUserRoleId(1);
        Set<UserResultDto> userResultDtoSet = new HashSet<>();
        userDto.setUserResults(userResultDtoSet);
        return userDto;
    }

    @Test
    public void createFullEntitiesDaoTest(){
        User newUser = new User();
        newUser.setEmail("testHiberasdasdasd11@gmail.com");
        newUser.setPassword("1244123429");
        newUser.setIdn(12812319l);
        newUser.setBlocked(false);
        newUser.setUserRoleId(1);

        userDao.save(newUser);

        SubjectExam subjectExam = new SubjectExam();
        subjectExam.setName("Fizra112323");
        subjectExam.setName_ua("Fizra121233");
        subjectExam.setDescription("Fizra12123");
        subjectExam.setDescription_ua("Fizra121233");

        subjectExamDao.save(subjectExam);

        UserResult userResult = new UserResult();
        userResult.setUser(newUser);
        userResult.setSubjectExam(subjectExam);
        userResult.setResult(200);
        userResult.setDateOfExam(new Date(System.currentTimeMillis()));
        
        userResultDao.save(userResult);

    }

}
