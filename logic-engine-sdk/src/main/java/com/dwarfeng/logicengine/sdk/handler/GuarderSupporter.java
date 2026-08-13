package com.dwarfeng.logicengine.sdk.handler;

/**
 * 守卫器支持器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface GuarderSupporter {

    /**
     * 提供守卫器类型。
     *
     * @return 守卫器类型。
     */
    String provideType();

    /**
     * 提供守卫器标签。
     *
     * @return 守卫器标签。
     */
    String provideLabel();

    /**
     * 提供守卫器描述。
     *
     * @return 守卫器描述。
     */
    String provideDescription();

    /**
     * 提供守卫器示例参数。
     *
     * @return 守卫器示例参数。
     */
    String provideExampleParam();
}
