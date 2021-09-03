package com.yankee.lineage;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLCreateStatement;
import com.alibaba.druid.sql.ast.statement.SQLInsertStatement;
import com.alibaba.druid.util.JdbcConstants;

import java.util.List;

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

    public static void main(String[] args) {
        parseStatement(parseSql(sql, JdbcConstants.MYSQL));
    }
}