package com.dwarfeng.logicengine.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 守卫器异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class GuarderException extends HandlerException {

    private static final long serialVersionUID = -9215100367516418823L;

    public GuarderException() {
    }

    public GuarderException(String message, Throwable cause) {
        super(message, cause);
    }

    public GuarderException(String message) {
        super(message);
    }

    public GuarderException(Throwable cause) {
        super(cause);
    }
}
