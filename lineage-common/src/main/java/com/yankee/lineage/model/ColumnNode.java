package com.yankee.lineage.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据血缘解析时的字段节点
 *
 * @author Yankee
 * @program yankee-lineage
 * @description
 * @since 2021/9/3
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColumnNode {
    /**
     * 列所属的表
     */
    private TableNode owner;

    /**
     * 表
     */
    private String tableName;

    /**
     * 名称
     */
    private String name;

    /**
     * 别名
     */
    private String alias;

    /**
     * 来源列
     */
    private final List<ColumnNode> sourceColumns = new ArrayList<>();

    /**
     * 节点表达式
     */
    private String expression;

    /**
     * 字段所在的表树Id
     */
    private Long tableTreeNodeId;

    /**
     * 表的表达式
     */
    private String tableExpression;

    /**
     * 字段是否为常量
     */
    @Builder.Default
    private Boolean isConstant = false;
}
