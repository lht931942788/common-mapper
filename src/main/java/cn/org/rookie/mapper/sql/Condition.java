package cn.org.rookie.mapper.sql;

public abstract class Condition {
    protected String columnName;
    protected Boolean isAnd;
    protected String prefix = "#";

    public Boolean isAnd() {
        return isAnd;
    }

    public void setIsAnd(boolean isAnd) {
        this.isAnd = isAnd;
    }

    public abstract String render();

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}
