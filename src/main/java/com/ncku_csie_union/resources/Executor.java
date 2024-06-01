package com.ncku_csie_union.resources;
// import java.util.List;
// import java.lang.Thread;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.ncku_csie_union.resources.RateLimit;
import com.ncku_csie_union.resources.interfaces.IExecutor;
import com.ncku_csie_union.resources.model.TaskRecord;

public class Executor extends Base implements IExecutor {
    private Integer rate;
    private String name;
    private String logPrefix;
    private Boolean stop;
    private RateLimit rateLimit;
    private ExecutorService executor;
    public static Integer ExecutorCount = 1;
    private ResultCollector resultCollector;
    

    public Executor(Integer rate,ResultCollector resultCollector) {
        this.name = "Executor-" + ExecutorCount++;
        this.logPrefix = "[" + name + "]";
        this.logger.Debug(logPrefix + "Constructor called");
        this.rate = rate;
        this.stop = false;
        this.rateLimit = new RateLimit((int)((float)this.rate * 1.3),this.rate);
        this.executor = Executors.newVirtualThreadPerTaskExecutor();
        this.resultCollector = resultCollector;
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
                    Future<TaskRecord> future = executor.submit(new Task() );
                    TaskRecord record = future.get();
                    if(record==null) {
                        logger.Debug("record is null");
                        continue;
                    }
                    resultCollector.AppendReport(record);
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
        if(this.stop) {
            // already stopped before
            return;
        }
        logger.Debug(logPrefix + "Stop called");
        this.stop = true;
        logger.Debug(logPrefix + "Stop end");
    }
}