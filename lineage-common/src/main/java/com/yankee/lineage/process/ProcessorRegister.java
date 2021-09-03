package com.yankee.lineage.process;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.yankee.lineage.process.sqlexpr.SQLExprProcessor;
import com.yankee.lineage.process.sqlselectquery.SQLSelectQueryProcessor;
import com.yankee.lineage.process.sqlstatement.SQLStatementProcessor;
import com.yankee.lineage.process.sqltabsource.SQLTableSourceProcessor;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Processor注册器 按类型处理
 *
 * @author Yankee
 * @program yankee-lineage
 * @description
 * @since 2021/9/3
 */
public class ProcessorRegister {

    /**
     * SQLStatement 处理器
     */
    private static final Map<Type, SQLStatementProcessor> STATEMENT_PROCESSOR_MAP = new HashMap<>();

    /**
     * SQLSelectQuery 处理器
     */
    private static final Map<Type, SQLSelectQueryProcessor> SQL_SELECT_QUERY_PROCESSOR_MAP = new HashMap<>();

    /**
     * SQLTableSource 处理器
     */
    private static final Map<Type, SQLTableSourceProcessor> TABLE_SOURCE_PROCESSOR_MAP = new HashMap<>();

    /**
     * SQLExpr处理器
     */
    private static final Map<Type, SQLExprProcessor> TABLE_SQL_EXPR_MAP = new HashMap<>();

    private ProcessorRegister() {

    }

    public static void register(Class<?> clazz, Object bean) {
        if (bean instanceof SQLStatementProcessor) {
            STATEMENT_PROCESSOR_MAP.put(clazz, (SQLStatementProcessor) bean);
        } else if (bean instanceof SQLSelectQueryProcessor) {
            SQL_SELECT_QUERY_PROCESSOR_MAP.put(clazz, (SQLSelectQueryProcessor) bean);
        } else if (bean instanceof SQLTableSourceProcessor) {
            TABLE_SOURCE_PROCESSOR_MAP.put(clazz, (SQLTableSourceProcessor) bean);
        } else if (bean instanceof SQLExprProcessor) {
            TABLE_SQL_EXPR_MAP.put(clazz, (SQLExprProcessor) bean);
        }
    }


    public static SQLStatementProcessor getSQLStatementProcessor(Type clazz) {
        SQLStatementProcessor statementProcessor = STATEMENT_PROCESSOR_MAP.get(clazz);
        if (Objects.isNull(statementProcessor)) {
            throw new UnsupportedOperationException(clazz.getTypeName());
        }
        return statementProcessor;
    }

    public static SQLSelectQueryProcessor getSQLSelectQueryProcessor(Type clazz) {
        SQLSelectQueryProcessor sqlSelectQueryProcessor = SQL_SELECT_QUERY_PROCESSOR_MAP.get(clazz);
        if (Objects.isNull(sqlSelectQueryProcessor)) {
            throw new UnsupportedOperationException(clazz.getTypeName());
        }
        return sqlSelectQueryProcessor;
    }

    public static SQLTableSourceProcessor getSQLTableSourceProcessor(Type clazz) {
        SQLTableSourceProcessor tableSourceProcessor = TABLE_SOURCE_PROCESSOR_MAP.get(clazz);
        if (Objects.isNull(tableSourceProcessor)) {
            throw new UnsupportedOperationException(clazz.getTypeName());
        }
        return tableSourceProcessor;
    }

    public static SQLExprProcessor getSQLExprProcessor(Type clazz) {
        SQLExprProcessor sqlExprProcessor = TABLE_SQL_EXPR_MAP.get(clazz);
        if (Objects.isNull(sqlExprProcessor)) {
            throw new UnsupportedOperationException(clazz.getTypeName());
        }
        return sqlExprProcessor;
    }

    public static SQLExprProcessor getSQLSelectQueryProcessor(SQLExpr expr) {
        Class<? extends SQLExpr> clazz = expr.getClass();
        SQLExprProcessor sqlExprProcessor = TABLE_SQL_EXPR_MAP.get(clazz);
        if (Objects.isNull(sqlExprProcessor)) {
            throw new UnsupportedOperationException(clazz.getName());
        }
        return sqlExprProcessor;
    }

}
