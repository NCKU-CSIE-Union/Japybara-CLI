package com.ncku_csie_union.resources;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.ncku_csie_union.resources.interfaces.IDispatcher;

public class Dispatcher extends Base implements IDispatcher {
    private String logPrefix = "[Dispatcher]";
    private Executor[] executors;
    private Integer sharding;
    private ExecutorService executorService;

    private Integer getSharding(){
        return 300;
    }

    public Dispatcher() {
        logger.Log(logPrefix + "Constructor called");
        sharding = getSharding();
        executors = new Executor[sharding];
        Integer total_rate = 0;
        Integer rate = config.rate / sharding;
        logger.Log(logPrefix + "Sharding: " + sharding);
        logger.Log(logPrefix + "Rate: " + rate);
        executorService = Executors.newVirtualThreadPerTaskExecutor();
        for(int i = 1; i < sharding; i++) {
            executors[i] = new Executor(rate, executorService);
            total_rate += rate;
        }
        executors[0] = new Executor(config.rate- total_rate, executorService);
    }
    public void Execute() {
        logger.Log(logPrefix + "Execute called");
        for(int i = 0; i < sharding; i++) {
            executors[i].Execute();
        }
        logger.Log(logPrefix + "All executors started");
        logger.Log(logPrefix + "Execute end");
    }
    public void Stop() {
        logger.Log(logPrefix + "Stop called");
        for(int i = 0; i < sharding; i++) {
            executors[i].Stop();
        }
        logger.Log(logPrefix + "Stop end");
    }
}