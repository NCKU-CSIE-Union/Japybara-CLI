package com.ncku_csie_union;
import com.ncku_csie_union.resources.Executor;
import com.ncku_csie_union.resources.CommandLine;
public class Main {
    public static void main(String[] args) throws Exception {
        CommandLine commandLine = new CommandLine();
        commandLine.Parse(args);
        System.out.println("Hello, World!");

        Executor executor = new Executor(2);
        Runnable runnable = () -> {
            try {
                Thread.sleep(5000);
                System.out.println("Try to stop executor");
                executor.Stop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Thread.startVirtualThread(runnable);

        executor.Init();
        executor.Execute();
    }
}
