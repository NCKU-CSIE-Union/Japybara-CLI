package com.ncku_csie_union;
import com.ncku_csie_union.resources.Executor;
import com.ncku_csie_union.resources.CommandLine;
public class Main {
    public static void main(String[] args) throws Exception {
        CommandLine commandLine = new CommandLine();
        commandLine.Parse(args);
        System.out.println("Hello, World!");
        
        // Executor executor = new Executor(2);
        // executor.Init();
        // executor.Execute();
        // executor.Stop();
    }
}
