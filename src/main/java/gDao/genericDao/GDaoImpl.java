package gDao.genericDao;

import gDao.util.Finder;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

/**
 * User: Mert Meral
 */
//@Transactional //Transactional should be provided from service layer
@Repository
public class GDaoImpl<T extends Serializable> extends HibernateDaoSupport implements GDao<T> {
    private static final Logger logger = Logger.getLogger(GDaoImpl.class);
    protected Class<T> persistentClass;

    @Autowired
    public void init(SessionFactory factory) {
        setSessionFactory(factory);
    }

    public GDaoImpl() {
        try {
            ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
            persistentClass = (Class<T>) type.getActualTypeArguments()[0];
        } catch (Exception e) {
            //continue
        }
    }

//    public T get(ID objectId) {
//        return (T) getSessionFactory().getCurrentSession().get(persistentClass, objectId);
//    }
//
//    public void deleteWithId(ID objectId) {
//        T obj = get(objectId);
//        getSessionFactory().getCurrentSession().delete(obj);
//    }

    public Object save(T object) {
        return getSessionFactory().getCurrentSession().save(object);
    }

    public void saveOrUpdate(T object) throws Exception {
        getSessionFactory().getCurrentSession().saveOrUpdate(object);
    }

    public void saveOrUpdateAll(Collection<T> entityList) {
        for (T t : entityList) {
            getSessionFactory().getCurrentSession().saveOrUpdate(t);
        }
    }


    public void update(T object) {
        getSessionFactory().getCurrentSession().update(object);
    }

    public void delete(T object) {
        getSessionFactory().getCurrentSession().delete(object);
    }

    public List<T> findAll() {
        Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(persistentClass);
        return criteria.list();
    }

    public List<T> findAll(final String orderColumn) {
        Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(persistentClass);
        criteria.addOrder(Order.asc(orderColumn));
        return criteria.list();
    }

    public List<T> findByColumn(String column, Object value) {
        Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(persistentClass);
        criteria.add(Restrictions.eq(column, value));
        return criteria.list();
    }

    public T findByColumnUnique(String column, Object value) {
        Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(persistentClass);
        criteria.add(Restrictions.eq(column, value));
        return (T) criteria.uniqueResult();
    }

    public List<T> findWithFinder(Finder finder) {
        Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(persistentClass, StringUtils.uncapitalize(persistentClass.getSimpleName()));
        finder.prepareCriteria(criteria, persistentClass);
        return criteria.list();
    }

    public T findWithFinderUnique(Finder finder) {
        Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(persistentClass);
        finder.prepareCriteria(criteria, persistentClass);
        return (T) criteria.uniqueResult();
    }

    public Integer count() {
        Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(persistentClass);
        criteria.setProjection(Projections.rowCount());
        return (Integer) ((Number) (criteria.list().get(0))).intValue();
    }

    public void setPersistentClass(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }
}
