package com.ncku_csie_union;
import com.ncku_csie_union.resources.Executor;
import com.ncku_csie_union.resources.CommandLine;
import com.ncku_csie_union.resources.Dispatcher;
public class Main {
    public static void main(String[] args) throws Exception {
        CommandLine commandLine = new CommandLine();
        commandLine.Parse(args);
        System.out.println("Hello, World!");

        Dispatcher dispatcher = new Dispatcher();
        Runnable runnable = () -> {
            try {
                Thread.sleep(5000);
                System.out.println("Try to stop executor");
                dispatcher.Stop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Thread.startVirtualThread(runnable);

        dispatcher.Execute();
        System.out.println("Main Thread Exit");
    }
}
