import com.dwarfeng.logicengine.impl.handler.guarder.groovy.Processor
import com.dwarfeng.logicengine.stack.handler.Guarder

/**
 * 示例守卫器处理器。
 *
 * <p>
 * 该处理器始终允许当前状态转移。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@SuppressWarnings(['GrPackage', 'unused'])
class ExampleGuarderProcessor implements Processor {

    @Override
    boolean test(Guarder.Context context) {
        return true
    }
}
