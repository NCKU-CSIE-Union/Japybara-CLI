package com.ncku_csie_union.resources;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.ncku_csie_union.resources.interfaces.IDispatcher;

public class Dispatcher extends Base implements IDispatcher {
    private String logPrefix = "[Dispatcher]";
    private Executor[] executors;
    private Integer vus;
    private ExecutorService executorService;

    public Dispatcher() {
        logger.Log(logPrefix + "Constructor called");
        vus = config.vu;
        executors = new Executor[vus];
        Integer total_rate = 0;
        Integer rate = config.rate / vus;
        logger.Log(logPrefix + "vus: " + vus);
        logger.Log(logPrefix + "Rate: " + rate);
        executorService = Executors.newVirtualThreadPerTaskExecutor();
        for(int i = 1; i < vus; i++) {
            executors[i] = new Executor(rate, executorService);
            total_rate += rate;
        }
        executors[0] = new Executor(config.rate- total_rate, executorService);
    }
    public void Execute() {
        logger.Log(logPrefix + "Execute called");
        for(int i = 0; i < vus; i++) {
            executors[i].Execute();
        }
        logger.Log(logPrefix + "All executors started");
        logger.Log(logPrefix + "Execute end");
    }
    public void Stop() {
        logger.Log(logPrefix + "Stop called");
        for(int i = 0; i < vus; i++) {
            executors[i].Stop();
        }
        logger.Log(logPrefix + "Stop end");
    }
}