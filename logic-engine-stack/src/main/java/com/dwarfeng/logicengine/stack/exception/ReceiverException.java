package com.dwarfeng.logicengine.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 接收器异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ReceiverException extends HandlerException {

    private static final long serialVersionUID = -3745035138798745827L;

    public ReceiverException() {
    }

    public ReceiverException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReceiverException(String message) {
        super(message);
    }

    public ReceiverException(Throwable cause) {
        super(cause);
    }
}
