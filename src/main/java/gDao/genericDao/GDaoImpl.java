/*
 * Copyright 2013 Mert Meral
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
 * @author Mert Meral
 * @since 0.0.1
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

    /**
     * If SimpleDao annotation is used, persistent class will be initialized through constructor by using
     * ParameterizedType class
     */
    public GDaoImpl() {
        try {
            ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
            persistentClass = (Class<T>) type.getActualTypeArguments()[0];
        } catch (Exception e) {
            logger.debug("Persistent class will be initialized manually by user");
        }
    }

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
        return ((Number) (criteria.list().get(0))).intValue();
    }

    public void setPersistentClass(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }
}
