package com.homework.epam.dao;

import com.homework.epam.entity.SubjectExam;
import com.homework.epam.entity.User;
import com.homework.epam.entity.UserResult;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
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
    public UserResult create(User user, SubjectExam subjectExam, int result, Date date) {
        UserResult userResult = new UserResult();
        userResult.setUser(user);
        userResult.setSubjectExam(subjectExam);
        userResult.setResult(result);
        userResult.setDateOfExam(date);
        sessionFactory.getCurrentSession().save(userResult);
        return userResult;
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
        final List<Integer> ids = new ArrayList<Integer>();
        ids.add(userId);
        List<UserResult> result = sessionFactory.getCurrentSession().createQuery("select ur from UserResult ur where ur.user.id in (:usr_id)").setParameterList("usr_id", ids).list();
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResult> getAllBySubjectExamId(Integer subjectExamId) {
        final List<Integer> ids = new ArrayList<Integer>();
        ids.add(subjectExamId);
        List<UserResult> result = sessionFactory.getCurrentSession().createQuery("select ur from UserResult ur where ur.subjectExam.id in (:sbjct_id)").setParameterList("sbjct_id", ids).list();
        return result;
    }

    @Override
    public List<UserResult> getAllByUserIdAndBySubjectExamId(Integer userId, Integer subjectExamId) {
        final List<Integer> ids = new ArrayList<Integer>();
        ids.add(userId);
        final List<Integer> ids2 = new ArrayList<Integer>();
        ids.add(subjectExamId);
        List<UserResult> result = sessionFactory.getCurrentSession().createQuery("select ur from UserResult ur where ur.subjectExam.id in (:sbjct_id) and ur.user.id in (:usr_id)").setParameterList("sbjct_id", ids2).setParameterList("usr_id", ids).list();
        return result;
    }

    @Override
    public void delete(Integer userId, Integer subjectExamId) {
        final List<Integer> ids = new ArrayList<Integer>();
        ids.add(userId);
        final List<Integer> ids2 = new ArrayList<Integer>();
        ids.add(subjectExamId);
        sessionFactory.getCurrentSession().createQuery("select ur from UserResult ur where ur.subjectExam.id in (:sbjct_id) and ur.user.id in (:usr_id)").setParameterList("sbjct_id", ids2).setParameterList("usr_id", ids).executeUpdate();
    }

    @Override
    public void delete(Integer userId) {
        final List<Integer> ids = new ArrayList<Integer>();
        ids.add(userId);
        sessionFactory.getCurrentSession().createQuery("delete from UserResult ur where ur.user.id in (:usr_id)").setParameterList("usr_id", ids).executeUpdate();
    }
}
