package com.homework.epam.dao;

import com.homework.epam.entity.SubjectExam;
import com.homework.epam.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Repository
public class SubjectExamDaoImpl implements SubjectExamDao{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public SubjectExam create(String name, String description, String name_ua, String description_ua) {
        SubjectExam subjectExam = new SubjectExam();
        subjectExam.setName(name);
        subjectExam.setDescription(description);
        subjectExam.setName_ua(name_ua);
        subjectExam.setDescription_ua(description_ua);
        sessionFactory.getCurrentSession().save(subjectExam);
        return subjectExam;
    }

    @Override
    @Transactional
    public void save(SubjectExam subjectExam) {
        Session session = sessionFactory.getCurrentSession();
        session.save(subjectExam);
    }

    @Override
    public SubjectExam get(Integer subjectExamId) {
        Session session = sessionFactory.getCurrentSession();
        SubjectExam subjectExam = session.get(SubjectExam.class, subjectExamId);
        return Objects.requireNonNull(subjectExam, "Subject exam not found by id: " + subjectExamId);
    }

    @Override
    public void delete(Integer subjectExamId) {
        SubjectExam subjectExam = get(subjectExamId);
        sessionFactory.getCurrentSession().delete(subjectExam);
    }

    @Override
    public List<SubjectExam> getAll() {
        List<SubjectExam> result = sessionFactory.getCurrentSession().createQuery("from SubjectExam ").list();
        return result;
    }
}
