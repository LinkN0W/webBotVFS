package org.example.services;

import org.example.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

class DBService {

    public static Transaction getTransaction(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = HibernateUtil.getSessionFactory().getCurrentSession().getTransaction();
        if (!transaction.isActive()) {
            transaction = session.beginTransaction();
        }
        return transaction;
    }

    public static void transactionRollback(Transaction transaction){
        if (transaction.getStatus() == TransactionStatus.ACTIVE
                || transaction.getStatus() == TransactionStatus.MARKED_ROLLBACK) {
            transaction.rollback();
        }
    }

}
