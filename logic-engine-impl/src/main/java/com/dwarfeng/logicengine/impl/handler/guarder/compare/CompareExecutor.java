package com.dwarfeng.logicengine.impl.handler.guarder.compare;

import com.dwarfeng.logicengine.sdk.handler.guarder.AbstractExecutor;
import com.dwarfeng.logicengine.sdk.util.Constants;
import com.dwarfeng.logicengine.stack.bean.dto.TaskVariableInspectInfo;
import com.dwarfeng.logicengine.stack.bean.dto.TaskVariableInspectResult;
import com.dwarfeng.logicengine.stack.exception.GuarderExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 比较守卫器执行器。
 *
 * <p>
 * 执行器读取配置中的左右两个操作数，其中操作数可以是任务变量，也可以是常量。比较域由左操作数的类型确定：
 * 左操作数为常量时使用其声明的类型，左操作数为任务变量时使用任务变量自身的值类型；右操作数按照比较域参与比较。
 * 比较完成后返回布尔结果，供上层决定状态转移是否发生。
 *
 * @author DwArFeng
 * @since 1.1.3
 */
@Component("compareGuarderRegistry.compareExecutor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CompareExecutor extends AbstractExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompareExecutor.class);

    private final CompareGuarderConfig config;

    public CompareExecutor(CompareGuarderConfig config) {
        this.config = config;
    }

    @Override
    public boolean test() throws Exception {
        TypedValue leftValue = resolveOperand(true);
        TypedValue rightValue = resolveOperand(false);
        int domainValueType = leftValue.getValueType();
        String comparator = config.getComparator();

        // 比较域由左操作数确定，算子必须适用于该类型，且右操作数的类型必须与其兼容。
        try {
            CompareGuarderConfigValidator.checkComparatorApplicable(comparator, domainValueType, "comparator");
        } catch (IllegalArgumentException e) {
            throw new GuarderExecutionException(e.getMessage(), e);
        }
        if (!CompareGuarderConfigValidator.isCompatible(domainValueType, rightValue.getValueType())) {
            throw new GuarderExecutionException(
                    "比较守卫器左右操作数类型不兼容: 左=" +
                            CompareGuarderConfigValidator.describeValueType(domainValueType) + ", 右=" +
                            CompareGuarderConfigValidator.describeValueType(rightValue.getValueType())
            );
        }

        boolean result = compare(domainValueType, comparator, leftValue.getValue(), rightValue.getValue());
        LOGGER.debug(
                "比较守卫器比较完成, 算子={}, 左操作数={}, 右操作数={}, 忽略大小写={}, 结果={}",
                comparator, leftValue, rightValue, config.isIgnoreCase(), result
        );
        return result;
    }

    /**
     * 解析指定侧的操作数。
     *
     * @param left 是否解析左操作数。
     * @return 解析后的带类型操作数。
     * @throws Exception 方法执行过程中发生的任何异常。
     */
    private TypedValue resolveOperand(boolean left) throws Exception {
        String sourceType = left ? config.getLeftSourceType() : config.getRightSourceType();
        String label = left ? "左" : "右";
        if (CompareGuarderConstants.SOURCE_TYPE_TASK_VARIABLE.equals(sourceType)) {
            String variableId = left ? config.getLeftTaskVariableId() : config.getRightTaskVariableId();
            return resolveTaskVariable(variableId, label);
        }
        String constantType = left ? config.getLeftConstantType() : config.getRightConstantType();
        String constantValue = left ? config.getLeftConstantValue() : config.getRightConstantValue();
        return resolveConstant(constantType, constantValue, label);
    }

    private TypedValue resolveTaskVariable(String variableId, String label) throws Exception {
        TaskVariableInspectResult inspectResult = context.inspectTaskVariable(
                new TaskVariableInspectInfo(context.getTask().getKey(), variableId)
        );
        if (Objects.isNull(inspectResult)) {
            throw new GuarderExecutionException("比较守卫器" + label + "操作数任务变量不存在: " + variableId);
        }
        if (!isValidValueType(inspectResult.getValueType())) {
            throw new GuarderExecutionException("比较守卫器" + label + "操作数任务变量值类型非法: " +
                    inspectResult.getValueType() + ", 任务变量 ID: " + variableId);
        }
        return new TypedValue(inspectResult.getValueType(), inspectResult.getValue());
    }

    private TypedValue resolveConstant(String constantType, String constantValue, String label) throws Exception {
        try {
            int valueType = CompareGuarderConfigValidator.parseValueType(constantType, label + "操作数常量类型");
            Object value = CompareGuarderConfigValidator.parseConstantValue(
                    valueType, constantValue, label + "操作数常量值"
            );
            return new TypedValue(valueType, value);
        } catch (IllegalArgumentException e) {
            throw new GuarderExecutionException(e.getMessage(), e);
        }
    }

    private boolean compare(int domainValueType, String comparator, Object leftValue, Object rightValue)
            throws Exception {
        switch (comparator) {
            case CompareGuarderConstants.COMPARATOR_EQ:
                return isEquals(domainValueType, leftValue, rightValue);
            case CompareGuarderConstants.COMPARATOR_NE:
                return !isEquals(domainValueType, leftValue, rightValue);
            case CompareGuarderConstants.COMPARATOR_GT:
            case CompareGuarderConstants.COMPARATOR_GE:
            case CompareGuarderConstants.COMPARATOR_LT:
            case CompareGuarderConstants.COMPARATOR_LE:
                return compareOrdered(domainValueType, comparator, leftValue, rightValue);
            case CompareGuarderConstants.COMPARATOR_CONTAINS:
                return containsText((String) leftValue, (String) rightValue, config.isIgnoreCase());
            case CompareGuarderConstants.COMPARATOR_MATCHES:
                return matchesText((String) leftValue, (String) rightValue, config.isIgnoreCase());
            default:
                throw new GuarderExecutionException("比较守卫器配置项 comparator 非法: " + comparator);
        }
    }

    private boolean isEquals(int domainValueType, Object leftValue, Object rightValue) throws Exception {
        if (Objects.isNull(leftValue) || Objects.isNull(rightValue)) {
            return Objects.isNull(leftValue) && Objects.isNull(rightValue);
        }
        switch (domainValueType) {
            case Constants.TASK_VARIABLE_VALUE_TYPE_STRING:
                return compareTextEquality((String) leftValue, (String) rightValue, config.isIgnoreCase());
            case Constants.TASK_VARIABLE_VALUE_TYPE_BOOLEAN:
                return leftValue.equals(rightValue);
            case Constants.TASK_VARIABLE_VALUE_TYPE_DATE:
                return ((Date) leftValue).getTime() == ((Date) rightValue).getTime();
            case Constants.TASK_VARIABLE_VALUE_TYPE_LONG:
            case Constants.TASK_VARIABLE_VALUE_TYPE_DOUBLE:
                return compareNumeric(domainValueType, leftValue, rightValue) == 0;
            default:
                throw new GuarderExecutionException("比较守卫器比较域类型非法: " + domainValueType);
        }
    }

    private static boolean compareOrdered(int domainValueType, String comparator, Object leftValue, Object rightValue)
            throws Exception {
        // 空值没有大小概念，有序比较一律判否。
        if (Objects.isNull(leftValue) || Objects.isNull(rightValue)) {
            return false;
        }
        int compareResult;
        switch (domainValueType) {
            case Constants.TASK_VARIABLE_VALUE_TYPE_LONG:
            case Constants.TASK_VARIABLE_VALUE_TYPE_DOUBLE:
                compareResult = compareNumeric(domainValueType, leftValue, rightValue);
                break;
            case Constants.TASK_VARIABLE_VALUE_TYPE_DATE:
                compareResult = Long.compare(((Date) leftValue).getTime(), ((Date) rightValue).getTime());
                break;
            default:
                throw new GuarderExecutionException("比较守卫器比较域类型不支持大小比较: " + domainValueType);
        }
        switch (comparator) {
            case CompareGuarderConstants.COMPARATOR_GT:
                return compareResult > 0;
            case CompareGuarderConstants.COMPARATOR_GE:
                return compareResult >= 0;
            case CompareGuarderConstants.COMPARATOR_LT:
                return compareResult < 0;
            case CompareGuarderConstants.COMPARATOR_LE:
                return compareResult <= 0;
            default:
                throw new GuarderExecutionException("比较守卫器配置项 comparator 非法: " + comparator);
        }
    }

    private static int compareNumeric(int domainValueType, Object leftValue, Object rightValue) {
        // 两侧同为整数时按 long 精确比较。
        if (domainValueType == Constants.TASK_VARIABLE_VALUE_TYPE_LONG
                && leftValue instanceof Long && rightValue instanceof Long) {
            return Long.compare((Long) leftValue, (Long) rightValue);
        }
        double leftDouble = ((Number) leftValue).doubleValue();
        double rightDouble = ((Number) rightValue).doubleValue();
        // 非有限值无法转换为十进制，退回 double 的比较语义。
        if (Double.isNaN(leftDouble) || Double.isInfinite(leftDouble)
                || Double.isNaN(rightDouble) || Double.isInfinite(rightDouble)) {
            return Double.compare(leftDouble, rightDouble);
        }
        return toDecimal(leftValue).compareTo(toDecimal(rightValue));
    }

    private static BigDecimal toDecimal(Object value) {
        if (value instanceof Long) {
            return BigDecimal.valueOf((Long) value);
        }
        return BigDecimal.valueOf((Double) value);
    }

    private static boolean compareTextEquality(String leftValue, String rightValue, boolean ignoreCase) {
        if (ignoreCase) {
            return leftValue.toLowerCase(Locale.ROOT).equals(rightValue.toLowerCase(Locale.ROOT));
        }
        return leftValue.equals(rightValue);
    }

    private static boolean containsText(String leftValue, String rightValue, boolean ignoreCase) {
        if (Objects.isNull(leftValue) || Objects.isNull(rightValue)) {
            return false;
        }
        if (ignoreCase) {
            return leftValue.toLowerCase(Locale.ROOT).contains(rightValue.toLowerCase(Locale.ROOT));
        }
        return leftValue.contains(rightValue);
    }

    private static boolean matchesText(String leftValue, String rightValue, boolean ignoreCase)
            throws GuarderExecutionException {
        if (Objects.isNull(leftValue) || Objects.isNull(rightValue)) {
            return false;
        }
        int flags = ignoreCase ? Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE : 0;
        try {
            return Pattern.compile(rightValue, flags).matcher(leftValue).matches();
        } catch (PatternSyntaxException e) {
            throw new GuarderExecutionException("比较守卫器右操作数不是合法的正则表达式: " + rightValue, e);
        }
    }

    private static boolean isValidValueType(int valueType) {
        return valueType == Constants.TASK_VARIABLE_VALUE_TYPE_STRING
                || valueType == Constants.TASK_VARIABLE_VALUE_TYPE_LONG
                || valueType == Constants.TASK_VARIABLE_VALUE_TYPE_DOUBLE
                || valueType == Constants.TASK_VARIABLE_VALUE_TYPE_BOOLEAN
                || valueType == Constants.TASK_VARIABLE_VALUE_TYPE_DATE;
    }

    @Override
    public String toString() {
        return "CompareExecutor{" +
                "config=" + config +
                ", context=" + context +
                '}';
    }

    /**
     * 带类型的操作数。
     *
     * @author DwArFeng
     * @since 1.1.3
     */
    private static final class TypedValue {

        private final int valueType;
        private final Object value;

        private TypedValue(int valueType, Object value) {
            this.valueType = valueType;
            this.value = value;
        }

        public int getValueType() {
            return valueType;
        }

        public Object getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "TypedValue{" +
                    "valueType=" + valueType +
                    ", value=" + value +
                    '}';
        }
    }
}
