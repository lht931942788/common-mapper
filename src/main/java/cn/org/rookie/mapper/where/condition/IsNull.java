package cn.org.rookie.mapper.where.condition;

import cn.org.rookie.mapper.where.Condition;

public class IsNull extends Condition {

    public IsNull(String columnName) {
        super(columnName);
    }

    @Override
    public String render() {
        return columnName + " is null";
    }
}
