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
    private ResultAggregator resultAggregator;
    private ResultCollector[] resultCollectors;

    public Dispatcher() {
        logger.Debug(logPrefix + "Constructor called");
        vus = config.vu;
        executors = new Executor[vus];
        Integer total_rate = 0;
        Integer rate = config.rate / vus;
        logger.Debug(logPrefix + "vus: " + vus);
        logger.Debug(logPrefix + "Rate: " + rate);
        resultAggregator = new ResultAggregator();
        resultCollectors = new ResultCollector[vus];
        for(int i = 0; i < vus; i++) {
            resultCollectors[i] = new ResultCollector();
        }
        executorService = Executors.newVirtualThreadPerTaskExecutor();
        for(int i = 1; i < vus; i++) {
            executors[i] = new Executor(rate, resultCollectors[i]);
            total_rate += rate;
        }
        executors[0] = new Executor(config.rate- total_rate, resultCollectors[0]);
    }
    public void Execute() {
        logger.Debug(logPrefix + "Execute called");
        for(int i = 0; i < vus; i++) {
            executors[i].Execute();
        }
        logger.Debug(logPrefix + "All executors started");
        logger.Debug(logPrefix + "Execute end");
    }
    public void Stop() {
        logger.Debug(logPrefix + "Stop called");
        for(int i = 0; i < vus; i++) {
            executors[i].Stop();
        }
        for (int i = 0; i < vus; i++) {
            // executors[i].WaitTermination();
            resultAggregator.RegisterResultCollector(resultCollectors[i]);
        }
        logger.Debug(logPrefix + "Stop end");
    }
    public void ShowReport(){
        logger.Debug(logPrefix + "ShowReport called");
        resultAggregator.ShowReport();
    }
}