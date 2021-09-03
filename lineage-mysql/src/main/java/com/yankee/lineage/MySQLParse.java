package com.yankee.lineage;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.stat.TableStat.Name;
import com.alibaba.druid.util.JdbcConstants;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * @author Yankee
 * @program yankee-lineage
 * @description
 * @since 2021/9/2
 */
public class MySQLParse {
    private static String sql = "insert into table_a (id, name, age) select b.id, b.name, c.age from table_b b inner join table_c c on b.id = c.id;" +
            "insert into table_d (id, name, age) select e.id, e.name, f.age from table_e e inner join table_f f on e.id = f.id;";

    /**
     * 解析statement
     * @param sql sql语句
     * @param dbType 数据库类型
     * @return list
     */
    public static List<SQLStatement> parseSql(String sql, DbType dbType) {
        List<SQLStatement> statements = SQLUtils.parseStatements(sql, dbType);
        return statements;
    }

    public static void parseStatement(List<SQLStatement> statements) {
        for (SQLStatement statement : statements) {
            if (statement instanceof SQLInsertStatement) {
                SQLInsertStatement insertStatement = (SQLInsertStatement) statement;
                System.out.println("insert:" + insertStatement.getChildren().get(0).getParent());
                System.out.println("insert:" + insertStatement.getQuery());
            } else if (statement instanceof SQLCreateStatement) {
                SQLCreateStatement createStatement = (SQLCreateStatement) statement;
                System.out.println("insert:" + createStatement);
            }
        }
    }

    public static void getFromTo(String sql) {
        List<SQLStatement> statements = SQLUtils.parseStatements(sql, JdbcConstants.MYSQL);
        TreeSet<String> fromSet = new TreeSet<>();
        TreeSet<String> toSet = new TreeSet<>();

        String database = "";
        for (SQLStatement statement : statements) {
            SchemaStatVisitor statVisitor = SQLUtils.createSchemaStatVisitor(JdbcConstants.MYSQL);
            if (statement instanceof SQLUseStatement) {
                database = ((SQLUseStatement) statement).getDatabase().getSimpleName().toUpperCase();
            }
            statement.accept(statVisitor);
            Map<Name, TableStat> tables = statVisitor.getTables();
            if (tables != null) {
                final String db = database;
                tables.forEach((tableName, stat) -> {
                    if (stat.getCreateCount() > 0 || stat.getInsertCount() > 0) {
                        String to = tableName.getName().toUpperCase();
                        if (!to.contains(".")) {
                            to = db + "." + to;
                        }
                        toSet.add(to);
                    } else if (stat.getSelectCount() > 0) {
                        String from = tableName.getName().toUpperCase();
                        if (!from.contains(".")) {
                            from = db + "." + from;
                        }
                        fromSet.add(from);
                    }
                });
            }
        }

        for (SQLStatement statement : statements) {
            SchemaStatVisitor statVisitor = SQLUtils.createSchemaStatVisitor(JdbcConstants.MYSQL);
            statement.accept(statVisitor);
            System.out.println(statVisitor.getColumns());
        }

        System.out.println(toSet);
    }

    public static void main(String[] args) {
        parseStatement(parseSql(sql, JdbcConstants.MYSQL));
    }
}