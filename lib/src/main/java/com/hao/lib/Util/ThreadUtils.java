package com.hao.lib.Util;

import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ThreadUtils {
    static class ExecutorConfig {
        int num;
        Executor executor;

        public ExecutorConfig(int num, Executor executor) {
            this.num = num;
            this.executor = executor;
        }
    }

    static HashMap<String, ExecutorConfig> map = new HashMap<>();
    static int num;
    static ThreadUtils threadUtils;

    private ThreadUtils() {
    }

    private static class ThreadHelper {
        static final ThreadUtils threadUtils = new ThreadUtils();
    }

    public static void setNum(int num) {
        ThreadUtils.num = num;
    }

    /**
     * 创建一个带有标示的线程池
     *
     * @param tag
     * @return
     */
    public static ScheduledExecutorService createSch(String tag) {
        if (threadUtils == null) {
            threadUtils = ThreadHelper.threadUtils;
        }
        if (map.get(tag) == null || !(map.get(tag).executor instanceof ExecutorService) || (num != map.get(tag).num) && num != 0) {
            map.put(tag, new ExecutorConfig(num, Executors.newScheduledThreadPool(map.get(tag).num)));
            threadUtils.num = 0;
        }
        return (ScheduledExecutorService) map.get(tag).executor;
    }

    public static ExecutorService createFix(String tag) {
        if (threadUtils == null) {
            threadUtils = ThreadHelper.threadUtils;
        }
        if (map.get(tag) == null || !(map.get(tag) instanceof ExecutorService) || (num != map.get(tag).num) && num != 0) {
            map.put(tag, new ExecutorConfig(num, Executors.newFixedThreadPool(map.get(tag).num)));
            threadUtils.num = 0;
        }
        return (ExecutorService) map.get(tag).executor;
    }


    //单线程化线程池
    public static ExecutorService createSingle(String tag) {
        if (threadUtils == null) {
            threadUtils = ThreadHelper.threadUtils;
        }
        if (map.get(tag) == null || !(map.get(tag) instanceof ScheduledExecutorService) || (num != map.get(tag).num) && num != 0) {
            map.put(tag, new ExecutorConfig(num, Executors.newSingleThreadExecutor()));
        }
        return (ExecutorService) map.get(tag).executor;
    }

}
