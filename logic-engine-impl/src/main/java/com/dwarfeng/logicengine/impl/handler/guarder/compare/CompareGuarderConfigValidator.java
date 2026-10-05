package com.dwarfeng.logicengine.impl.handler.guarder.compare;

import com.dwarfeng.logicengine.sdk.util.Constants;

import java.util.Date;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 比较守卫器配置校验器。
 *
 * <p>
 * 该校验器集中承载比较守卫器的词表解析与配置校验规则：一方面在守卫器构造阶段对参数进行静态校验，使配置层面的
 * 错误尽早暴露；另一方面为执行器提供类型词表解析、类型兼容判断以及算子适用性判断等公共能力，
 * 保证构造阶段与运行阶段使用同一套规则。词表常量统一由 {@link CompareGuarderConstants} 定义。
 *
 * @author DwArFeng
 * @since 1.1.3
 */
final class CompareGuarderConfigValidator {

    /**
     * 解析类型词表，返回任务变量值类型编码。
     *
     * <p>
     * 可读名与整数编码等价，可读名优先；除此之外的取值一律视为非法配置。
     *
     * @param raw       类型词表的原始文本。
     * @param fieldName 出现异常时用于描述配置项名称的字段名。
     * @return 任务变量值类型编码。
     */
    static int parseValueType(String raw, String fieldName) {
        if (Objects.isNull(raw)) {
            throw new IllegalArgumentException("比较守卫器配置项 " + fieldName + " 不能为空");
        }
        switch (raw) {
            case CompareGuarderConstants.VALUE_TYPE_STRING:
            case "0":
                return Constants.TASK_VARIABLE_VALUE_TYPE_STRING;
            case CompareGuarderConstants.VALUE_TYPE_LONG:
            case "1":
                return Constants.TASK_VARIABLE_VALUE_TYPE_LONG;
            case CompareGuarderConstants.VALUE_TYPE_DOUBLE:
            case "2":
                return Constants.TASK_VARIABLE_VALUE_TYPE_DOUBLE;
            case CompareGuarderConstants.VALUE_TYPE_BOOLEAN:
            case "3":
                return Constants.TASK_VARIABLE_VALUE_TYPE_BOOLEAN;
            case CompareGuarderConstants.VALUE_TYPE_DATE:
            case "4":
                return Constants.TASK_VARIABLE_VALUE_TYPE_DATE;
            default:
                throw new IllegalArgumentException("比较守卫器配置项 " + fieldName + " 非法: " + raw);
        }
    }

    /**
     * 按照值类型解析常量值。
     *
     * <p>
     * 文本不做任何转换；整数、浮点数与日期均按照严格的文本格式解析，日期使用毫秒时间戳；布尔值只接受
     * <code>true</code> 与 <code>false</code>，不采用宽松解析。
     *
     * @param valueType 任务变量值类型编码。
     * @param raw       常量值的原始文本。
     * @param fieldName 出现异常时用于描述配置项名称的字段名。
     * @return 解析后的常量值。
     */
    static Object parseConstantValue(int valueType, String raw, String fieldName) {
        if (Objects.isNull(raw)) {
            throw new IllegalArgumentException("比较守卫器配置项 " + fieldName + " 不能为空");
        }
        switch (valueType) {
            case Constants.TASK_VARIABLE_VALUE_TYPE_STRING:
                return raw;
            case Constants.TASK_VARIABLE_VALUE_TYPE_LONG:
                try {
                    return Long.parseLong(raw);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException(
                            "比较守卫器配置项 " + fieldName + " 无法按整数解析: " + raw, e
                    );
                }
            case Constants.TASK_VARIABLE_VALUE_TYPE_DOUBLE:
                try {
                    return Double.parseDouble(raw);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException(
                            "比较守卫器配置项 " + fieldName + " 无法按浮点数解析: " + raw, e
                    );
                }
            case Constants.TASK_VARIABLE_VALUE_TYPE_BOOLEAN:
                if ("true".equals(raw)) {
                    return Boolean.TRUE;
                }
                if ("false".equals(raw)) {
                    return Boolean.FALSE;
                }
                throw new IllegalArgumentException("比较守卫器配置项 " + fieldName + " 无法按布尔值解析: " + raw);
            case Constants.TASK_VARIABLE_VALUE_TYPE_DATE:
                try {
                    return new Date(Long.parseLong(raw));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException(
                            "比较守卫器配置项 " + fieldName + " 无法按毫秒时间戳解析: " + raw, e
                    );
                }
            default:
                throw new IllegalArgumentException("比较守卫器配置项 " + fieldName + " 的值类型编码非法: " + valueType);
        }
    }

