package com.ncku_csie_union.resources.interfaces.core;

public interface ILogger {
    // public static ILogger GetInstance();
    public void Log(String message);
    public void Error(String message);
    public void Warn(String message);
    public void Info(String message);
    public void Debug(String message);
    public void Trace(String message);
    public void Log(String message, Throwable t);
    public void Error(String message, Throwable t);
    public void Warn(String message, Throwable t);
    public void Info(String message, Throwable t);
    public void Debug(String message, Throwable t);
    public void Trace(String message, Throwable t);
}