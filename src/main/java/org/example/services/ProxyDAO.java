package org.example.services;

import org.example.HibernateUtil;
import org.example.entities.Proxy;
import org.example.entities.User;

import java.util.UUID;

public class ProxyDAO {

    Iterable<Proxy> getAllProxies(){
        return HibernateUtil.getSessionFactory()
                .getCurrentSession().createQuery(
                        "From " + Proxy.class.getSimpleName()).getResultList();
    }

    UUID add(Proxy proxy) {
        return (UUID) HibernateUtil.getSessionFactory()
                .getCurrentSession()
                .save(proxy);
    }

    Proxy getProxyByServiceId(int serviceId){
        return (Proxy) HibernateUtil.getSessionFactory()
                .getCurrentSession().createQuery(
                        "From " +Proxy.class.getSimpleName()+ " WHERE serviceId = :serviceId"
                ).setParameter("serviceId", serviceId).setFirstResult(0)
                .setMaxResults(1)
                .uniqueResult();
    }
}
