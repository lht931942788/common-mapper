package cn.org.rookie.mapper.sql.where;

public abstract class SingleCondition extends Condition {

    public SingleCondition(String columnName) {
        super(columnName);
    }


    public String render() {
        return columnName + " " + getTerm() + " " + getPrefix() + "{wrapper.params." + columnName + "}";
    }

    public abstract String getTerm();
}
