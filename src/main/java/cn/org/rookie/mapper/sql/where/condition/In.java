package cn.org.rookie.mapper.sql.where.condition;

import cn.org.rookie.mapper.sql.where.Condition;

public class In extends Condition {
    public In(String columnName) {
        super(columnName);
    }

    @Override
    public String render() {
        return columnName + " in (${wrapper.params." + columnName + "})";
    }
}