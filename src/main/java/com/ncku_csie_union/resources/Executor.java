package com.ncku_csie_union.resources;
// import java.util.List;
// import java.lang.Thread;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ncku_csie_union.resources.RateLimit;
import com.ncku_csie_union.resources.interfaces.IExecutor;

public class Executor extends Base implements IExecutor {
    private Integer rate;
    private String name;
    private String logPrefix;
    private Boolean stop;
    private RateLimit rateLimit;
    public static Integer ExecutorCount = 0;
    

    public Executor(Integer rate) {
        this.logger.Log("[Executor] Constructor called");
        this.rate = rate;
        this.name = "Executor-" + ExecutorCount++;
        this.logPrefix = "[" + name + "]";
        this.stop = false;
        this.rateLimit = new RateLimit(this.rate,this.rate);
        this.logger.Log("[Executor] VUs: " + this.rate);
    }
    public void Init() {
        this.logger.Log(logPrefix + "Init called");
    }
    public void Execute() {
        this.logger.Log(logPrefix + "Execute called");
        Runnable mockTask = () -> { 
            System.out.println("=========== Running VT:" + Thread.currentThread().getName() + " Start ===========");
            System.out.println("[x] " + Thread.currentThread() + "...");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("=========== Running VT:" + Thread.currentThread().getName() + " Done ===========");
        }; 
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            while(!this.stop) {
                this.rateLimit.GetToken();
                executor.submit(mockTask);
            }
        }
        finally {
            System.out.println("ExecutorService is shutdown");
        }
        this.logger.Log(logPrefix + "Execute end");
    }
    public void Stop() {
        this.logger.Log(logPrefix + "Stop called");
        this.stop = true;
        this.logger.Log(logPrefix + "Stop end");
    }
}