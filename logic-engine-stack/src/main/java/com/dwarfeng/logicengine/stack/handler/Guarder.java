package com.dwarfeng.logicengine.stack.handler;

import com.dwarfeng.logicengine.stack.bean.dto.TaskEventCreateInfo;
import com.dwarfeng.logicengine.stack.bean.dto.TaskUpdateModalInfo;
import com.dwarfeng.logicengine.stack.bean.dto.TaskVariableInspectInfo;
import com.dwarfeng.logicengine.stack.bean.dto.TaskVariableInspectResult;
import com.dwarfeng.logicengine.stack.bean.dto.TaskVariableRemoveInfo;
import com.dwarfeng.logicengine.stack.bean.dto.TaskVariableUpsertInfo;
import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.logicengine.stack.bean.entity.State;
import com.dwarfeng.logicengine.stack.bean.entity.Task;
import com.dwarfeng.logicengine.stack.exception.GuarderException;

import javax.annotation.Nullable;

/**
 * 守卫器。
 *
 * <p>
 * 守卫器用于判断当前状态是否允许向目标状态转移。实现可以通过 {@link Context} 获取任务及状态信息，
 * 并查看或维护任务变量，或创建任务事件。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface Guarder {

    /**
     * 生成一个新的守卫器执行器。
     *
     * @return 新的守卫器执行器。
     * @throws GuarderException 守卫器异常。
     */
    Executor newExecutor() throws GuarderException;

    /**
     * 守卫器执行器。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    interface Executor {

        /**
         * 初始化守卫器执行器。
         *
         * @param context 指定的守卫器上下文。
         */
        void init(Context context);

        /**
         * 判断状态转移条件是否成立。
         *
         * @return 条件是否成立。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        boolean test() throws Exception;
    }

    /**
     * 守卫器上下文。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    interface Context {

        /**
         * 获取任务。
         *
         * @return 任务。
         */
        Task getTask();

        /**
         * 获取区段。
         *
         * @return 区段。
         */
        Section getSection();

        /**
         * 获取当前状态。
         *
         * @return 当前状态。
         */
        State getCurrentState();

        /**
         * 查看任务变量。
         *
         * <p>
         * 如果指定的任务变量不存在，则返回 <code>null</code>。
         *
         * @param info 任务变量查看信息。
         * @return 任务变量查看结果。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        @Nullable
        TaskVariableInspectResult inspectTaskVariable(TaskVariableInspectInfo info) throws Exception;

        /**
         * 插入/更新任务变量。
         *
         * @param info 任务变量插入/更新信息。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        void upsertTaskVariable(TaskVariableUpsertInfo info) throws Exception;

        /**
         * 删除任务变量。
         *
         * @param info 任务变量删除信息。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        void removeTaskVariable(TaskVariableRemoveInfo info) throws Exception;

        /**
         * 更新任务模态。
         *
         * @param info 任务更新模态信息。
         * @throws Exception 方法执行过程中发生的任何异常。
         * @since 1.2.0
         */
        void updateTaskModal(TaskUpdateModalInfo info) throws Exception;

        /**
         * 创建任务事件。
         *
         * @param info 任务事件创建信息。
         * @throws Exception 方法执行过程中发生的任何异常。
         * @since 1.2.0
         */
        void createTaskEvent(TaskEventCreateInfo info) throws Exception;
    }
}
