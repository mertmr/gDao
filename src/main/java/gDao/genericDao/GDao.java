package gDao.genericDao;

import gDao.util.Finder;

import java.util.Collection;
import java.util.List;

/**
 * persistentClass: Mert Meral
 * @param <T> Daoda kullanilacak model sinifi
 */

public interface GDao<T> {

    public Object save(T object);

    public void saveOrUpdate(T object) throws Exception;

    public void saveOrUpdateAll(Collection<T> entityList);

    public void update(T object);

    public void delete(T object);

    public List<T> findAll();

    public List<T> findAll(final String orderColumn);

    public List<T> findByColumn(String column, Object criteria);

    /**
     * Same with findByColumn in methodology, return one result(null if not found)
     * @param column
     * @param criteria
     * @return One result or null, according to filters
     */
    public T findByColumnUnique(String column, Object criteria);

    /**
     * With using Finder filters, searching through database is easier
     * @param finder Use Finder's filters to get the result
     * @return List of the results according to filters
     */
    public List<T> findWithFinder(Finder finder);

    /**
     * Same with findWithFinder in methodology, return one result(null if not found)
     * @param finder
     * @return One result or null, according to filters
     */
    public T findWithFinderUnique(Finder finder);

    public Integer count();

    public void setPersistentClass(Class<T> persistentClass);
}
