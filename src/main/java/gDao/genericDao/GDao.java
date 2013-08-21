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

import java.util.Collection;
import java.util.List;

/**
 * @author Mert Meral
 * @since 0.0.1
 * Interface of GDao. Generic functions are provided through this interface.
 * @param <T> Persistent entity class that is to be used
 */

public interface GDao<T> {

    /**
     * Object will be persisted to database. Be carefull, object entity class must implement Serializable
     * @return Object will return with the generated id. You can use this id according to your business logic
     */
    public Object save(T object);

    /**
     * Objects id will selected from database. If id found, update operation will be done. Otherwise save operation will
     * take place
     */
    public void saveOrUpdate(T object) throws Exception;

    /**
     * saveOrUpdate operation for collections
     */
    public void saveOrUpdateAll(Collection<T> entityList);

    /**
     * Update operation. Requires id in the object
     */
    public void update(T object);

    /**
     * Delete operation
     */
    public void delete(T object);

    /**
     * Returns the list of table, all records
     */
    public List<T> findAll();

    /**
     * Returns the list of table, all records, ordered by column
     */
    public List<T> findAll(final String orderColumn);

    /**
     * Find result according to parameters.
     * <h1>Example:</h1>
     * Lets say you have address to search in Student table. Student table have column name "address"  <p>
     * List<Student> studentList = studentDao.findByColumn("address", "Orlando Street no:1");
     * @param column Define the column name you want to search
     * @param criteria Define the value you want to search for the column name
     *
     */
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
     * <h1>Example:</h1>
     * Finder finder = new Finder(); <br>
     * finder.addFilterEqual("address", "kozyatagi"); <br>
     * finder.addSortAsc("address"); <br>
     * List<Student> studentList = studentGDao.findWithFinder(finder);
     * @param finder As you can see from the example, build an instance of Finder class and use the filters
     * @return List of the results according to filters
     */
    public List<T> findWithFinder(Finder finder);

    /**
     * Same with findWithFinder in methodology, return one result(null if not found)
     * @param finder
     * @return One result or null, according to filters
     */
    public T findWithFinderUnique(Finder finder);

    /**
     * @return Return the count of records at the table as an integer number
     */
    public Integer count();

    /**
     * Used by framework usually. Detailed configurations may be provided in the future
     * @param persistentClass Entity class
     */
    public void setPersistentClass(Class<T> persistentClass);
}
