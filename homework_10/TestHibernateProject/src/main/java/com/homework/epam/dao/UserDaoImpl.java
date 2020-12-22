package com.homework.epam.dao;

import com.homework.epam.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public User create(String email, Long idn, boolean blocked, Integer userRoleId, String password) {
        User user = new User();
        user.setEmail(email);
        user.setIdn(idn);
        user.setBlocked(blocked);
        user.setUserRoleId(userRoleId);
        user.setPassword(password);
        sessionFactory.getCurrentSession().save(user);
        return user;
    }

    @Transactional
    @Override
    public void save(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
    }

    @Transactional
    @Override
    public User get(Integer userId) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, userId);
        return Objects.requireNonNull(user, "User not found by id: " + userId);
    }

    @Override
    @Transactional
    public User get(String email) {
        Session session = sessionFactory.getCurrentSession();
        Query query=session.createQuery("from User where email= :email");
        query.setParameter("email", email);
        List list = ((org.hibernate.query.Query<?>) query).list();
        User user= (User) list.get(0);
        System.out.println(user);
        return Objects.requireNonNull(user, "User not found by email: " + email);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        User user = get(id);
        sessionFactory.getCurrentSession().delete(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAll() {
        List<User> result = sessionFactory.getCurrentSession().createQuery("from User").list();
        return result;
    }
}
