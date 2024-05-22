package main.resources.interfaces;
import main.resources.Config;

public interface ICommandLine {
    public Config Parse(String[] args);
}