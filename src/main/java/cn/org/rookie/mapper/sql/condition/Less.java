package cn.org.rookie.mapper.sql.condition;

import cn.org.rookie.mapper.sql.SingleCondition;

public class Less extends SingleCondition {

    @Override
    public String getTerm() {
        return "&lt;";
    }
}
