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
    private Random random = new Random(System.currentTimeMillis());
    public static Integer ExecutorCount = 1;
    

    public Executor(Integer rate,ExecutorService exec) {
        this.name = "Executor-" + ExecutorCount++;
        this.logPrefix = "[" + name + "]";
        this.logger.Log(logPrefix + "Constructor called");
        this.rate = rate;
        this.stop = false;
        this.rateLimit = new RateLimit((int)((float)this.rate * 1.3),this.rate);
        this.executor = Executors.newVirtualThreadPerTaskExecutor();
        this.logger.Log(logPrefix + "Rate: " + rate);
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
                // Simulate some work
                // sleep for a random time between 0 ~ 2 seconds
                int sleepTime = random.nextInt(10) ;
                System.out.println("Sleeping for " + sleepTime + "ms");
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                executor.shutdown();
                e.printStackTrace();
                Thread.currentThread().interrupt();

            }
            System.out.println("=========== Running VT:" + Thread.currentThread().getName() + " Done ===========");
        }; 

        new Thread( ()->{
            try {
                while(!this.stop) {
                    this.rateLimit.GetToken();
                    // executor.submit(mockTask);
                    Future<?> future = executor.submit(mockTask);
                    future.get();
                }
            }
            catch (Exception e) {
                System.out.println("Exception: " + e);
            }
            finally {
                executor.shutdown();
                System.out.println("ExecutorService is shutdown");
            }
        }).start();
        
        this.logger.Log(logPrefix + "Execute end");
    }
    public void Stop() {
        this.logger.Log(logPrefix + "Stop called");
        this.stop = true;
        this.logger.Log(logPrefix + "Stop end");
    }
}