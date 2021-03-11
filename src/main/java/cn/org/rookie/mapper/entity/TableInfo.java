package cn.org.rookie.mapper.entity;

import cn.org.rookie.mapper.annotation.JoinColumn;
import cn.org.rookie.mapper.annotation.Table;
import cn.org.rookie.mapper.annotation.Transient;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class TableInfo {

    private final String tableName;
    private final List<ColumnInfo> columns = new ArrayList<>();
    private final List<JoinColumnInfo> joinColumns = new ArrayList<>();
    private PrimaryInfo primaryInfo;

    public TableInfo(Class<?> type) {
        Table table = type.getAnnotation(Table.class);
        if (table != null) {
            tableName = humpToUnderline(table.value());
        } else {
            tableName = type.getSimpleName();
        }
        Field[] fields = Stream.concat(Stream.of(type.getDeclaredFields()), Stream.of(type.getSuperclass().getDeclaredFields())).toArray(Field[]::new);
        for (Field field : fields) {
            Transient isTransient = field.getAnnotation(Transient.class);
            if (isTransient == null) {
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
