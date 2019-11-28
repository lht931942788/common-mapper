package cn.org.rookie.mapper.table;

import cn.org.rookie.mapper.annotation.Column;
import cn.org.rookie.utils.StringUtils;

import java.lang.reflect.Field;

public class ColumnInfo {

    private String fieldName;
    private String columnName;
    private boolean isOrder;
    private String orderType;

    public ColumnInfo(Field field) {
        Column column = field.getAnnotation(Column.class);
        fieldName = field.getName();
        if (column == null || "".equals(column.value())) {
            columnName = StringUtils.camelCaseToUnderscore(field.getName());
        } else {
            columnName = column.value();
            isOrder = column.order();
            orderType = column.orderType();
        }
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public boolean isOrder() {
        return isOrder;
    }

    public void setOrder(boolean order) {
        isOrder = order;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
