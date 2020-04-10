package com.github.commonmapper.mapper.table;

import com.github.commonmapper.utils.StringUtils;
import com.github.commonmapper.mapper.annotation.*;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TableInfo {

    private final Logger log = LoggerFactory.getLogger(TableInfo.class);

    private String tableName;
    private PrimaryInfo primaryInfo;
    private List<ColumnInfo> columns = new ArrayList<>();
    private List<JoinColumnInfo> joinColumns = new ArrayList<>();

    public TableInfo(Class type) {
        Annotation table = type.getAnnotation(Table.class);
        if (table instanceof Table) {
            tableName = StringUtils.camelCaseToUnderscore(((Table) table).value());
        } else {
            tableName = type.getSimpleName();
        }
        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            Primary primary = field.getAnnotation(Primary.class);
            if (primary != null) {
                primaryInfo = new PrimaryInfo(field);
                break;
            }
        }
        if (primaryInfo == null) {
            log.warn(() -> type.getName() + " has no primary key configured");
        }
        for (Field field : fields) {
            Transient isTransient = field.getAnnotation(Transient.class);
            JoinTable joinTable = field.getAnnotation(JoinTable.class);
            if (isTransient == null && joinTable == null) {
                Primary primary = field.getAnnotation(Primary.class);
                if (primary == null) {
                    if (field.getAnnotation(JoinColumn.class) != null) {
                        JoinColumnInfo joinColumnInfo = new JoinColumnInfo(field);
                        joinColumns.add(joinColumnInfo);
                    } else {
                        ColumnInfo columnInfo = new ColumnInfo(field);
                        columns.add(columnInfo);
                    }
                }
            }
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
