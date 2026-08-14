package com.dwarfeng.logicengine.sdk.handler.dispatcher;

import com.dwarfeng.logicengine.stack.exception.DispatcherException;
import com.dwarfeng.logicengine.stack.exception.DispatcherExecutionException;
import com.dwarfeng.logicengine.stack.exception.DispatcherNotStartException;
import com.dwarfeng.logicengine.stack.handler.Dispatcher;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 调度器的抽象实现。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class AbstractDispatcher implements Dispatcher {

    protected String dispatcherType;

    private final Lock lock = new ReentrantLock();

    /**
     * 启动标记。
     *
     * <p>
     * 用于标记调度处理器是否已经启动。
     *
     * <p>
     * 实现类可以通过调用 {@link #isStarted()} 方法来获取该标记，以判断调度处理器是否已经启动。<br>
     * 实现类不允许直接修改该标记。
     */
    private boolean startFlag;

    public AbstractDispatcher() {
    }

    public AbstractDispatcher(String dispatcherType) {
        this.dispatcherType = dispatcherType;
    }

    @Override
    public boolean supportType(String type) {
        lock.lock();
        try {
            return dispatcherType.equals(type);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void start() throws DispatcherException {
        lock.lock();
        try {
            if (startFlag) {
                return;
            }
            try {
                doStart();
            } catch (DispatcherExecutionException e) {
                throw e;
            } catch (Exception e) {
                throw new DispatcherExecutionException(e);
            }
            startFlag = true;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 启动调度器的具体实现。
     *
     * @throws Exception 启动过程中抛出的任何异常。
     */
    protected abstract void doStart() throws Exception;

    @Override
    public void stop() throws DispatcherException {
        lock.lock();
        try {
            if (!startFlag) {
                return;
            }
            try {
                doStop();
            } catch (DispatcherExecutionException e) {
                throw e;
            } catch (Exception e) {
                throw new DispatcherExecutionException(e);
            }
            startFlag = false;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 停止调度器的具体实现。
     *
     * @throws Exception 停止过程中抛出的任何异常。
     */
    protected abstract void doStop() throws Exception;

    @Override
    public void dispatch(LongIdKey sectionKey) throws DispatcherException {
        lock.lock();
        try {
            if (!startFlag) {
                throw new DispatcherNotStartException();
            }
            try {
                doDispatch(sectionKey);
            } catch (DispatcherExecutionException e) {
                throw e;
            } catch (Exception e) {
                throw new DispatcherExecutionException(e);
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * 调度部件执行请求的具体实现。
     *
     * @param sectionKey 部件主键。
     * @throws Exception 调度过程中抛出的任何异常。
     */
    protected abstract void doDispatch(LongIdKey sectionKey) throws Exception;

    /**
     * 返回调度器是否已经启动。
     *
     * @return 调度器是否已经启动。
     */
    protected boolean isStarted() {
        lock.lock();
        try {
            return startFlag;
        } finally {
            lock.unlock();
        }
    }

    public String getDispatcherType() {
        return dispatcherType;
    }

    public void setDispatcherType(String dispatcherType) {
        this.dispatcherType = dispatcherType;
    }

    @Override
    public String toString() {
        return "AbstractDispatcher{" +
                "dispatcherType='" + dispatcherType + '\'' +
                ", lock=" + lock +
                ", startFlag=" + startFlag +
                '}';
    }
}
