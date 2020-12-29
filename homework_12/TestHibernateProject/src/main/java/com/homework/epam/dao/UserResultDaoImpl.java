package com.homework.epam.dao;


import com.homework.epam.entity.UserResult;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserResultDaoImpl implements UserResultDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<UserResult> getAll() {
        List<UserResult> result = sessionFactory.getCurrentSession().createQuery("from UserResult ").list();
        return result;
    }


    @Override
    @Transactional
    public void save(UserResult userResult) {
        Session session = sessionFactory.getCurrentSession();
        session.save(userResult);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResult> getAllByUserId(Integer userId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select ur from UserResult ur where ur.user.id= (:usr_id)");
        query.setParameter("usr_id", userId);
        List<UserResult> result = query.list();
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResult> getAllBySubjectExamId(Integer subjectExamId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select ur from UserResult ur where ur.subjectExam.id = (:sbjct_id)");
        query.setParameter("sbjct_id", subjectExamId);
        List<UserResult> result = query.list();
        return result;
    }

    @Override
    public List<UserResult> getAllByUserIdAndBySubjectExamId(Integer userId, Integer subjectExamId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select ur from UserResult ur where ur.subjectExam.id = (:sbjct_id) and ur.user.id = (:usr_id)");
        query.setParameter("sbjct_id", subjectExamId);
        query.setParameter("usr_id", userId);
        List<UserResult> result = query.list();
        return result;
    }

    @Override
    public void delete(Integer userId, Integer subjectExamId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from UserResult ur where ur.subjectExam.id = (:sbjct_id) and ur.user.id = (:usr_id)");
        query.setParameter("sbjct_id", subjectExamId);
        query.setParameter("usr_id", userId);
        query.executeUpdate();
    }

    @Override
    public void delete(Integer userId) {
       Session session = sessionFactory.getCurrentSession();
       Query query = session.createQuery("delete from UserResult ur where ur.user.id = (:usr_id)");
       query.setParameter("usr_id", userId);
       query.executeUpdate();
    }
}
