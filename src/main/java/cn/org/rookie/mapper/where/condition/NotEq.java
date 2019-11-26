package cn.org.rookie.mapper.where.condition;

import cn.org.rookie.mapper.where.SingleCondition;

public class NotEq extends SingleCondition {
    public NotEq(String columnName) {
        super(columnName);
    }

    @Override
    public String getTerm() {
        return "!=";
    }
}
