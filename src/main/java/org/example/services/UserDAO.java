package org.example.services;

import org.example.HibernateUtil;
import org.example.entities.User;
import org.hibernate.LockMode;

import java.util.UUID;

public class UserDAO {


    public Iterable<User> getNotAppointedUsers(int count){
        return HibernateUtil.getSessionFactory().getCurrentSession().createQuery(
                "From " + User.class.getSimpleName() + " where isAppointed = false")
                .setMaxResults(count).getResultList();
    }



    public User get(UUID id) {
        return HibernateUtil.getSessionFactory()
                .getCurrentSession()
                .get(User.class, id, LockMode.PESSIMISTIC_READ);
    }

    public Iterable<User> getAll() {
        return HibernateUtil.getSessionFactory()
                .getCurrentSession().createQuery(
                        "From " + User.class.getSimpleName()+ " WHERE isAppointed = false").getResultList();

    }

    public User getFirst() {
        return (User) HibernateUtil.getSessionFactory()
                .getCurrentSession().createQuery(
                        "From " + User.class.getSimpleName()+ " WHERE isAppointed = false")
                .setFirstResult(0).setMaxResults(1).uniqueResult();

    }


    public UUID add(User user) {
        return (UUID) HibernateUtil.getSessionFactory()
                .getCurrentSession()
                .save(user);
    }
}
