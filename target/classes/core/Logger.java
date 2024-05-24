package main.resources.core;
import main.resources.interfaces.core.ILogger;

public class Logger implements ILogger {
    private static Logger instance;
    private Logger(){}

    public static synchronized Logger GetInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void Log(String message) {
        System.out.println(message);
    }
    public void Error(String message) {
        System.out.println(message);
    }
    public void Warn(String message) {
        System.out.println(message);
    }
    public void Info(String message) {
        System.out.println(message);
    }
    public void Debug(String message) {
        System.out.println(message);
    }
    public void Trace(String message) {
        System.out.println(message);
    }
    public void Log(String message, Throwable t) {
        System.out.println(message);
    }
    public void Error(String message, Throwable t) {
        System.out.println(message);
    }
    public void Warn(String message, Throwable t) {
        System.out.println(message);
    }
    public void Info(String message, Throwable t) {
        System.out.println(message);
    }
    public void Debug(String message, Throwable t) {
        System.out.println(message);
    }
    public void Trace(String message, Throwable t) {
        System.out.println(message);
    }
}