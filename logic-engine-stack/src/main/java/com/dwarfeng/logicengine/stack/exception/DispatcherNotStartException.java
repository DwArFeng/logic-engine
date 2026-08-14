package com.dwarfeng.logicengine.stack.exception;

/**
 * 调度器未启动异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class DispatcherNotStartException extends DispatcherException {

    private static final long serialVersionUID = -8186009155852099109L;

    public DispatcherNotStartException() {
    }

    public DispatcherNotStartException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "调度器未启动";
    }
}
