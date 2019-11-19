package cn.org.rookie.mapper.table;

import cn.org.rookie.mapper.annotation.JoinColumn;
import cn.org.rookie.mapper.annotation.Primary;
import cn.org.rookie.mapper.annotation.Table;
import cn.org.rookie.mapper.annotation.Transient;
import cn.org.rookie.tools.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TableInfo {

    private Class type;
    private String tableName;
    private PrimaryInfo primaryInfo;
    private List<ColumnInfo> columns = new ArrayList<>();
    private List<JoinColumnInfo> joinColumns = new ArrayList<>();

    public TableInfo(Class type) {
        this.type = type;
        init();
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public PrimaryInfo getPrimaryInfo() {
        return primaryInfo;
    }

    public void setPrimaryInfo(PrimaryInfo primaryInfo) {
        this.primaryInfo = primaryInfo;
    }

    public List<ColumnInfo> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnInfo> columns) {
        this.columns = columns;
    }

    public List<JoinColumnInfo> getJoinColumns() {
        return joinColumns;
    }

    public void setJoinColumns(List<JoinColumnInfo> joinColumns) {
        this.joinColumns = joinColumns;
    }

    public TableInfo init() {
        Annotation annotation = type.getAnnotation(Table.class);
        if (annotation instanceof Table) {
            tableName = StringUtils.camelCaseToUnderscore(((Table) annotation).value());
        }
        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            Transient isTransient = field.getAnnotation(Transient.class);
            if (isTransient == null) {
                if (field.getAnnotation(Primary.class) != null) {
                    primaryInfo = new PrimaryInfo(field);
                }
                if (field.getAnnotation(JoinColumn.class) != null) {
                    JoinColumnInfo joinColumnInfo = new JoinColumnInfo(field);
                    joinColumns.add(joinColumnInfo);
                } else {
                    ColumnInfo columnInfo = new ColumnInfo(field);
                    columns.add(columnInfo);
                }
            }
        }
        return this;
    }
}
