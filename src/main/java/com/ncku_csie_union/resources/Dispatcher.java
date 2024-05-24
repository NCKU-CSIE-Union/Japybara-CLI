package com.ncku_csie_union.resources;
import com.ncku_csie_union.resources.interfaces.IDispatcher;

public class Dispatcher extends Base implements IDispatcher {
    private String logPrefix = "[Dispatcher]";
    private Executor[] executors;
    private Integer sharding;

    public Dispatcher() {
        logger.Log(logPrefix + "Constructor called");
        sharding = 16;
        executors = new Executor[sharding];
        Integer total_rate = 0;
        Integer rate = config.rate / sharding;
        for(int i = 1; i < sharding; i++) {
            executors[i] = new Executor(rate);
            total_rate += rate;
        }
        executors[0] = new Executor(config.rate - total_rate);
    }
    public void Execute() {
        logger.Log(logPrefix + "Execute called");
        for(int i = 0; i < sharding; i++) {
            executors[i].Execute();
        }
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