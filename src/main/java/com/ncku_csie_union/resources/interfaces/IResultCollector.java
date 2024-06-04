package com.ncku_csie_union.resources.interfaces;

import java.util.ArrayList;

import com.ncku_csie_union.resources.model.TaskRecord;

public interface IResultCollector {
    public void AppendReport(TaskRecord record);
    public ArrayList<TaskRecord> GetRecords();
}