package cn.org.rookie.mapper.provider;

import cn.org.rookie.mapper.annotation.*;
import cn.org.rookie.mapper.table.ColumnInfo;
import cn.org.rookie.mapper.table.JoinColumnInfo;
import cn.org.rookie.mapper.table.PrimaryInfo;
import cn.org.rookie.mapper.table.TableInfo;
import cn.org.rookie.tools.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@SuppressWarnings("ALL")
public class ParsedTableInfo {

    private static final ConcurrentMap<String, TableInfo> PARSED_TABLE_INFO = new ConcurrentHashMap<>();

    public static TableInfo getTableInfo(Class type) {
        String name = type.getName();
        TableInfo tableInfo = PARSED_TABLE_INFO.get(name);
        if (tableInfo == null) {
            tableInfo = parse(type);
            PARSED_TABLE_INFO.put(name, tableInfo);
            return tableInfo;
        }
        return tableInfo;
    }

    public static TableInfo parse(Class type) {
        TableInfo tableInfo = new TableInfo();
        tableInfo.setType(type);
        Table table = (Table) type.getAnnotation(Table.class);
        String tableName;
        if (table != null) {
            tableName = table.value();
        } else {
            tableName = type.getSimpleName();
        }
        tableInfo.setTableName(tableName);
        Field[] fields = type.getDeclaredFields();
        List<ColumnInfo> columnInfos = new ArrayList<>();
        List<JoinColumnInfo> joinColumnInfos = new ArrayList<>();
        List<String> columnNames = new ArrayList<>();
        List<String> fieldNames = new ArrayList<>();
        for (Field field : fields) {
            Transient exist = field.getAnnotation(Transient.class);
            if (exist == null) {
                JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
                String name = field.getName();
                if (joinColumn == null) {
                    Column column = field.getAnnotation(Column.class);
                    Primary primary = field.getAnnotation(Primary.class);
                    ColumnInfo columnInfo = new ColumnInfo();
                    String columnName;
                    String fieldName;
                    if (column == null) {
                        columnName = StringUtils.camelCaseToUnderscore(name);
                        fieldName = name;
                        columnInfo.setColumnName(columnName);
                        columnInfo.setFieldName(name);
                        columnNames.add(columnName);
                        fieldNames.add("#{" + fieldName + "}");
                    } else {
                        columnName = column.value();
                        fieldName = name;
                        if (columnName.isEmpty()) {
                            columnName = name;
                        }
                        columnInfo.setOrder(column.order());
                        columnInfo.setOrderType(column.orderType());
                        columnInfo.setColumnName(columnName);
                        columnInfo.setFieldName(fieldName);
                        columnNames.add(columnName);
                        fieldNames.add("#{" + fieldName + "}");
                    }
                    if (primary != null) {
                        PrimaryInfo primaryInfo = new PrimaryInfo(columnName, fieldName);
                        tableInfo.setPrimary(primaryInfo);
                        columnInfo.setPrimary(true);
                    }
                    columnInfos.add(columnInfo);
                } else {
                    String joinTableName = joinColumn.tableName();
                    JoinColumnInfo joinColumnInfo = new JoinColumnInfo();
                    joinColumnInfo.setTableName(joinTableName);
                    joinColumnInfo.setFieldName(name);
                    String[] targets = joinColumn.targets();
                    if (targets.length > 0) {
                        String[] relations = joinColumn.relations();
                        int targetsLength = targets.length;
                        int relationsLength = relations.length;
                        String[] wheres = new String[targetsLength];
                        if (targetsLength != relationsLength) {
                            throw new RuntimeException("配置有误！");
                        }
                        for (int i = 0; i < targetsLength; i++) {
                            wheres[i] = tableName + "." + targets[i] + " = " + joinTableName + "." + relations[i];
                        }
                        joinColumnInfo.setWheres(wheres);
                    }
                    String column = joinColumn.column();
                    if (column.isEmpty()) {
                        joinColumnInfo.setColumnName(StringUtils.camelCaseToUnderscore(name));
                    } else {
                        joinColumnInfo.setColumnName(column);
                    }
                    joinColumnInfos.add(joinColumnInfo);
                }
            }
        }
        tableInfo.setColumns(columnInfos);
        tableInfo.setJoinTables(joinColumnInfos);
        tableInfo.setColumnNames(columnNames.toArray(new String[0]));
        tableInfo.setFieldNames(fieldNames.toArray(new String[0]));
        return tableInfo;
    }
}
