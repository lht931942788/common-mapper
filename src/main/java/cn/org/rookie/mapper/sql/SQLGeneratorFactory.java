package cn.org.rookie.mapper.sql;

public class SQLGeneratorFactory {

    private static SQLGenerator sqlGenerator;
    private static String databaseId;

    public static SQLGenerator build(String databaseId) {
        if (SQLGeneratorFactory.databaseId == null) {
            SQLGeneratorFactory.databaseId = databaseId;
        }
        if (!SQLGeneratorFactory.databaseId.equals(databaseId)) {
            SQLGeneratorFactory.databaseId = databaseId;
            build();
        }
        if (sqlGenerator == null) {
            build();
        }
        return sqlGenerator;
    }

    private static void build() {
        switch (databaseId) {
            case "oracle":
                sqlGenerator = new OracleSQLGenerator();
            case "mysql":
                sqlGenerator = new MySQLGenerator();
            default:
                sqlGenerator = new MySQLGenerator();
        }
    }
}
