package cn.org.rookie.mapper.where.condition;

import cn.org.rookie.mapper.where.SingleCondition;

public class Lt extends SingleCondition {
    public Lt(String columnName) {
        super(columnName);
    }

    @Override
    public String getTerm() {
        return "&lt;";
    }
}
