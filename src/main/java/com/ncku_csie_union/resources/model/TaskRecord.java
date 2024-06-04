package com.ncku_csie_union.resources.model;

public class TaskRecord {
    public long Duration;
    public long DataReceived;
    public long Timestamp;

    public TaskRecord(long duration, long dataReceived) {
        this.Duration = duration;
        this.DataReceived = dataReceived;
        this.Timestamp = System.currentTimeMillis();
    }
}