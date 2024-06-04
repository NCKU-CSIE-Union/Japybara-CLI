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
}