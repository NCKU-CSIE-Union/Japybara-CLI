package main.resources.interfaces;
import main.resources.interfaces.core.ILock;

public interface IShareMemory {
    public ILock duration = null;
    public ILock rate = null;
    public ILock dataReceived = null;
}