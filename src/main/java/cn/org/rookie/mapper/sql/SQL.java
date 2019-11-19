package cn.org.rookie.mapper.sql;

import cn.org.rookie.mapper.table.AssociationInfo;
import cn.org.rookie.mapper.table.ColumnInfo;
import cn.org.rookie.mapper.table.JoinColumnInfo;
import cn.org.rookie.mapper.table.TableInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQL {

    private static final String INSERT = "insert into %s (%s) values (%s)";
    private static final String DELETE = "delete from %s";
    private static final String UPDATE = "update %s set %s";
    private static final String SELECT = "select %s from %s %s";
    private final Map<String, String> SQL_MAP = new HashMap<>();
    private TableInfo tableInfo;
    private String key;
    private Class type;
    private String where;

    public SQL(Class type) {
        this.tableInfo = new TableInfo(type);
        this.type = type;
    }

    public SQL insert() {
        return changeKey("insert");
    }

    public SQL delete() {
        return changeKey("delete");
    }

    public SQL update() {
        return changeKey("update");
    }

    public SQL select() {
        return changeKey("select");
    }

    public String generateInsert() {
        List<ColumnInfo> columns = tableInfo.getColumns();
        StringBuilder value = new StringBuilder();
        StringBuilder values = new StringBuilder();
        for (ColumnInfo columnInfo : columns) {
            value.append(columnInfo.getColumnName()).append(", ");
            values.append("#{").append(columnInfo.getFieldName()).append("}, ");
        }
        return String.format(INSERT, tableInfo.getTableName(), value.substring(0, value.length() - 2), values.substring(0, values.length() - 2));
    }

    public String generateDelete() {
        return String.format(DELETE, tableInfo.getTableName());
    }

    public String generateUpdate() {
        List<ColumnInfo> columns = tableInfo.getColumns();
        StringBuilder set = new StringBuilder();
        for (ColumnInfo columnInfo : columns) {
            set.append(columnInfo.getColumnName()).append(" = ").append("#{").append(columnInfo.getFieldName()).append("}, ");
        }
        return String.format(UPDATE, tableInfo.getTableName(), set.substring(0, set.length() - 2));
    }

    public String generateSelect() {
        StringBuilder columns = new StringBuilder();
        String tableName = tableInfo.getTableName();
        StringBuilder tableNames = new StringBuilder(tableName + ", ");
        StringBuilder where = new StringBuilder();
        for (ColumnInfo columnInfo : tableInfo.getColumns()) {
            columns.append(tableName).append(".").append(columnInfo.getColumnName()).append(" ").append("\"").append(columnInfo.getFieldName()).append("\"").append(", ");
        }

        for (JoinColumnInfo joinColumnInfo : tableInfo.getJoinColumns()) {
            tableNames.append(joinColumnInfo.getTableName()).append(", ");
            columns.append(joinColumnInfo.getTableName()).append(".").append(joinColumnInfo.getColumnName()).append(" ").append("\"").append(joinColumnInfo.getFieldName()).append("\"").append(", ");
            List<AssociationInfo> associations = joinColumnInfo.getAssociations();
            for (AssociationInfo associationInfo : associations) {
                where.append(tableName).append(".").append(associationInfo.getTarget()).append(" = ")
                        .append(joinColumnInfo.getTableName()).append(".").append(associationInfo.getAssociation()).append(" and ");
            }
        }

        if (where.length() > 0) {
            return String.format(SELECT, columns.substring(0, columns.length() - 2), tableNames.substring(0, tableNames.length() - 2), "where " + where.substring(0, where.length() - 5));
        }
        return String.format(SELECT, columns.substring(0, columns.length() - 2), tableNames.substring(0, tableNames.length() - 2), where);
    }

    public SQL byPrimary() {
        where = tableInfo.getTableName() + "." + tableInfo.getPrimaryInfo().getColumnName() + " = #{id}";
        return this;
    }

    public SQL where(Wrapper wrapper) {
        if (wrapper != null) {
            List<Condition> conditions = wrapper.getConditions();
            StringBuilder sqlBuilder = new StringBuilder();
            for (Condition condition : conditions) {
                String where = condition.getColumnName() + condition.getSql().replace("%s", condition.getPrefix() + "{wrapper.params." + condition.getColumnName() + "}");
                if (condition.isAnd()) {
                    sqlBuilder.append(" and ").append(where);
                } else {
                    sqlBuilder.append(" or ").append(where);
                }
            }
            where = where + sqlBuilder.toString();
        }
        return this;
    }

    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public void setTableInfo(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    private SQL changeKey(String key) {
        where = " where 1 = 1";
        this.key = key;
        return this;
    }

    public String getSql() {
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
        return sql + where.replace("where 1 = 1 and", "where").replace("where 1 = 1 or", "where").replace("where 1 = 1", "");
    }
}
