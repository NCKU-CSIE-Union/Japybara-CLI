package com.ncku_csie_union.resources;
// import java.util.List;
// import java.lang.Thread;
import java.util.*; 
import com.ncku_csie_union.resources.interfaces.IExecutor;

public class Executor extends Base implements IExecutor {
    private Integer VUs;
    private Thread.Builder[] threadBuilders;
    private Thread[] threads;
    private String name;
    public static Integer ExecutorCount = 0;

    public Executor(Integer VUserCount) {
        this.logger.Log("[Executor] Constructor called");
        this.name = "Executor-" + ExecutorCount++;
        this.VUs = VUserCount;
        this.logger.Log("[Executor] VUs: " + this.VUs);
    }
    public void Init() {
        this.logger.Log("[Executor] Init called");
        this.threadBuilders = new Thread.Builder[this.VUs];
        for (int i = 0; i < this.VUs; i++) {
            this.logger.Log("[Executor] Creating thread " + i);
            threadBuilders[i] = Thread.ofVirtual().name(this.name + "-Thread-" + i);
        }
    }
    public void Execute() {
        this.logger.Log("[Executor] Execute called");
        this.threads = new Thread[this.VUs];
        for (int i = 0; i < this.VUs; i++) {
            this.logger.Log("[Executor] Starting thread " + i);
            Runnable task = () -> { 
                while(true){
                    System.out.println("Running VT" + Thread.currentThread().getName());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }; 
            this.threads[i] = threadBuilders[i].start(task);
        }
        this.logger.Log("[Executor] All threads started");
        this.logger.Log("[Executor] Waiting for threads to finish");
        for (int i = 0; i < this.VUs; i++) {
            try {
                this.threads[i].join();
            } catch (InterruptedException e) {
                this.logger.Log("[Executor] Thread " + i + " interrupted");
            }
        }
    }
    public void Stop() {
        this.logger.Log("[Executor] Stop called");
        for (int i = 0; i < this.VUs; i++) {
            this.logger.Log("[Executor] Stopping thread " + i);
            this.threads[i].interrupt();
        }
        this.logger.Log("[Executor] All threads stopped");
    }
}