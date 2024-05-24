package com.ncku_csie_union.resources.interfaces;
import com.ncku_csie_union.resources.interfaces.core.ILock;

public interface IShareMemory {
    public ILock duration = null;
    public ILock rate = null;
    public ILock dataReceived = null;
}