package cn.org.rookie.mapper.entity;

import cn.org.rookie.mapper.annotation.*;
import cn.org.rookie.mapper.utils.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TableInfo {

    private final String tableName;
    private PrimaryInfo primaryInfo;
    private final List<ColumnInfo> columns = new ArrayList<>();
    private final List<JoinColumnInfo> joinColumns = new ArrayList<>();

    public TableInfo(Class type) {
        Annotation table = type.getAnnotation(Table.class);
        if (table instanceof Table) {
            tableName = StringUtils.camelCaseToUnderscore(((Table) table).value());
        } else {
            tableName = type.getSimpleName();
        }
        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            Transient isTransient = field.getAnnotation(Transient.class);
            JoinTable joinTable = field.getAnnotation(JoinTable.class);
            if (isTransient == null && joinTable == null) {
                if (field.getAnnotation(JoinColumn.class) != null) {
                    JoinColumnInfo joinColumnInfo = new JoinColumnInfo(field);
                    joinColumns.add(joinColumnInfo);
                } else {
                    ColumnInfo columnInfo = new ColumnInfo(field);
                    if (columnInfo.isPrimary()) {
                        primaryInfo = new PrimaryInfo(field);
                    } else {
                        columns.add(columnInfo);
                    }
                }
            }
        }
        if (primaryInfo == null) {
            throw new RuntimeException(type.getName() + " has no primary key configured");
        }
    }

    public String getTableName() {
        return tableName;
    }

    public PrimaryInfo getPrimaryInfo() {
        return primaryInfo;
    }

    public List<ColumnInfo> getColumns() {
        return columns;
    }

    public List<JoinColumnInfo> getJoinColumns() {
        return joinColumns;
    }

}
