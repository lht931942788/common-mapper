package cn.org.rookie.mapper.sql.condition;

import cn.org.rookie.mapper.sql.Condition;

public class IsNull extends Condition {

    @Override
    public String render() {
        return columnName + " IS NULL";
    }
}
