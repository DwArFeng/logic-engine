package com.dwarfeng.logicengine.stack.exception;

/**
 * 守卫器执行异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class GuarderExecutionException extends GuarderException {

    private static final long serialVersionUID = 754716489619434352L;

    public GuarderExecutionException() {
    }

    public GuarderExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public GuarderExecutionException(String message) {
        super(message);
    }

    public GuarderExecutionException(Throwable cause) {
        super(cause);
    }
}
