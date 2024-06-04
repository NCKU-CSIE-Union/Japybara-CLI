package com.ncku_csie_union.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ncku_csie_union.resources.interfaces.IResultAggregator;
import com.ncku_csie_union.resources.interfaces.IResultCollector;
import com.ncku_csie_union.resources.model.TaskRecord;

class ResultAggregator extends Base implements IResultAggregator {
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
        final String CYAN = "\033[0;36m";
        final String GRAY = "\033[0;37m";
        final String RESET = "\033[0m";
        final String BOLD = "\033[1m";
        final String PADDING = "  ";
        System.out.flush();
        System.out.println("\n");
        System.out.println(PADDING + BOLD + "data_received" + RESET + GRAY + "........:" + RESET + " " + BOLD + CYAN + dataReceivedHumanReadable(dataReceivedStatistics.GetTotal()) + RESET + " " + CYAN + dataReceivedHumanReadable(dataReceivedStatistics.GetTotal() / this.config.GetDuration() * 1000) + "/s" + RESET);
        System.out.println(PADDING + BOLD + "duration" + RESET + GRAY + ".............:" + RESET + " " +
        "avg=" + CYAN + durationHumanReadable(durationStatistics.GetAverage()) + RESET + " " +
        "min=" + CYAN + durationHumanReadable(durationStatistics.GetMin()) + RESET + " " +
        "med=" + CYAN + durationHumanReadable(durationStatistics.GetMedian()) + RESET + " " +
        "max=" + CYAN + durationHumanReadable(durationStatistics.GetMax()) + RESET + " " +
        "p(90)=" + CYAN + durationHumanReadable(durationStatistics.GetP90()) + RESET + " " +
        "p(95)=" + CYAN + durationHumanReadable(durationStatistics.GetP95()) + RESET);
    }

    private String dataReceivedHumanReadable(double dataReceived) {
        if (dataReceived < 1024) {
            return String.format("%d B", dataReceived);
        } else if (dataReceived < 1024 * 1024) {
            return String.format("%.2f KB", dataReceived / 1024.0);
        } else if (dataReceived < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", dataReceived / 1024.0 / 1024.0);
        } else {
            return String.format("%.2f GB", dataReceived / 1024.0 / 1024.0 / 1024.0);
        }
    }

    private String durationHumanReadable(double duration) {
        // ns , µs, ms, s
        if (duration < 1000) {
            return String.format("%dns", duration);
        } else if (duration < 1000 * 1000) {
            return String.format("%.2fµs", duration / 1000.0);
        } else if (duration < 1000 * 1000 * 1000) {
            return String.format("%.2fms", duration / 1000.0 / 1000.0);
        } else {
            return String.format("%.2fs", duration / 1000.0 / 1000.0 / 1000.0);
        }
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
