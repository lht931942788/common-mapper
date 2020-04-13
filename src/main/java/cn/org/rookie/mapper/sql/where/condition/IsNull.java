package cn.org.rookie.mapper.sql.where.condition;

import cn.org.rookie.mapper.sql.where.Condition;

public class IsNull extends Condition {

    public IsNull(String columnName) {
        super(columnName);
    }

    @Override
    public String render() {
        return columnName + " IS NULL";
    }
}
