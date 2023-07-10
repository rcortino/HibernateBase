package com.skapps.hibernate;

import com.skapps.hibernate.params.Param;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TransactionRequiredException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EntityQuery<T> {

    final Class<T> tClass;

    @Autowired
    private SessionFactory sessionFactory;
    private final Session session;
    private final CriteriaBuilder criteriaBuilder;
    private final  CriteriaQuery<T> criteriaQuery;
    private final Root<T> root;

    public EntityQuery(Class<T> tClass, SessionFactory sessionFactory) {
        this.tClass = tClass;
        this.sessionFactory = sessionFactory;
        session = getSession();
        criteriaBuilder = session.getCriteriaBuilder();
        criteriaQuery = criteriaBuilder.createQuery(tClass);
        root = criteriaQuery.from(tClass);
    }

    @Transactional
    public List<T> performListQuery(List<Param> params) {
        try {
            org.hibernate.query.Query<T> query = buildQuery(params);
            return query.getResultList();
        } catch (NoResultException e){
            return null;
        }
    }

    @Transactional
    public List<T> performOrderedQuery(List<Param> params, String orderBy){

        if (params != null && !params.isEmpty()) {
            criteriaQuery.select(root).where(getPredicates(params)).orderBy();
        }
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get(orderBy)));
        try {
            org.hibernate.query.Query<T> query = session.createQuery(criteriaQuery);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public List<T> performPredicateQuery(Predicate[] predicates) {
        try {
            criteriaQuery.select(root).where(predicates);
            Query<T> query = session.createQuery(criteriaQuery);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public T getSingleResult(List<Param> params) {
        try {
            Query<T> query = buildQuery(params);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public void saveEntity(T enitity) {
        try {
            Transaction tx = getTransaction(session);
            session.persist(enitity);
            tx.commit();
        } catch (TransactionRequiredException e) {

        }
    }

    @Transactional
    public void delete(T entity) {
        Transaction tx = getTransaction(session);
        session.remove(entity);
        tx.commit();
    }

    private Query<T> buildQuery(List<Param> params) {

        if (params != null && !params.isEmpty()) {

            criteriaQuery.select(root).where(getPredicates(params));
        }
        return session.createQuery(criteriaQuery);
    }

    private Predicate[] getPredicates(List<Param> params) {
        Predicate[] predicates = new Predicate[params.size()];
        for (int i = 0; i < params.size(); i++) {
            predicates[i] = params.get(i).getPredicate(criteriaBuilder, root);
        }
        return predicates;
    }

    private Session getSession() {
        if (sessionFactory.getCurrentSession().isOpen()) {
            Session session = sessionFactory.getCurrentSession();
            if (session.getTransaction().isActive()){
                session.getTransaction();
            } else {
                session.beginTransaction();
            }
            return sessionFactory.getCurrentSession();
        } else {
            sessionFactory.openSession();
            Session session = sessionFactory.getCurrentSession();
            if (session.getTransaction().isActive()){
                session.getTransaction();
            } else {
                session.beginTransaction();
            }
            return sessionFactory.getCurrentSession();
        }
    }

    private Transaction getTransaction(Session session) {
        try {
            Transaction transaction = session.getTransaction();
            if (!transaction.isActive()) {
                transaction.begin();
            }
            return transaction;
        } catch (Exception e) {
            return session.beginTransaction();
        }
    }

}
