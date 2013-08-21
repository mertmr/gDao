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
package gDao.util;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mert Meral
 * @since 0.0.2
 * We can say search customizer in short. You can add filters and sorters. The relation logic is also build up here.
 */
public class Finder {
    private int maxResult;
    private List<Criterion> criterionList;
    private List<Order> orderList;
    private List<Filter> relationList;

    public Finder() {
        criterionList = new ArrayList<Criterion>();
        orderList = new ArrayList<Order>();
        relationList = new ArrayList<Filter>();
        maxResult = 0;
    }

    /**
     * Search column by value. Similar to GDao function, findByColumn
     * @param column Define the column name you want to search
     * @param value Define the value you want to search for the column name
     */
    public void addFilterEqual(String column, Object value) {
        controlRelationAndAddToList(criterionList, Restrictions.eq(trimColumn(column), value), column);
    }

    /**
     * According to column, fetch the results equal or lower than the value
     */
    public void addFilterLessThanOrEqual(String column, Object value) {
        controlRelationAndAddToList(criterionList, Restrictions.le(trimColumn(column), value), column);
    }

    /**
     * Fetch the results ascended sorted
     */
    public void addSortAsc(String column) {
        controlRelationAndAddToList(orderList, Order.asc(trimColumn(column)), column);
    }

    /**
     * Fetch the results descended sorted
     */
    public void addSortDesc(String column) {
        controlRelationAndAddToList(orderList, Order.desc(column), column);
    }

    /**
     * Set the maximum number of records that will be fetched
     */
    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    /**
     * Used by gDao framework. Prepare filter, sort and relation lists before searching with hibernate
     */
    public void prepareCriteria(Criteria criteria, Class persistentClass) {
        for (Criterion criterion : criterionList)
            criteria.add(criterion);

        for (Order order : orderList)
            criteria.addOrder(order);

        for (Filter filter : relationList) {
            String[] joinedColumns = filter.getJoinedColumns();
            for (int i = 0, joinedColumnsLength = joinedColumns.length - 1; i < joinedColumnsLength; i++) {
                String relatedColumn = joinedColumns[i];
                String firstJoin;
                String persistentClassName = StringUtils.uncapitalize(persistentClass.getSimpleName());

                if (joinedColumns[i].equals(persistentClassName))
                    relatedColumn = joinedColumns[i + 1];

                if (i == 0)
                    firstJoin = persistentClassName;
                else
                    firstJoin = joinedColumns[i - 1];

                criteria.createCriteria(firstJoin + "." + relatedColumn, relatedColumn);
            }
        }


        if (maxResult != 0)
            criteria.setMaxResults(maxResult);

    }

    private void controlRelationAndAddToList(List list, Object expression, String column) {
        if (column.contains(".")) {
            String[] tables = column.split("\\.");
            relationList.add(new Filter(column, tables));
        }
        list.add(expression);
    }

    private String trimColumn(String column) {
        if (column.contains(".")) {
            String[] tables = column.split("\\.");
            if (tables.length > 2)
                column = tables[tables.length - 2] + "." +
                        tables[tables.length - 1]; //son ikiyi don
        }
        return column;
    }
}
