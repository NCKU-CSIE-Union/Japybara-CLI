package com.ncku_csie_union;
import com.ncku_csie_union.resources.Executor;
import com.ncku_csie_union.resources.CommandLine;
import com.ncku_csie_union.resources.Config;
import com.ncku_csie_union.resources.Task;
import com.ncku_csie_union.resources.core.Logger;
import com.ncku_csie_union.resources.Dispatcher;
import sun.misc.Signal;

public class Main {
    public static void main(String[] args) throws Exception {
        CommandLine commandLine = new CommandLine();
        commandLine.Parse(args);

        Config config = Config.GetInstance();
        Logger logger = Logger.GetInstance();
        Dispatcher dispatcher = new Dispatcher();
        // handle ctrl+c signal
        Signal.handle(new Signal("INT"), signal -> {
            logger.Warn("\nCtrl+C pressed\nGracefully shutdown...\n");
            dispatcher.Stop();
            dispatcher.ShowReport();
            System.exit(0);
        });
        
        Runnable runnable = () -> {
            try {
                Thread.sleep(config.GetDuration());
                dispatcher.Stop();
                dispatcher.ShowReport();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Thread.startVirtualThread(runnable);

        dispatcher.Execute();
    }
}
