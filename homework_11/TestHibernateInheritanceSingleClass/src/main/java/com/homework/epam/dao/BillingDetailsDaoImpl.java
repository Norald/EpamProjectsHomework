package com.homework.epam.dao;

import com.homework.epam.model.BillingDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.hibernate.query.Query;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class BillingDetailsDaoImpl implements BillingDetailsDao{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public List<BillingDetails> get(Integer userId) {
        Session session = sessionFactory.getCurrentSession();
        Query query=session.createQuery("select bd from BillingDetails bd where bd.owner.id= :owner_id");
        query.setParameter("owner_id", userId);
        return query.list();
    }
}
