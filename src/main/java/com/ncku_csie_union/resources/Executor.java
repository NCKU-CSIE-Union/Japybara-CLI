package com.ncku_csie_union.resources;
// import java.util.List;
// import java.lang.Thread;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ncku_csie_union.resources.RateLimit;
import com.ncku_csie_union.resources.interfaces.IExecutor;

public class Executor extends Base implements IExecutor {
    private Integer VUs;
    private String name;
    private Boolean stop;
    private RateLimit rateLimit;
    public static Integer ExecutorCount = 0;
    

    public Executor(Integer VUserCount) {
        this.logger.Log("[Executor] Constructor called");
        this.VUs = VUserCount;
        this.name = "Executor-" + ExecutorCount++;
        this.stop = false;
        this.rateLimit = new RateLimit(config.rate,config.rate);
        this.logger.Log("[Executor] VUs: " + this.VUs);
    }
    public void Init() {
        this.logger.Log("[Executor] Init called");
    }
    public void Execute() {
        this.logger.Log("[Executor:Execute] called");
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
        this.logger.Log("[Executor:Execute] end");
    }
    public void Stop() {
        this.logger.Log("[Executor:Stop] called");
        this.stop = true;
        this.logger.Log("[Executor:Stop] end");
    }
}