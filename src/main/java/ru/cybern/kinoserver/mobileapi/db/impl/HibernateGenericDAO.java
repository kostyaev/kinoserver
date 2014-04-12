package ru.cybern.kinoserver.mobileapi.db.impl;
import org.hibernate.Criteria;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import ru.cybern.kinoserver.mobileapi.db.GenericDAO;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;


@Stateless
public abstract class HibernateGenericDAO<T, ID extends Serializable> implements GenericDAO<T, ID> {

    @PersistenceContext
    private EntityManager em;

    private Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public HibernateGenericDAO() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected Session getSession() {
        if (em == null)
            throw new IllegalStateException("Session has not been set on DAO before usage");
        return em.unwrap(Session.class);
    }

    private Class<T> getPersistentClass() {
        return persistentClass;
    }

    @Override
    public T save(T entity) {
        getSession().saveOrUpdate(entity);
        return entity;
    }

    @Override
    public void delete(T entity) {
        getSession().delete(entity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T findById(ID id, boolean lock) {
        T entity;
        if (lock)
            entity = (T) getSession().get(getPersistentClass(), id, LockOptions.UPGRADE);
        else
            entity = (T) getSession().get(getPersistentClass(), id);

        return entity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return createCriteria().list();
    }

    protected Criteria createCriteria() {
        return getSession().createCriteria(getPersistentClass());
    }

    @SuppressWarnings("unchecked")
    protected List<T> findByCriteria(Criteria criteria, int first, int max) {
        criteria.setFirstResult(first);
        criteria.setMaxResults(max);
        return criteria.list();
    }

    protected Query createQuery(String queryString) {
        return getSession().createQuery(queryString);
    }

    protected SQLQuery createSQLQuery(String queryString) {
        return getSession().createSQLQuery(queryString);
    }

    public int count() {
        return count(createCriteria());
    }

    protected int count(Criteria criteria) {
        criteria.setProjection(Projections.rowCount());
        return ((Long) criteria.uniqueResult()).intValue();
    }


    public void flush() {
        getSession().flush();
    }

    public void clear() {
        getSession().clear();
    }

}
