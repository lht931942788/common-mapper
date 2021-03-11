package cn.org.rookie.mapper.entity;

import cn.org.rookie.mapper.annotation.Column;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            columnName = humpToUnderline(field.getName());
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

    private static String humpToUnderline(String str) {
        Matcher matcher = Pattern.compile("[A-Z]").matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private static String underlineToHump(String str) {
        Matcher matcher = Pattern.compile("_(\\w)").matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
