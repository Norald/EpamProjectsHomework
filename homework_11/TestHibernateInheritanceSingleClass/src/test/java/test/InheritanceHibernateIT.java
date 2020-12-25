package test;

import com.homework.epam.dao.BillingDetailsDao;
import com.homework.epam.model.BankAccount;
import com.homework.epam.model.BillingDetails;
import com.homework.epam.model.CreditCard;
import com.homework.epam.model.Owner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test.config.HibernateConfiguration;

import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfiguration.class})
@ComponentScan("com.homework.epam")
public class InheritanceHibernateIT {

    @Autowired
    BillingDetailsDao billingDetailsDao;

    @Autowired
    SessionFactory sessionFactory;

    private Session session;

    private EntityTransaction transaction;

    @Before
    public void init(){
        session = sessionFactory.openSession();
        transaction = session.getTransaction();
        transaction.begin();
    }

    @After
    public void after(){
        session.close();
    }

    @Test
    public void addAndGetOwnerAndDetailsWithDao(){
        Owner owner = new Owner();
        owner.setFirstName("First name");
        owner.setLastName("Last name");

        CreditCard creditCard = new CreditCard();
        creditCard.setCardNumber(4149);
        creditCard.setExpMonth("12");
        creditCard.setExpYear("2030");
        creditCard.setOwner(owner);

        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccount("Account");
        bankAccount.setBankName("Bank name");
        bankAccount.setOwner(owner);

        Set<BillingDetails> details = new HashSet<>();
        details.add(creditCard);
        details.add(bankAccount);
        owner.setBillingDetails(details);

        int userId = (Integer) session.save(owner);
        transaction.commit();
        Assert.assertEquals(owner.getBillingDetails().size(), billingDetailsDao.get(userId).size());
    }
}