    /**
     * 对配置进行静态校验。
     *
     * <p>
     * 静态校验覆盖所有不依赖运行期数据即可判定的规则，包括必填项、来源与专有字段的互斥关系、类型词表与算子词表的
     * 合法性、常量值的可解析性，以及左操作数为常量时可以确定的比较域约束；右操作数为常量时，其类型与算子的
     * 匹配关系同样可以独立校验。
     *
     * @param config 待校验的配置。
     */
    static void validateStatic(CompareGuarderConfig config) {
        if (Objects.isNull(config)) {
            throw new IllegalArgumentException("比较守卫器配置不能为空");
        }
        String comparator = config.getComparator();
        checkRequired("comparator", comparator);
        if (!isValidComparator(comparator)) {
            throw new IllegalArgumentException("比较守卫器配置项 comparator 非法: " + comparator);
        }

        Integer leftValueType = validateOperand(config, true);
        Integer rightValueType = validateOperand(config, false);

        // 左操作数为常量时，比较域可以在构造阶段确定，可以提前校验算子与左右操作数的类型匹配情况。
        if (Objects.nonNull(leftValueType)) {
            checkComparatorApplicable(comparator, leftValueType, "comparator");
            if (Objects.nonNull(rightValueType) && !isCompatible(leftValueType, rightValueType)) {
                throw new IllegalArgumentException("比较守卫器左右操作数类型不兼容: 左=" +
                        describeValueType(leftValueType) + ", 右=" + describeValueType(rightValueType));
            }
        }
        // 右操作数为常量时，其类型与算子的匹配关系与比较域无关，可以独立校验。
        if (Objects.nonNull(rightValueType)) {
            checkComparatorApplicable(comparator, rightValueType, "right_constant_type");
            // 算子为正则匹配时，提前检查常量的正则语法。
            if (CompareGuarderConstants.COMPARATOR_MATCHES.equals(comparator)) {
                checkPatternSyntax(config.getRightConstantValue(), "right_constant_value");
            }
        }
    }

    /**
     * 判断两个值类型是否兼容。
     *
     * <p>
     * 类型相同时恒为兼容；整数与浮点数之间允许互相兼容，以保证数值比较的可用性；其余组合一律不兼容。
     *
     * @param leftValueType  左值类型编码。
     * @param rightValueType 右值类型编码。
     * @return 两个值类型是否兼容。
     */
    // 为了保证代码的可读性，此处代码不做控制逻辑反转。
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    static boolean isCompatible(int leftValueType, int rightValueType) {
        if (leftValueType == rightValueType) {
            return true;
        }
        return isNumericValueType(leftValueType) && isNumericValueType(rightValueType);
    }

    /**
     * 判断算子是否为有序比较算子。
     *
     * @param comparator 算子。
     * @return 算子是否为有序比较算子。
     */
    static boolean isOrderedComparator(String comparator) {
        if (Objects.isNull(comparator)) {
            return false;
        }
        switch (comparator) {
            case CompareGuarderConstants.COMPARATOR_GT:
            case CompareGuarderConstants.COMPARATOR_GE:
            case CompareGuarderConstants.COMPARATOR_LT:
            case CompareGuarderConstants.COMPARATOR_LE:
                return true;
            default:
                return false;
        }
    }

    /**
     * 判断算子是否为字符串算子。
     *
     * @param comparator 算子。
     * @return 算子是否为字符串算子。
     */
    static boolean isStringComparator(String comparator) {
        if (Objects.isNull(comparator)) {
            return false;
        }
        switch (comparator) {
            case CompareGuarderConstants.COMPARATOR_CONTAINS:
            case CompareGuarderConstants.COMPARATOR_MATCHES:
                return true;
            default:
                return false;
        }
    }

    /**
     * 校验算子与操作数类型的匹配关系。
     *
     * @param comparator 算子。
     * @param valueType  操作数的值类型编码。
     * @param fieldName  出现异常时用于描述配置项名称的字段名。
     */
    static void checkComparatorApplicable(String comparator, int valueType, String fieldName) {
        if (isStringComparator(comparator)) {
            if (valueType != Constants.TASK_VARIABLE_VALUE_TYPE_STRING) {
                throw new IllegalArgumentException("比较守卫器配置项 " + fieldName + " 为 " + comparator +
                        "，操作数的值类型必须为 " + CompareGuarderConstants.VALUE_TYPE_STRING + "，实际为 " +
                        describeValueType(valueType));
            }
            return;
        }
        if (isOrderedComparator(comparator)) {
            if (valueType != Constants.TASK_VARIABLE_VALUE_TYPE_LONG
                    && valueType != Constants.TASK_VARIABLE_VALUE_TYPE_DOUBLE
                    && valueType != Constants.TASK_VARIABLE_VALUE_TYPE_DATE) {
                throw new IllegalArgumentException("比较守卫器配置项 " + fieldName + " 为 " + comparator +
                        "，操作数的值类型必须为 " + CompareGuarderConstants.VALUE_TYPE_LONG + "、" +
                        CompareGuarderConstants.VALUE_TYPE_DOUBLE + " 或 " + CompareGuarderConstants.VALUE_TYPE_DATE +
                        "，实际为 " + describeValueType(valueType));
            }
        }
    }

