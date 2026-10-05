package com.dwarfeng.logicengine.impl.handler.guarder.compare;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.stack.bean.Bean;

/**
 * 比较守卫器配置。
 *
 * <p>
 * 该配置用于描述比较守卫器的左右两个操作数以及二者之间的比较方式。
 * 每个操作数可以是任务变量，也可以是常量：常量需要显式声明类型，任务变量则直接使用自身的值类型。
 * 比较域由左操作数的类型确定，右操作数按照比较域参与比较。
 * 比较方式支持等值比较、大小比较以及字符串的包含与正则匹配，比较结果决定状态转移是否发生。
 *
 * @author DwArFeng
 * @since 1.1.3
 */
public class CompareGuarderConfig implements Bean {

    private static final long serialVersionUID = 6213082106565256893L;

    @JSONField(name = "#left_source_type", ordinal = 1)
    private String leftSourceTypeRem = "左操作数来源类型，合法值为 task_variable、constant。";

    @JSONField(name = "left_source_type", ordinal = 2)
    private String leftSourceType;

    @JSONField(name = "#left_task_variable_id", ordinal = 3)
    private String leftTaskVariableIdRem = "当 left_source_type=task_variable 时必填，表示左操作数对应的任务变量 ID。";

    @JSONField(name = "left_task_variable_id", ordinal = 4)
    private String leftTaskVariableId;

    @JSONField(name = "#left_constant_type", ordinal = 5)
    private String leftConstantTypeRem =
            "当 left_source_type=constant 时必填，合法值为 string、long、double、boolean、date 或类型编码 0 - 4。";

    @JSONField(name = "left_constant_type", ordinal = 6)
    private String leftConstantType;

    @JSONField(name = "#left_constant_value", ordinal = 7)
    private String leftConstantValueRem = "当 left_source_type=constant 时必填，按照 left_constant_type 解析。";

    @JSONField(name = "left_constant_value", ordinal = 8)
    private String leftConstantValue;

    @JSONField(name = "#right_source_type", ordinal = 9)
    private String rightSourceTypeRem = "右操作数来源类型，合法值为 task_variable、constant。";

    @JSONField(name = "right_source_type", ordinal = 10)
    private String rightSourceType;

    @JSONField(name = "#right_task_variable_id", ordinal = 11)
    private String rightTaskVariableIdRem = "当 right_source_type=task_variable 时必填，表示右操作数对应的任务变量 ID。";

    @JSONField(name = "right_task_variable_id", ordinal = 12)
    private String rightTaskVariableId;

    @JSONField(name = "#right_constant_type", ordinal = 13)
    private String rightConstantTypeRem =
            "当 right_source_type=constant 时必填，合法值为 string、long、double、boolean、date 或类型编码 0 - 4。";

    @JSONField(name = "right_constant_type", ordinal = 14)
    private String rightConstantType;

    @JSONField(name = "#right_constant_value", ordinal = 15)
    private String rightConstantValueRem = "当 right_source_type=constant 时必填，按照 right_constant_type 解析。";

    @JSONField(name = "right_constant_value", ordinal = 16)
    private String rightConstantValue;

    @JSONField(name = "#comparator", ordinal = 17)
    private String comparatorRem = "比较方式，合法值为 eq、ne、gt、ge、lt、le、contains、matches。";

    @JSONField(name = "comparator", ordinal = 18)
    private String comparator;

    @JSONField(name = "#ignore_case", ordinal = 19)
    private String ignoreCaseRem = "是否忽略大小写，仅在字符串比较时生效。";

    @JSONField(name = "ignore_case", ordinal = 20)
    private boolean ignoreCase = false;

    public CompareGuarderConfig() {
    }

    public CompareGuarderConfig(
            String leftSourceType, String leftTaskVariableId, String leftConstantType, String leftConstantValue,
            String rightSourceType, String rightTaskVariableId, String rightConstantType, String rightConstantValue,
            String comparator, boolean ignoreCase
    ) {
        this.leftSourceType = leftSourceType;
        this.leftTaskVariableId = leftTaskVariableId;
        this.leftConstantType = leftConstantType;
        this.leftConstantValue = leftConstantValue;
        this.rightSourceType = rightSourceType;
        this.rightTaskVariableId = rightTaskVariableId;
        this.rightConstantType = rightConstantType;
        this.rightConstantValue = rightConstantValue;
        this.comparator = comparator;
        this.ignoreCase = ignoreCase;
    }

    public String getLeftSourceTypeRem() {
        return leftSourceTypeRem;
    }

    public void setLeftSourceTypeRem(String leftSourceTypeRem) {
        this.leftSourceTypeRem = leftSourceTypeRem;
    }

    public String getLeftSourceType() {
        return leftSourceType;
    }

    public void setLeftSourceType(String leftSourceType) {
        this.leftSourceType = leftSourceType;
    }

    public String getLeftTaskVariableIdRem() {
        return leftTaskVariableIdRem;
    }

