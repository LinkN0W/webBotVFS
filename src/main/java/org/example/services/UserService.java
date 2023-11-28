package org.example.services;

import org.example.entities.User;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import java.util.List;

public class UserService {


    UserDAO userDAO;
    public UserService(){
        userDAO = new UserDAO();
    }

    public void addUser(User user)  {
        Transaction transaction = DBService.getTransaction();
        try {
            System.out.println("ok");
            userDAO.add(user);
            transaction.commit();

        } catch (HibernateException | NoResultException e) {
            e.printStackTrace();
            DBService.transactionRollback(transaction);
        }

    }



    public Iterable<User> getNotAppointedUsers(int count){
        Iterable<User> users = null;
        Transaction transaction = DBService.getTransaction();
        try {
            System.out.println("ok");
            users = userDAO.getNotAppointedUsers(count);
            transaction.commit();

        } catch (HibernateException | NoResultException e) {
            e.printStackTrace();
            DBService.transactionRollback(transaction);
        }

        return users;
    }

    public Iterable<User> getAllNotAppointedUsers(){

        Iterable<User> users = null;
        Transaction transaction = DBService.getTransaction();
        try {
            System.out.println("ok");
            users = userDAO.getAll();
            transaction.commit();

        } catch (HibernateException | NoResultException e) {
            e.printStackTrace();
            DBService.transactionRollback(transaction);
        }

        return users;
    }


    public User getFirstNotAppointedUser(){

        User user = null;
        Transaction transaction = DBService.getTransaction();
        try {
            System.out.println("ok");
            user = userDAO.getFirst();
            transaction.commit();

        } catch (HibernateException | NoResultException e) {
            e.printStackTrace();
            DBService.transactionRollback(transaction);
        }

        return user;
    }
}