    /**
     * 校验正则表达式的语法。
     *
     * @param regex     正则表达式。
     * @param fieldName 出现异常时用于描述配置项名称的字段名。
     */
    // 为了保证代码的可读性，此处代码不做简化。
    @SuppressWarnings("SameParameterValue")
    static void checkPatternSyntax(String regex, String fieldName) {
        try {
            Pattern.compile(regex);
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("比较守卫器配置项 " + fieldName + " 不是合法的正则表达式: " + regex, e);
        }
    }

    /**
     * 获取值类型的可读名。
     *
     * @param valueType 值类型编码。
     * @return 值类型的可读名，未知类型返回其编码文本。
     */
    static String describeValueType(int valueType) {
        switch (valueType) {
            case Constants.TASK_VARIABLE_VALUE_TYPE_STRING:
                return CompareGuarderConstants.VALUE_TYPE_STRING;
            case Constants.TASK_VARIABLE_VALUE_TYPE_LONG:
                return CompareGuarderConstants.VALUE_TYPE_LONG;
            case Constants.TASK_VARIABLE_VALUE_TYPE_DOUBLE:
                return CompareGuarderConstants.VALUE_TYPE_DOUBLE;
            case Constants.TASK_VARIABLE_VALUE_TYPE_BOOLEAN:
                return CompareGuarderConstants.VALUE_TYPE_BOOLEAN;
            case Constants.TASK_VARIABLE_VALUE_TYPE_DATE:
                return CompareGuarderConstants.VALUE_TYPE_DATE;
            default:
                return String.valueOf(valueType);
        }
    }

    private static Integer validateOperand(CompareGuarderConfig config, boolean left) {
        String label = left ? "左" : "右";
        String prefix = left ? "left" : "right";
        String sourceType = left ? config.getLeftSourceType() : config.getRightSourceType();
        String taskVariableId = left ? config.getLeftTaskVariableId() : config.getRightTaskVariableId();
        String constantType = left ? config.getLeftConstantType() : config.getRightConstantType();
        String constantValue = left ? config.getLeftConstantValue() : config.getRightConstantValue();
        checkRequired(prefix + "_source_type", sourceType);
        switch (sourceType) {
            case CompareGuarderConstants.SOURCE_TYPE_TASK_VARIABLE:
                checkRequired(prefix + "_task_variable_id", taskVariableId);
                if (Objects.nonNull(constantType) || Objects.nonNull(constantValue)) {
                    throw new IllegalArgumentException("比较守卫器" + label + "操作数为任务变量时，不允许出现 " +
                            prefix + "_constant_type 或 " + prefix + "_constant_value");
                }
                // 任务变量的值类型在运行期才能确定，返回 null 表示比较域静态不可判。
                return null;
            case CompareGuarderConstants.SOURCE_TYPE_CONSTANT:
                if (Objects.nonNull(taskVariableId)) {
                    throw new IllegalArgumentException("比较守卫器" + label + "操作数为常量时，不允许出现 " +
                            prefix + "_task_variable_id");
                }
                checkRequired(prefix + "_constant_type", constantType);
                checkNotNull(prefix + "_constant_value", constantValue);
                int valueType = parseValueType(constantType, prefix + "_constant_type");
                parseConstantValue(valueType, constantValue, prefix + "_constant_value");
                return valueType;
            default:
                throw new IllegalArgumentException("比较守卫器配置项 " + prefix + "_source_type 非法: " + sourceType);
        }
    }

    private static boolean isValidComparator(String comparator) {
        if (Objects.isNull(comparator)) {
            return false;
        }
        switch (comparator) {
            case CompareGuarderConstants.COMPARATOR_EQ:
            case CompareGuarderConstants.COMPARATOR_NE:
            case CompareGuarderConstants.COMPARATOR_GT:
            case CompareGuarderConstants.COMPARATOR_GE:
            case CompareGuarderConstants.COMPARATOR_LT:
            case CompareGuarderConstants.COMPARATOR_LE:
            case CompareGuarderConstants.COMPARATOR_CONTAINS:
            case CompareGuarderConstants.COMPARATOR_MATCHES:
                return true;
            default:
                return false;
        }
    }

    private static boolean isNumericValueType(int valueType) {
        return valueType == Constants.TASK_VARIABLE_VALUE_TYPE_LONG
                || valueType == Constants.TASK_VARIABLE_VALUE_TYPE_DOUBLE;
    }

    private static void checkRequired(String fieldName, String value) {
        if (Objects.isNull(value) || value.trim().isEmpty()) {
            throw new IllegalArgumentException("比较守卫器配置项 " + fieldName + " 不能为空");
        }
    }

    private static void checkNotNull(String fieldName, String value) {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException("比较守卫器配置项 " + fieldName + " 不能为空");
        }
    }

    private CompareGuarderConfigValidator() {
        throw new IllegalStateException("禁止进行实例化操作");
    }
}
