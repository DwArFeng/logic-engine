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
import com.dwarfeng.logicengine.stack.exception.PerformerException;

import javax.annotation.Nullable;

/**
 * 执行器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface Performer {

    /**
     * 生成一个新的执行器。
     *
     * @return 新的执行器。
     * @throws PerformerException 执行器异常。
     */
    Executor newExecutor() throws PerformerException;

    /**
     * 执行器执行器。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    interface Executor {

        /**
         * 初始化执行器。
         *
         * @param context 指定的执行器上下文。
         */
        void init(Context context);

        /**
         * 执行状态转移动作。
         *
         * <p>
         * 该方法被调用时，需要按照预定的逻辑执行状态转移动作。执行器可以通过 {@link Context} 获取任务及状态信息，
         * 并对任务变量进行维护，或创建任务事件。
         *
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        void execute() throws Exception;
    }

    /**
     * 执行器上下文。
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
         * 获取锚点状态。
         *
         * @return 锚点状态。
         */
        State getAnchorState();

        /**
         * 获取目标状态。
         *
         * @return 目标状态。
         */
        State getTargetState();

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
