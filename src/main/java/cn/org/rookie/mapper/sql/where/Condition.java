package cn.org.rookie.mapper.sql.where;

public abstract class Condition {
    protected String columnName;
    protected Boolean isAnd;
    protected String prefix = "#";

    public Condition(String columnName) {
        this.columnName = columnName;
    }

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

}