    public void setLeftTaskVariableIdRem(String leftTaskVariableIdRem) {
        this.leftTaskVariableIdRem = leftTaskVariableIdRem;
    }

    public String getLeftTaskVariableId() {
        return leftTaskVariableId;
    }

    public void setLeftTaskVariableId(String leftTaskVariableId) {
        this.leftTaskVariableId = leftTaskVariableId;
    }

    public String getLeftConstantTypeRem() {
        return leftConstantTypeRem;
    }

    public void setLeftConstantTypeRem(String leftConstantTypeRem) {
        this.leftConstantTypeRem = leftConstantTypeRem;
    }

    public String getLeftConstantType() {
        return leftConstantType;
    }

    public void setLeftConstantType(String leftConstantType) {
        this.leftConstantType = leftConstantType;
    }

    public String getLeftConstantValueRem() {
        return leftConstantValueRem;
    }

    public void setLeftConstantValueRem(String leftConstantValueRem) {
        this.leftConstantValueRem = leftConstantValueRem;
    }

    public String getLeftConstantValue() {
        return leftConstantValue;
    }

    public void setLeftConstantValue(String leftConstantValue) {
        this.leftConstantValue = leftConstantValue;
    }

    public String getRightSourceTypeRem() {
        return rightSourceTypeRem;
    }

    public void setRightSourceTypeRem(String rightSourceTypeRem) {
        this.rightSourceTypeRem = rightSourceTypeRem;
    }

    public String getRightSourceType() {
        return rightSourceType;
    }

    public void setRightSourceType(String rightSourceType) {
        this.rightSourceType = rightSourceType;
    }

    public String getRightTaskVariableIdRem() {
        return rightTaskVariableIdRem;
    }

    public void setRightTaskVariableIdRem(String rightTaskVariableIdRem) {
        this.rightTaskVariableIdRem = rightTaskVariableIdRem;
    }

    public String getRightTaskVariableId() {
        return rightTaskVariableId;
    }

    public void setRightTaskVariableId(String rightTaskVariableId) {
        this.rightTaskVariableId = rightTaskVariableId;
    }

    public String getRightConstantTypeRem() {
        return rightConstantTypeRem;
    }

    public void setRightConstantTypeRem(String rightConstantTypeRem) {
        this.rightConstantTypeRem = rightConstantTypeRem;
    }

    public String getRightConstantType() {
        return rightConstantType;
    }

    public void setRightConstantType(String rightConstantType) {
        this.rightConstantType = rightConstantType;
    }

    public String getRightConstantValueRem() {
        return rightConstantValueRem;
    }

    public void setRightConstantValueRem(String rightConstantValueRem) {
        this.rightConstantValueRem = rightConstantValueRem;
    }

    public String getRightConstantValue() {
        return rightConstantValue;
    }

    public void setRightConstantValue(String rightConstantValue) {
        this.rightConstantValue = rightConstantValue;
    }

    public String getComparatorRem() {
        return comparatorRem;
    }

    public void setComparatorRem(String comparatorRem) {
        this.comparatorRem = comparatorRem;
    }

    public String getComparator() {
        return comparator;
    }

    public void setComparator(String comparator) {
        this.comparator = comparator;
    }

    public String getIgnoreCaseRem() {
        return ignoreCaseRem;
    }

    public void setIgnoreCaseRem(String ignoreCaseRem) {
        this.ignoreCaseRem = ignoreCaseRem;
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    @Override
    public String toString() {
        return "CompareGuarderConfig{" +
                "leftSourceTypeRem='" + leftSourceTypeRem + '\'' +
                ", leftSourceType='" + leftSourceType + '\'' +
                ", leftTaskVariableIdRem='" + leftTaskVariableIdRem + '\'' +
                ", leftTaskVariableId='" + leftTaskVariableId + '\'' +
                ", leftConstantTypeRem='" + leftConstantTypeRem + '\'' +
                ", leftConstantType='" + leftConstantType + '\'' +
                ", leftConstantValueRem='" + leftConstantValueRem + '\'' +
                ", leftConstantValue='" + leftConstantValue + '\'' +
                ", rightSourceTypeRem='" + rightSourceTypeRem + '\'' +
                ", rightSourceType='" + rightSourceType + '\'' +
                ", rightTaskVariableIdRem='" + rightTaskVariableIdRem + '\'' +
                ", rightTaskVariableId='" + rightTaskVariableId + '\'' +
                ", rightConstantTypeRem='" + rightConstantTypeRem + '\'' +
                ", rightConstantType='" + rightConstantType + '\'' +
                ", rightConstantValueRem='" + rightConstantValueRem + '\'' +
                ", rightConstantValue='" + rightConstantValue + '\'' +
                ", comparatorRem='" + comparatorRem + '\'' +
                ", comparator='" + comparator + '\'' +
                ", ignoreCaseRem='" + ignoreCaseRem + '\'' +
                ", ignoreCase=" + ignoreCase +
                '}';
    }
}
