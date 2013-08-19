package gDao.util;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mmeral
 * Date: 5/7/13
 * Time: 9:51 AM
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

    public void addFilterEqual(String column, Object object) {
        controlRelationAndAddToList(criterionList, Restrictions.eq(trimColumn(column), object), column);
    }

    public void addFilterLessThanOrEqual(String column, Object object) {
        controlRelationAndAddToList(criterionList, Restrictions.le(trimColumn(column), object), column);
    }

    public void addSortAsc(String column) {
        controlRelationAndAddToList(orderList, Order.asc(trimColumn(column)), column);
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

    public void addSortDesc(String column) {
        controlRelationAndAddToList(orderList, Order.desc(column), column);
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    private void controlRelationAndAddToList(List list, Object expression, String column) {
        if (column.contains(".")) {
            String[] tables = column.split("\\.");
//            tables = (String[]) ArrayUtils.remove(tables, tables.length - 1);
            relationList.add(new Filter(column, tables));
        }
        list.add(expression);
    }

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
}
