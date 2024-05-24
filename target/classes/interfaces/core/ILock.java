package main.resources.interfaces.core;

public interface ILock {
    public void Lock();
    public void Unlock();
    
    public Object Get();
    public void Set(Object value);
}