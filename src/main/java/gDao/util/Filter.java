package gDao.util;

/**
 * Created with IntelliJ IDEA.
 * User: mmeral
 * Date: 5/7/13
 * Time: 9:57 AM
 */
public class Filter {
    private String column;
    private Object value;
    private String relation;
    private String[] joinedColumns;

    public Filter(String column, Object value) {
        this.column = column;
        this.value = value;
    }

    public Filter(String column, String[] joinedColumns){
        this.column = column;
        this.joinedColumns = joinedColumns;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String[] getJoinedColumns() {
        return joinedColumns;
    }

    public void setJoinedColumns(String[] joinedColumns) {
        this.joinedColumns = joinedColumns;
    }

    public Object getValue() {

        return value;
    }

    public void setValue(Object value) {
        this.value = this.value;
    }
}
