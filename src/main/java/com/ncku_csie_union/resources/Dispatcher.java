package com.ncku_csie_union.resources;
import com.ncku_csie_union.resources.interfaces.IDispatcher;

public class Dispatcher extends Base implements IDispatcher {
    private String logPrefix = "[Dispatcher]";
    private Executor[] executors;
    private Integer sharding;

    private Integer getSharding(){
        // if(config.rate <= 100) {
        //     return 1;
        // }
        // else if(config.rate <= 500) {
        //     return 2;
        // }
        // else if(config.rate <= 1000) {
        //     return 4;
        // }
        // else if (config.rate <= 2000) {
        //     return 8;
        // }
        // else if(config.rate <= 4000) {
        //     return 16;
        // }
        // else if(config.rate <= 8000) {
        //     return 32;
        // }
        // else if(config.rate <= 16000) {
        //     return 64;
        // }
        // else if(config.rate <= 32000) {
        //     return 128;
        // }
        return 1;
    }

    public Dispatcher() {
        logger.Log(logPrefix + "Constructor called");
        sharding = getSharding();
        executors = new Executor[sharding];
        Integer total_rate = 0;
        Integer rate = config.rate / sharding;
        logger.Log(logPrefix + "Sharding: " + sharding);
        logger.Log(logPrefix + "Rate: " + rate);
        for(int i = 1; i < sharding; i++) {
            executors[i] = new Executor(rate);
            total_rate += rate;
        }
        executors[0] = new Executor(config.rate- total_rate);
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