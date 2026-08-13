package com.dwarfeng.logicengine.stack.struct;

import com.dwarfeng.logicengine.stack.bean.entity.GuarderInfo;
import com.dwarfeng.logicengine.stack.bean.entity.PerformerInfo;
import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.logicengine.stack.bean.entity.State;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.logicengine.stack.handler.Guarder;
import com.dwarfeng.logicengine.stack.handler.Performer;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.List;
import java.util.Map;

/**
 * 作业本地缓存。
 *
 * <p>
 * 缓存以部件为边界保存一次完整的状态机配置和插件定义。插件执行器不放入缓存，
 * 作业运行时通过缓存的插件定义创建独立执行器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class JobLocalCache {

    private final Section section;
    private final Map<StateKey, State> states;
    private final State initialState;
    private final List<State> terminalStates;
    private final List<GuarderInfo> guarders;
    private final Map<LongIdKey, Guarder> guarderMap;
    private final List<PerformerInfo> performers;
    private final Map<LongIdKey, Performer> performerMap;
    private final List<String> warnings;

    public JobLocalCache(
            Section section,
            Map<StateKey, State> states,
            State initialState,
            List<State> terminalStates,
            List<GuarderInfo> guarders,
            Map<LongIdKey, Guarder> guarderMap,
            List<PerformerInfo> performers,
            Map<LongIdKey, Performer> performerMap,
            List<String> warnings
    ) {
        this.section = section;
        this.states = states;
        this.initialState = initialState;
        this.terminalStates = terminalStates;
        this.guarders = guarders;
        this.guarderMap = guarderMap;
        this.performers = performers;
        this.performerMap = performerMap;
        this.warnings = warnings;
    }

    public Section getSection() {
        return section;
    }

    public Map<StateKey, State> getStates() {
        return states;
    }

    public State getInitialState() {
        return initialState;
    }

    public List<State> getTerminalStates() {
        return terminalStates;
    }

    public List<GuarderInfo> getGuarders() {
        return guarders;
    }

    public Map<LongIdKey, Guarder> getGuarderMap() {
        return guarderMap;
    }

    public List<PerformerInfo> getPerformers() {
        return performers;
    }

    public Map<LongIdKey, Performer> getPerformerMap() {
        return performerMap;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    @Override
    public String toString() {
        return "JobLocalCache{" +
                "section=" + section +
                ", states=" + states +
                ", initialState=" + initialState +
                ", terminalStates=" + terminalStates +
                ", guarders=" + guarders +
                ", guarderMap=" + guarderMap +
                ", performers=" + performers +
                ", performerMap=" + performerMap +
                ", warnings=" + warnings +
                '}';
    }
}
