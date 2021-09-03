package com.yankee.lineage.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据学院解析时的表节点
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
public class TableNode {
    /**
     * schema
     */
    private String schemaName;

    /**
     * 表名
     */
    private String name;

    /**
     * 别名
     */
    private String alias;

    /**
     * 是否为虚拟表
     */
    @Builder.Default
    private Boolean isVirtualTemp = false;

    /**
     * 特殊节点的处理
     */
    private String queryType;

    /**
     * 字段列表
     */
    private final List<ColumnNode> columns = new ArrayList<>();

    /**
     * 表达式
     */
    private String expression;
}
