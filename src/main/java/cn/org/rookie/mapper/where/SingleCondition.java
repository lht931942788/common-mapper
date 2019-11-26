package cn.org.rookie.mapper.where;

public abstract class SingleCondition extends Condition {

    protected String prefix = "#";

    public SingleCondition(String columnName) {
        super(columnName);
    }


    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String render() {
        return columnName + " " + getTerm() + " " + getPrefix() + "{wrapper.params." + columnName + "}";
    }

    public abstract String getTerm();
}
