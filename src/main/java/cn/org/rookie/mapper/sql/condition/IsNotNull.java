package cn.org.rookie.mapper.sql.condition;

import cn.org.rookie.mapper.sql.Condition;

public class IsNotNull extends Condition {

    @Override
    public String render() {
        return columnName + " IS NOT NULL";
    }

}
