package com.ncku_csie_union.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ncku_csie_union.resources.interfaces.IResultAggregator;
import com.ncku_csie_union.resources.interfaces.IResultCollector;
import com.ncku_csie_union.resources.model.TaskRecord;

class ResultAggregator implements IResultAggregator {
    private ArrayList<IResultCollector> collectors = new ArrayList<IResultCollector>();
    private ArrayStatistics<Long> durationStatistics = new ArrayStatistics<Long>();
    private ArrayStatistics<Long> dataReceivedStatistics = new ArrayStatistics<Long>();

    public void RegisterResultCollector(IResultCollector collector) {
        this.collectors.add(collector);
        List<Long> durations = collector.GetRecords().stream()
                .map(record -> record.Duration)
                .collect(Collectors.toList());
        List<Long> dataReceived = collector.GetRecords().stream()
                .map(record -> record.DataReceived)
                .collect(Collectors.toList());
        this.durationStatistics.AddData(durations);
        this.dataReceivedStatistics.AddData(dataReceived);
    }

    public void RegisterResultCollector(ArrayList<IResultCollector> newCollectors) {
        for (IResultCollector collector : newCollectors) {
            RegisterResultCollector(collector);
        }
    }

    public void ShowReport() {
        System.out.println("========= Duration Statistics =========");
        printStatistics(durationStatistics);
        System.out.println("======= Data Received Statistics ======");
        printStatistics(dataReceivedStatistics);
    }

    private void printStatistics(ArrayStatistics<Long> statistics) {
        System.out.printf("Avg: %.2fms, Min: %dms, Med: %.2fms, Max: %dms, P(90): %.2fms, P(95): %.2fms\n",
                statistics.GetAverage(),
                statistics.GetMin(),
                statistics.GetMedian(),
                statistics.GetMax(),
                statistics.GetP90(),
                statistics.GetP95());
    }
}
