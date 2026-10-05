package com.dwarfeng.logicengine.impl.handler.guarder.compare;

/**
 * 比较守卫器常量。
 *
 * <p>
 * 该类集中定义比较守卫器配置所使用的词表常量，包括操作数来源、比较算子以及常量类型词表。
 * 词表统一采用严格小写的标准形式，不接受别名。
 *
 * @author DwArFeng
 * @since 1.1.3
 */
public final class CompareGuarderConstants {

    public static final String SOURCE_TYPE_TASK_VARIABLE = "task_variable";

    public static final String SOURCE_TYPE_CONSTANT = "constant";

    public static final String COMPARATOR_EQ = "eq";
    public static final String COMPARATOR_NE = "ne";
    public static final String COMPARATOR_GT = "gt";
    public static final String COMPARATOR_GE = "ge";
    public static final String COMPARATOR_LT = "lt";
    public static final String COMPARATOR_LE = "le";
    public static final String COMPARATOR_CONTAINS = "contains";
    public static final String COMPARATOR_MATCHES = "matches";

    public static final String VALUE_TYPE_STRING = "string";
    public static final String VALUE_TYPE_LONG = "long";
    public static final String VALUE_TYPE_DOUBLE = "double";
    public static final String VALUE_TYPE_BOOLEAN = "boolean";
    public static final String VALUE_TYPE_DATE = "date";

    private CompareGuarderConstants() {
        throw new IllegalStateException("禁止进行实例化操作");
    }
}
