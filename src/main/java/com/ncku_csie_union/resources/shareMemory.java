package com.ncku_csie_union.resources;

import java.util.concurrent.locks.ReentrantLock;

import com.ncku_csie_union.resources.interfaces.IShareMemory;
import com.ncku_csie_union.resources.interfaces.core.ILock;

public class shareMemory implements IShareMemory {
    private ReportAggregator reportAggregator;

    public shareMemory() {
        this.reportAggregator = new ReportAggregator();
    }

    public synchronized void appendReport(long duration, long dataReceived) {
        reportAggregator.incrementIteration();
        reportAggregator.addDuration(duration);
        reportAggregator.addDataReceived(dataReceived);
    }
}

class Iteration implements ILock {
    private int count;
    private final ReentrantLock lock = new ReentrantLock();

    public Iteration() {
        this.count = 0;
    }

    public void increment() {
        Lock();
        try {
            count++;
        } finally {
            Unlock();
        }
    }

    public int getCount() {
        Lock();
        try {
            return count;
        } finally {
            Unlock();
        }
    }


    @Override
    public void Lock() {
        lock.lock();
    }

    @Override
    public void Unlock() {
        lock.unlock();
    }
}

class Duration implements ILock {
    private long totalDuration;
    private final ReentrantLock lock = new ReentrantLock();

    public Duration() {
        this.totalDuration = 0;
    }

    public void addDuration(long duration) {
        Lock();
        try {
            totalDuration += duration;
        } finally {
            Unlock();
        }
    }

    public long getTotalDuration() {
        Lock();
        try {
            return totalDuration;
        } finally {
            Unlock();
        }
    }

    @Override
    public void Lock() {
        lock.lock();
    }

    @Override
    public void Unlock() {
        lock.unlock();
    }
}

class DataReceived implements ILock {
    private long dataReceived;
    private final ReentrantLock lock = new ReentrantLock();

    public DataReceived() {
        this.dataReceived = 0;
    }

    public void addData(long data) {
        Lock();
        try {
            dataReceived += data;
        } finally {
            Unlock();
        }
    }

    public long getDataReceived() {
        Lock();
        try {
            return dataReceived;
        } finally {
            Unlock();
        }
    }

    @Override
    public void Lock() {
        lock.lock();
    }

    @Override
    public void Unlock() {
        lock.unlock();
    }
}

class ReportAggregator {
    private final Iteration iteration;
    private final Duration duration;
    private final DataReceived dataReceived;

    public ReportAggregator() {
        this.iteration = new Iteration();
        this.duration = new Duration();
        this.dataReceived = new DataReceived();
    }

    public void incrementIteration() {
        iteration.increment();
    }

    public void addDuration(long duration) {
        this.duration.addDuration(duration);
    }

    public void addDataReceived(long data) {
        dataReceived.addData(data);
    }

    public int getIterationCount() {
        return iteration.getCount();
    }

    public long getTotalDuration() {
        return duration.getTotalDuration();
    }

    public long getDataReceived() {
        return dataReceived.getDataReceived();
    }
}
