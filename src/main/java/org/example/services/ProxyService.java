package org.example.services;

import org.example.entities.Proxy;
import org.example.entities.User;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;

public class ProxyService {


    ProxyDAO proxyDAO;
    public ProxyService(){
        proxyDAO = new ProxyDAO();
    }


    public Iterable<Proxy> getAll(){

        Iterable<Proxy> proxies = null;
        Transaction transaction = DBService.getTransaction();
        try {
            System.out.println("ok");
            proxies = proxyDAO.getAllProxies();
            transaction.commit();

        } catch (HibernateException | NoResultException e) {
            e.printStackTrace();
            DBService.transactionRollback(transaction);
        }

        return proxies;
    }


    public void addProxy(Proxy proxy)  {
        Transaction transaction = DBService.getTransaction();
        try {
            if(proxyDAO.getProxyByServiceId(proxy.getServiceId()) == null) {
                proxyDAO.add(proxy);
                transaction.commit();
            }
            else{
                DBService.transactionRollback(transaction);
            }

        } catch (HibernateException | NoResultException e) {
            e.printStackTrace();
            DBService.transactionRollback(transaction);
        }

    }
}
