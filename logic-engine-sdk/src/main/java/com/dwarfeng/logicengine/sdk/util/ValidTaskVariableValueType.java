package com.dwarfeng.logicengine.sdk.util;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 任务变量值类型字段有效性验证注解。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Documented
@Constraint(validatedBy = ValidTaskVariableValueType.InternalConstraintValidator.class)
@Target({
        ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE
})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTaskVariableValueType {

    String message() default "invalid task variable value type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class InternalConstraintValidator implements ConstraintValidator<ValidTaskVariableValueType, Integer> {

        @Override
        public boolean isValid(Integer value, ConstraintValidatorContext context) {
            try {
                return Constants.taskVariableValueTypeSpace().contains(value);
            } catch (Exception e) {
                return false;
            }
        }
    }
}
