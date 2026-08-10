package com.dwarfeng.logicengine.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 执行器异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class PerformerException extends HandlerException {

    private static final long serialVersionUID = 8724998022376349873L;

    public PerformerException() {
    }

    public PerformerException(String message, Throwable cause) {
        super(message, cause);
    }

    public PerformerException(String message) {
        super(message);
    }

    public PerformerException(Throwable cause) {
        super(cause);
    }
}
