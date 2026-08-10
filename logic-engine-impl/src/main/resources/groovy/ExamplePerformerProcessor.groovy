import com.dwarfeng.logicengine.impl.handler.performer.groovy.Processor
import com.dwarfeng.logicengine.stack.bean.dto.TaskVariableUpsertInfo
import com.dwarfeng.logicengine.stack.handler.Performer

/**
 * 示例执行器处理器。
 *
 * <p>
 * 该处理器在执行状态转移动作时维护任务变量。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@SuppressWarnings(['GrPackage', 'unused'])
class ExamplePerformerProcessor implements Processor {

    @Override
    void execute(Performer.Context context) {
        context.upsertTaskVariable(new TaskVariableUpsertInfo(
                context.task.key, "last_performer_message", 0, "executed"
        ))
    }
}
