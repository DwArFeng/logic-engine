package com.dwarfeng.logicengine.stack.exception;

/**
 * 守卫器构造异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class GuarderMakeException extends GuarderException {

    private static final long serialVersionUID = -6503081907969425071L;

    public GuarderMakeException() {
    }

    public GuarderMakeException(String message, Throwable cause) {
        super(message, cause);
    }

    public GuarderMakeException(String message) {
        super(message);
    }

    public GuarderMakeException(Throwable cause) {
        super(cause);
    }
}
