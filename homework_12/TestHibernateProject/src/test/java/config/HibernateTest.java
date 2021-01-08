package config;

import com.homework.epam.dao.SubjectExamDao;
import com.homework.epam.dao.UserDao;
import com.homework.epam.dao.UserResultDao;
import com.homework.epam.dto.UserDto;
import com.homework.epam.dto.UserResultDto;
import com.homework.epam.entity.SubjectExam;
import com.homework.epam.entity.User;
import com.homework.epam.entity.UserDetails;
import com.homework.epam.entity.UserResult;
import com.homework.epam.service.UserService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
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

    @Autowired
    private SessionFactory sessionFactory;

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
        User created = userDao.save(user);
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


    @Test
    @Transactional
    public void saveUserAndDetailsPM() {
        Session session = null;
        EntityTransaction transaction = null;
        session = sessionFactory.openSession();
        transaction = session.getTransaction();
        transaction.begin();

        User newUser = new User();
        newUser.setEmail("testPM@gmail.com");
        newUser.setPassword("13333123429");
        newUser.setIdn(133332319l);
        newUser.setBlocked(false);
        newUser.setUserRoleId(1);
        Set<UserResult> userResults = new HashSet<>();
        newUser.setUserResults(userResults);

        UserDetails userDetails = new UserDetails();
        userDetails.setName("Name");
        userDetails.setSurname("Surname");
        userDetails.setPatronymic("Patronymic");
        userDetails.setCity("City");
        userDetails.setRegion("Region");
        userDetails.setSchoolName("School name");
        userDetails.setAverageCertificate(200);
        userDetails.setName_ua("Імя");
        userDetails.setSurname_ua("Прізвище");
        userDetails.setPatronymic_ua("По батькові");
        userDetails.setCity_ua("Місто");
        userDetails.setRegion_ua("Регіон");
        userDetails.setSurname_ua("Назва школи");
        userDetails.setUser(newUser);

        session.persist(userDetails);
        transaction.commit();
        session.close();
    }

    @Test
    @Transactional
    public void getUserAndDetailsPM() {
        Session session = null;
        EntityTransaction transaction = null;
        session = sessionFactory.openSession();
        transaction = session.getTransaction();
        transaction.begin();
        int userId = 78;
        User user = session.get(User.class, userId);
        session.close();
        Assert.assertEquals(user.getEmail(), userDao.get(userId).getEmail());
    }

    @Test
    @Transactional
    public void deleteUserAndDetailsPM() {
        Session session = null;
        EntityTransaction transaction = null;
        session = sessionFactory.openSession();
        transaction = session.getTransaction();
        transaction.begin();
        User newUser = new User();
        newUser.setEmail("testPM1@gmail.com");
        newUser.setPassword("13331113429");
        newUser.setIdn(133331119l);
        newUser.setBlocked(false);
        newUser.setUserRoleId(1);
        Set<UserResult> userResults = new HashSet<>();
        newUser.setUserResults(userResults);

        UserDetails userDetails = new UserDetails();
        userDetails.setName("Name1");
        userDetails.setSurname("Surname1");
        userDetails.setPatronymic("Patronymic1");
        userDetails.setCity("City1");
        userDetails.setRegion("Region1");
        userDetails.setSchoolName("School name1");
        userDetails.setAverageCertificate(210);
        userDetails.setName_ua("Імя1");
        userDetails.setSurname_ua("Прізвище1");
        userDetails.setPatronymic_ua("По батькові1");
        userDetails.setCity_ua("Міст1о");
        userDetails.setRegion_ua("Регіон1");
        userDetails.setSurname_ua("Назва школ1и");
        userDetails.setUser(newUser);

        session.persist(userDetails);
        transaction.commit();
        transaction.begin();
        session.delete(userDetails);
        transaction.commit();

    }

    @Test
    public void testHibernateSecondLevelCache(){
        int newUserId = addAuthor(sessionFactory);

        EntityManager em = sessionFactory.createEntityManager();
        System.out.println(em.find(User.class, newUserId));

        printCacheInfo();

        em = sessionFactory.createEntityManager();
        System.out.println(em.find(User.class, newUserId));


    }

    private static void printCacheInfo() {
        List<CacheManager> cacheManagers = CacheManager.ALL_CACHE_MANAGERS;
        if (!cacheManagers.isEmpty()) {
            CacheManager cacheManager = cacheManagers.get(0);
            Cache authorsCache = cacheManager.getCache(User.class.getName());
            System.out.println("Authors second level cache has size = " + authorsCache.getSize());
        } else {
            System.out.println("Hibernate second level cache is disabled.");
        }
    }


    private static int addAuthor(SessionFactory sessionManagerFactory) {
        EntityManager entityManager = sessionManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();

            User newUser = new User();
            newUser.setEmail("testCache2@gmail.com");
            newUser.setPassword("13332253429");
            newUser.setIdn(133555229l);
            newUser.setBlocked(false);
            newUser.setUserRoleId(1);
            Set<UserResult> userResults = new HashSet<>();
            newUser.setUserResults(userResults);

            entityManager.persist(newUser);

            tx.commit();
            return newUser.getId();
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        }
    }


}
