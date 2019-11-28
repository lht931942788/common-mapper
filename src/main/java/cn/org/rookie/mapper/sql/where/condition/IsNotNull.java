package cn.org.rookie.mapper.sql.where.condition;

import cn.org.rookie.mapper.sql.where.Condition;

public class IsNotNull extends Condition {

    public IsNotNull(String columnName) {
        super(columnName);
    }

    @Override
    public String render() {
        return columnName + " is not null";
    }

}
