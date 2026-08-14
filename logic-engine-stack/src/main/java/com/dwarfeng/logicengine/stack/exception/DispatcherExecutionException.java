package com.dwarfeng.logicengine.stack.exception;

/**
 * 调度器执行异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class DispatcherExecutionException extends DispatcherException {

    private static final long serialVersionUID = -7387969759883088152L;

    public DispatcherExecutionException() {
    }

    public DispatcherExecutionException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "调度器执行异常";
    }
}
