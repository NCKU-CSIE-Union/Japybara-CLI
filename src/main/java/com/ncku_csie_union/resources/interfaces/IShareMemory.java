package com.ncku_csie_union.resources.interfaces;
import com.ncku_csie_union.resources.interfaces.core.ILock;

public interface IShareMemory {
    public void appendReport(long duration, long dataReceived);
}