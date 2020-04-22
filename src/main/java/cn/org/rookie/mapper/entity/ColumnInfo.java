package cn.org.rookie.mapper.entity;

import cn.org.rookie.mapper.annotation.Column;
import cn.org.rookie.mapper.utils.StringUtils;

import java.lang.reflect.Field;

public class ColumnInfo {

    private final String fieldName;
    private final String columnName;
    private boolean primary;
    private Boolean isOrder = false;
    private String orderType = "aes";

    public ColumnInfo(Field field) {
        Column column = field.getAnnotation(Column.class);
        fieldName = field.getName();
        if (column == null || "".equals(column.value())) {
            columnName = StringUtils.camelCaseToUnderscore(field.getName());
            if (column != null) {
                primary = column.primary();
            }
        } else {
            columnName = column.value();
            primary = column.primary();
            isOrder = column.order();
            orderType = column.orderType();
        }
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getColumnName() {
        return columnName;
    }

    public boolean isPrimary() {
        return primary;
    }

    public boolean isOrder() {
        return isOrder;
    }

    public String getOrderType() {
        return orderType;
    }

}
