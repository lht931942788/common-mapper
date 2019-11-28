package cn.org.rookie.mapper.sql;

import cn.org.rookie.mapper.table.AssociationInfo;
import cn.org.rookie.mapper.table.ColumnInfo;
import cn.org.rookie.mapper.table.JoinColumnInfo;
import cn.org.rookie.mapper.table.TableInfo;
import org.apache.ibatis.jdbc.SQL;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLMaker {

    private final Map<String, String> SQL_MAP = new HashMap<>();
    private final TableInfo tableInfo;
    private final Class<?> type;
    private String key;
    private String where;

    public SQLMaker(Class<?> type) {
        this.tableInfo = new TableInfo(type);
        this.type = type;
    }

    public SQLMaker insert() {
        return changeKey("insert");
    }

    public SQLMaker delete() {
        return changeKey("delete");
    }

    public SQLMaker update() {
        return changeKey("update");
    }

    public SQLMaker select() {
        return changeKey("select");
    }

    private String generateInsert() {
        List<ColumnInfo> columns = tableInfo.getColumns();
        SQL sql = new SQL();
        sql.INSERT_INTO(tableInfo.getTableName());
        for (ColumnInfo columnInfo : columns) {
            sql.VALUES(columnInfo.getColumnName(), "#{" + columnInfo.getFieldName() + "}");
        }
        return sql.toString();
    }

    private String generateDelete() {
        return new SQL().DELETE_FROM(tableInfo.getTableName()).toString();
    }

    private String generateUpdate() {
        List<ColumnInfo> columns = tableInfo.getColumns();
        SQL sql = new SQL();
        sql.UPDATE(tableInfo.getTableName());
        for (ColumnInfo columnInfo : columns) {
            sql.SET(columnInfo.getColumnName() + " = #{" + columnInfo.getFieldName() + "}");
        }
        return sql.toString();
    }

    private String generateSelect() {
        SQL sql = new SQL();
        String tableName = tableInfo.getTableName();
        for (ColumnInfo columnInfo : tableInfo.getColumns()) {
            sql.SELECT(tableName + "." + columnInfo.getColumnName() + " \"" + columnInfo.getFieldName() + "\"");
        }
        sql.FROM(tableName);

        for (JoinColumnInfo joinColumnInfo : tableInfo.getJoinColumns()) {
            sql.FROM(joinColumnInfo.getTableName());
            sql.SELECT(joinColumnInfo.getTableName() + "." + joinColumnInfo.getColumnName() + " \"" + joinColumnInfo.getFieldName() + "\"");
            List<AssociationInfo> associations = joinColumnInfo.getAssociations();
            for (AssociationInfo associationInfo : associations) {
                sql.WHERE(tableName + "." + associationInfo.getTarget() + " = " + joinColumnInfo.getTableName() + "." + associationInfo.getAssociation());
            }
        }
        return sql.toString();
    }

    public SQLMaker byPrimary() {
        where = "where " + tableInfo.getTableName() + "." + tableInfo.getPrimaryInfo().getColumnName() + " = #{id}";
        return this;
    }

    public SQLMaker where(String where) {
        this.where += where;
        return this;
    }

    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public Class<?> getType() {
        return type;
    }

    private SQLMaker changeKey(String key) {
        where = " where 1 = 1";
        this.key = key;
        return this;
    }

    private String getSql() {
        String sql = SQL_MAP.get(key);
        if (sql == null) {
            switch (key) {
                case "insert":
                    sql = generateInsert();
                    break;
                case "delete":
                    sql = generateDelete();
                    break;
                case "update":
                    sql = generateUpdate();
                    break;
                case "select":
                    sql = generateSelect();
                    break;
            }
            SQL_MAP.put(key, sql);
        }
        return sql;
    }

    public String build() {
        String sql = getSql();
        if (sql.contains("where")) {
            return sql + where;
        }
        return sql + where.replace("where 1 = 1 and", "where").replace("where 1 = 1 or", "where").replace("where 1 = 1", "");
    }
}
