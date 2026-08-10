package com.dwarfeng.logicengine.sdk.util;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 任务状态字段有效性验证注解。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Documented
@Constraint(validatedBy = ValidTaskStatus.InternalConstraintValidator.class)
@Target({
        ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE
})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTaskStatus {

    String message() default "invalid task status";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class InternalConstraintValidator implements ConstraintValidator<ValidTaskStatus, Integer> {

        @Override
        public boolean isValid(Integer value, ConstraintValidatorContext context) {
            try {
                return Constants.taskStatusSpace().contains(value);
            } catch (Exception e) {
                return false;
            }
        }
    }
}
