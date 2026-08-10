package com.dwarfeng.logicengine.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.Set;

/**
 * 任务状态不匹配异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TaskStatusMismatchException extends HandlerException {

    private static final long serialVersionUID = 6297942612462342662L;

    private final Set<Integer> expectedStatusSet;
    private final int actualStatus;

    public TaskStatusMismatchException(Set<Integer> expectedStatusSet, int actualStatus) {
        this.expectedStatusSet = expectedStatusSet;
        this.actualStatus = actualStatus;
    }

    public TaskStatusMismatchException(Throwable cause, Set<Integer> expectedStatusSet, int actualStatus) {
        super(cause);
        this.expectedStatusSet = expectedStatusSet;
        this.actualStatus = actualStatus;
    }

    @Override
    public String getMessage() {
        return "任务状态不匹配, 期望的状态为 " + expectedStatusSet + ", 实际的状态为 " + actualStatus;
    }
}
