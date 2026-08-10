package com.dwarfeng.logicengine.stack.exception;

/**
 * 执行器执行异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class PerformerExecutionException extends PerformerException {

    private static final long serialVersionUID = 5628874435366057726L;

    public PerformerExecutionException() {
    }

    public PerformerExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public PerformerExecutionException(String message) {
        super(message);
    }

    public PerformerExecutionException(Throwable cause) {
        super(cause);
    }
}
