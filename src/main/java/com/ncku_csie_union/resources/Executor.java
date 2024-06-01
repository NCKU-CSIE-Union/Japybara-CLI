package com.ncku_csie_union.resources;
// import java.util.List;
// import java.lang.Thread;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.ncku_csie_union.resources.RateLimit;
import com.ncku_csie_union.resources.interfaces.IExecutor;

public class Executor extends Base implements IExecutor {
    private Integer rate;
    private String name;
    private String logPrefix;
    private Boolean stop;
    private RateLimit rateLimit;
    private ExecutorService executor;
    public static Integer ExecutorCount = 1;
    

    public Executor(Integer rate,ExecutorService exec) {
        this.name = "Executor-" + ExecutorCount++;
        this.logPrefix = "[" + name + "]";
        this.logger.Debug(logPrefix + "Constructor called");
        this.rate = rate;
        this.stop = false;
        this.rateLimit = new RateLimit((int)((float)this.rate * 1.3),this.rate);
        this.executor = Executors.newVirtualThreadPerTaskExecutor();
        this.logger.Debug(logPrefix + "Rate: " + rate);
    }
    public void Init() {
        this.logger.Debug(logPrefix + "Init called");
    }
    public void Execute() {
        this.logger.Debug(logPrefix + "Execute called");

        new Thread( ()->{
            try {
                while(!this.stop) {
                    this.rateLimit.GetToken();
                    // executor.submit(mockTask);
                    Future<?> future = executor.submit(new Task() );
                    future.get();
                }
            }
            catch (Exception e) {
                logger.Error("Exception: " + e);
            }
            finally {
                executor.shutdown();
                logger.Debug("ExecutorService is shutdown");
            }
        }).start();
        
        logger.Debug(logPrefix + "Execute end");
    }
    public void Stop() {
        logger.Debug(logPrefix + "Stop called");
        this.stop = true;
        logger.Debug(logPrefix + "Stop end");
    }
}