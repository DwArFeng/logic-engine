package com.dwarfeng.logicengine.stack.exception;

/**
 * 不支持的执行器类型异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class UnsupportedPerformerTypeException extends PerformerException {

    private static final long serialVersionUID = 159765716988927646L;

    private final String type;

    public UnsupportedPerformerTypeException(String type) {
        this.type = type;
    }

    public UnsupportedPerformerTypeException(Throwable cause, String type) {
        super(cause);
        this.type = type;
    }

    @Override
    public String getMessage() {
        return "不支持的执行器类型: " + type;
    }
}
