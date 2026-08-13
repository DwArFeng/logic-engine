package com.dwarfeng.logicengine.stack.exception;

/**
 * 不支持的守卫器类型异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class UnsupportedGuarderTypeException extends GuarderException {

    private static final long serialVersionUID = 6170886376042843029L;

    private final String type;

    public UnsupportedGuarderTypeException(String type) {
        this.type = type;
    }

    public UnsupportedGuarderTypeException(Throwable cause, String type) {
        super(cause);
        this.type = type;
    }

    @Override
    public String getMessage() {
        return "不支持的守卫器类型: " + type;
    }
}
