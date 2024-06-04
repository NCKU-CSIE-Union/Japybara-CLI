package com.ncku_csie_union.resources.core;
import com.ncku_csie_union.resources.interfaces.core.ILogger;
import com.ncku_csie_union.resources.Config;

public class Logger implements ILogger {
    private static Config config = null;
    private static Logger instance;
    private Logger(){}
    private static final String RED = "\033[0;31m";
    private static final String YELLOW = "\033[0;33m";
    private static final String BLUE = "\033[0;34m";
    private static final String GREEN = "\033[0;32m";
    private static final String CYAN = "\033[0;36m";
    private static final String RESET = "\033[0m";

    public static synchronized Logger GetInstance() {
        if (instance == null) {
            instance = new Logger();
            config = Config.GetInstance();
        }
        return instance;
    }

    public void Log(String message) {
        System.out.println(message);
    }
    public void Error(String message) {
        System.out.println("["+RED+"ERROR"+RESET+"] "+RED + message + RESET);
    }
    public void Warn(String message) {
        System.out.println(YELLOW + message + RESET);
    }
    public void Info(String message) {
        System.out.println(message);
    }
    public void Debug(String message) {
        if (config.verbose) {
            System.out.println(message);
        }
    }
    public void Trace(String message) {
        System.out.println(message);
    }
    public void Log(String message, Throwable t) {
        System.out.println(message);
    }
    public void Error(String message, Throwable t) {
        System.out.println(" ["+RED+"ERROR"+RESET+"] "+RED + message + RESET);
    }
    public void Warn(String message, Throwable t) {
        System.out.println(YELLOW + message + RESET);
    }
    public void Info(String message, Throwable t) {
        System.out.println(message);
    }
    public void Debug(String message, Throwable t) {
        if (config.verbose) {
            System.out.println(message);
        }
    }
    public void Trace(String message, Throwable t) {
        System.out.println(message);
    }
    public void Capybara(){
        // read `ascii.txt` and print it
        try {
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader("ascii.txt"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    public void Logo(){
        // read `logo.txt` and print it
        try {
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader("logo.txt"));
            String line = null;
            System.out.println(BLUE);
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
            System.out.println(RESET);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    public void ProgressBar(long totalMiliseconds){
        // print progress bar
        Thread.startVirtualThread(() -> {
            long startTime = System.currentTimeMillis();
            long currentTime = System.currentTimeMillis();
            long elapsedTime = 0;
            int progress = 0;
            int barWidth = 50;
            while (elapsedTime < totalMiliseconds) {
                currentTime = System.currentTimeMillis();
                elapsedTime = currentTime - startTime;
                progress = (int) (barWidth * elapsedTime / totalMiliseconds);
                System.out.print("\r[");
                for (int i = 0; i < barWidth; i++) {
                    if (i < progress) {
                        System.out.print("=");
                    } else if (i == progress) {
                        System.out.print(">");
                    } else {
                        System.out.print(" ");
                    }
                }
                System.out.print("] " + progress * 2 + "%");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println();
            System.out.flush();
        });
    }
}