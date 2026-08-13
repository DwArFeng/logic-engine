package com.dwarfeng.logicengine.impl.handler;

import com.dwarfeng.logicengine.sdk.util.Constants;
import com.dwarfeng.logicengine.stack.bean.entity.GuarderInfo;
import com.dwarfeng.logicengine.stack.bean.entity.PerformerInfo;
import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.logicengine.stack.bean.entity.State;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.logicengine.stack.handler.*;
import com.dwarfeng.logicengine.stack.service.GuarderInfoMaintainService;
import com.dwarfeng.logicengine.stack.service.PerformerInfoMaintainService;
import com.dwarfeng.logicengine.stack.service.SectionMaintainService;
import com.dwarfeng.logicengine.stack.service.StateMaintainService;
import com.dwarfeng.logicengine.stack.struct.JobLocalCache;
import com.dwarfeng.subgrade.impl.handler.Fetcher;
import com.dwarfeng.subgrade.impl.handler.GeneralLocalCacheHandler;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class JobLocalCacheHandlerImpl implements JobLocalCacheHandler {

    private static final Comparator<GuarderInfo> GUARDER_COMPARATOR = Comparator
            .comparingInt(GuarderInfo::getIndex)
            .thenComparing(info -> info.getKey() == null ? Long.MAX_VALUE : info.getKey().getLongId());
    private static final Comparator<PerformerInfo> PERFORMER_COMPARATOR = Comparator
            .comparingInt(PerformerInfo::getIndex)
            .thenComparing(info -> info.getKey() == null ? Long.MAX_VALUE : info.getKey().getLongId());

    private final GeneralLocalCacheHandler<LongIdKey, JobLocalCache> handler;

    public JobLocalCacheHandlerImpl(JobLocalCacheFetcher fetcher) {
        this.handler = new GeneralLocalCacheHandler<>(fetcher);
    }

    @Override
    @BehaviorAnalyse
    public boolean exists(LongIdKey key) throws HandlerException {
        return handler.exists(key);
    }

    @Override
    @BehaviorAnalyse
    public JobLocalCache get(LongIdKey key) throws HandlerException {
        return handler.get(key);
    }

    @Override
    @BehaviorAnalyse
    public boolean remove(LongIdKey key) {
        return handler.remove(key);
    }

    @Override
    @BehaviorAnalyse
    public void clear() {
        handler.clear();
    }

    @Component
    public static class JobLocalCacheFetcher implements Fetcher<LongIdKey, JobLocalCache> {

        private final SectionMaintainService sectionMaintainService;
        private final StateMaintainService stateMaintainService;
        private final GuarderInfoMaintainService guarderInfoMaintainService;
        private final PerformerInfoMaintainService performerInfoMaintainService;
        private final GuarderHandler guarderHandler;
        private final PerformerHandler performerHandler;

        public JobLocalCacheFetcher(
                SectionMaintainService sectionMaintainService,
                StateMaintainService stateMaintainService,
                GuarderInfoMaintainService guarderInfoMaintainService,
                PerformerInfoMaintainService performerInfoMaintainService,
                GuarderHandler guarderHandler,
                PerformerHandler performerHandler
        ) {
            this.sectionMaintainService = sectionMaintainService;
            this.stateMaintainService = stateMaintainService;
            this.guarderInfoMaintainService = guarderInfoMaintainService;
            this.performerInfoMaintainService = performerInfoMaintainService;
            this.guarderHandler = guarderHandler;
            this.performerHandler = performerHandler;
        }

        @Override
        @BehaviorAnalyse
        @Transactional(
                transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class
        )
        public boolean exists(LongIdKey key) throws Exception {
            return sectionMaintainService.exists(key);
        }

        @Override
        @BehaviorAnalyse
        @Transactional(
                transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class
        )
        public JobLocalCache fetch(LongIdKey key) throws Exception {
            Section section = sectionMaintainService.get(key);
            List<State> stateList = stateMaintainService.lookupAsList(
                    StateMaintainService.CHILD_FOR_SECTION, new Object[]{key}
            );
            Map<StateKey, State> states = new HashMap<>();
            State initialState = null;
            List<State> terminalStates = new ArrayList<>();
            for (State state : stateList) {
                if (states.put(state.getKey(), state) != null) {
                    throw new IllegalStateException("部件状态主键重复: " + state.getKey());
                }
                if (state.getType() == Constants.STATE_TYPE_INITIAL) {
                    if (initialState != null) {
                        throw new IllegalStateException("部件只能存在一个初始状态: " + key);
                    }
                    initialState = state;
                }
                if (state.getType() == Constants.STATE_TYPE_TERMINAL) {
                    terminalStates.add(state);
                }
            }
            if (initialState == null) {
                throw new IllegalStateException("部件不存在初始状态: " + key);
            }
            if (terminalStates.isEmpty()) {
                throw new IllegalStateException("部件不存在终止状态: " + key);
            }

            List<GuarderInfo> guarders = guarderInfoMaintainService.lookupAsList(
                    GuarderInfoMaintainService.CHILD_FOR_SECTION, new Object[]{key}
            );
            guarders.sort(GUARDER_COMPARATOR);
            Map<LongIdKey, Guarder> guarderMap = new HashMap<>();
            Map<StateKey, List<StateKey>> edges = new HashMap<>();
            List<String> warnings = new ArrayList<>();
            for (GuarderInfo guarderInfo : guarders) {
                if (!Objects.equals(key, guarderInfo.getSectionKey())) {
                    throw new IllegalStateException("守卫器所属部件不匹配: " + guarderInfo.getKey());
                }
                validateStateReference(states, guarderInfo.getAnchorStateKey(), "守卫器锚点", guarderInfo.getKey());
                validateStateReference(states, guarderInfo.getTargetStateKey(), "守卫器目标", guarderInfo.getKey());
                if (guarderInfo.isEnabled()) {
                    if (guarderInfo.getTargetStateKey().equals(guarderInfo.getAnchorStateKey())) {
                        warnings.add("守卫器存在自转换: " + guarderInfo.getKey());
                    }
                    edges.computeIfAbsent(guarderInfo.getAnchorStateKey(), ignored -> new ArrayList<>())
                            .add(guarderInfo.getTargetStateKey());
                    guarderMap.put(guarderInfo.getKey(), guarderHandler.make(
                            guarderInfo.getType(), guarderInfo.getParam()
                    ));
                }
            }

            List<PerformerInfo> performers = performerInfoMaintainService.lookupAsList(
                    PerformerInfoMaintainService.CHILD_FOR_SECTION, new Object[]{key}
            );
            performers.sort(PERFORMER_COMPARATOR);
            Map<LongIdKey, Performer> performerMap = new HashMap<>();
            for (PerformerInfo performerInfo : performers) {
                if (!Objects.equals(key, performerInfo.getSectionKey())) {
                    throw new IllegalStateException("执行器所属部件不匹配: " + performerInfo.getKey());
                }
                validateStateReference(states, performerInfo.getAnchorStateKey(), "执行器锚点", performerInfo.getKey());
                validateStateReference(states, performerInfo.getTargetStateKey(), "执行器目标", performerInfo.getKey());
                if (performerInfo.isEnabled()) {
                    performerMap.put(performerInfo.getKey(), performerHandler.make(
                            performerInfo.getType(), performerInfo.getParam()
                    ));
                }
            }

            for (State terminalState : terminalStates) {
                if (!edges.getOrDefault(terminalState.getKey(), Collections.emptyList()).isEmpty()) {
                    throw new IllegalStateException("终止状态不能存在启用的状态转移: " + terminalState.getKey());
                }
            }
            Set<StateKey> reachable = new HashSet<>();
            collectReachable(initialState.getKey(), edges, reachable);
            for (State state : stateList) {
                if (!reachable.contains(state.getKey())) {
                    warnings.add("状态从初始状态不可达: " + state.getKey());
                }
            }
            for (State terminalState : terminalStates) {
                if (!canReachTerminal(terminalState.getKey(), edges, terminalStates, new HashSet<>())) {
                    throw new IllegalStateException("状态无法到达终止状态: " + terminalState.getKey());
                }
            }
            for (State state : stateList) {
                if (!canReachTerminal(state.getKey(), edges, terminalStates, new HashSet<>())) {
                    throw new IllegalStateException("状态无法到达终止状态: " + state.getKey());
                }
            }

            return new JobLocalCache(
                    section,
                    Collections.unmodifiableMap(new HashMap<>(states)),
                    initialState,
                    Collections.unmodifiableList(new ArrayList<>(terminalStates)),
                    Collections.unmodifiableList(new ArrayList<>(guarders)),
                    Collections.unmodifiableMap(new HashMap<>(guarderMap)),
                    Collections.unmodifiableList(new ArrayList<>(performers)),
                    Collections.unmodifiableMap(new HashMap<>(performerMap)),
                    Collections.unmodifiableList(new ArrayList<>(warnings))
            );
        }

        private static void validateStateReference(
                Map<StateKey, State> states, StateKey stateKey, String label, LongIdKey infoKey
        ) {
            if (stateKey == null || !states.containsKey(stateKey)) {
                throw new IllegalStateException(label + "不存在: " + infoKey + ", stateKey=" + stateKey);
            }
        }

        private static void collectReachable(
                StateKey stateKey, Map<StateKey, List<StateKey>> edges, Set<StateKey> reachable
        ) {
            if (!reachable.add(stateKey)) {
                return;
            }
            for (StateKey target : edges.getOrDefault(stateKey, Collections.emptyList())) {
                collectReachable(target, edges, reachable);
            }
        }

        private static boolean canReachTerminal(
                StateKey stateKey, Map<StateKey, List<StateKey>> edges,
                List<State> terminalStates, Set<StateKey> visiting
        ) {
            for (State terminalState : terminalStates) {
                if (terminalState.getKey().equals(stateKey)) {
                    return true;
                }
            }
            if (!visiting.add(stateKey)) {
                return false;
            }
            try {
                for (StateKey target : edges.getOrDefault(stateKey, Collections.emptyList())) {
                    if (canReachTerminal(target, edges, terminalStates, visiting)) {
                        return true;
                    }
                }
                return false;
            } finally {
                visiting.remove(stateKey);
            }
        }
    }
}
