package cn.org.rookie.mapper.sql;

public class Condition {

    private String columnName;
    private String sql;
    private String prefix = "#";
    private boolean isAnd = true;

    public Condition(String columnName, String sql, boolean isAnd) {
        this.columnName = columnName;
        this.sql = sql;
        this.isAnd = isAnd;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public boolean isAnd() {
        return isAnd;
    }

    public void setAnd(boolean and) {
        isAnd = and;
    }
}
