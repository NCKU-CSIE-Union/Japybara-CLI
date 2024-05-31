package com.ncku_csie_union.resources;

import java.lang.reflect.Array;
import java.util.ArrayList;

import com.ncku_csie_union.resources.interfaces.IResultCollector;
import com.ncku_csie_union.resources.model.TaskRecord;


public class ResultCollector implements IResultCollector {
    private ArrayList<TaskRecord> recordList = new ArrayList<TaskRecord>();

    public void AppendReport(long duration, long dataReceived) {
        TaskRecord record = new TaskRecord(duration, dataReceived);
        recordList.add(record);
    }

    public ArrayList<TaskRecord> GetRecords() {
        return recordList;
    }
}

