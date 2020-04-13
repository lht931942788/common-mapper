package cn.org.rookie.mapper.table;

import cn.org.rookie.mapper.annotation.Association;
import cn.org.rookie.mapper.annotation.JoinColumn;
import cn.org.rookie.mapper.utils.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class JoinColumnInfo {

    private final String tableName;
    private final String fieldName;
    private final String columnName;
    private final List<AssociationInfo> associations = new ArrayList<>();

    public JoinColumnInfo(Field field) {
        JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
        tableName = joinColumn.tableName();
        fieldName = field.getName();
        if ("".equals(joinColumn.column())) {
            columnName = StringUtils.camelCaseToUnderscore(fieldName);
        } else {
            columnName = joinColumn.column();
        }
        Association[] associations = joinColumn.relations();
        for (Association association : associations) {
            this.associations.add(new AssociationInfo(association));
        }
    }

    public String getTableName() {
        return tableName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getColumnName() {
        return columnName;
    }

    public List<AssociationInfo> getAssociations() {
        return associations;
    }
}
