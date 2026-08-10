package com.dwarfeng.logicengine.stack.exception;

/**
 * 执行器构造异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class PerformerMakeException extends PerformerException {

    private static final long serialVersionUID = 5652856215313322708L;

    public PerformerMakeException() {
    }

    public PerformerMakeException(String message, Throwable cause) {
        super(message, cause);
    }

    public PerformerMakeException(String message) {
        super(message);
    }

    public PerformerMakeException(Throwable cause) {
        super(cause);
    }
}
