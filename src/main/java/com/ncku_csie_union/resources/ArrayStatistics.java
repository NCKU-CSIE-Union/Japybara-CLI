package com.ncku_csie_union.resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArrayStatistics<T extends Number & Comparable<T>> {
    private List<T> data;
    private boolean dirty;
    private double cache_average;

    public ArrayStatistics() {
        this.data = new ArrayList<>();
        this.dirty = true;
    }

    public ArrayStatistics(List<T> initialData) {
        this.data = new ArrayList<>(initialData);
        this.dirty = true;
        sortData();
    }

    public void AddData(T value) {
        data.add(value);
        this.dirty = true;
    }

    public void AddData(List<T> values) {
        data.addAll(values);
        this.dirty = true;
    }

    private void sortData() {
        if (dirty) {
            Collections.sort(data);
            this.cache_average = GetAverage();
            dirty = false;
        }
    }

    public double GetAverage() {
        if (data.isEmpty()) return 0;
        if (!dirty) return cache_average;
        double sum = 0;
        for (T num : data) {
            sum += num.doubleValue();
        }
        cache_average = sum / ((double)data.size());
        return cache_average;
    }

    public T GetMin() {
        sortData();
        return data.isEmpty() ? null : this.data.getFirst();
    }

    public T GetMax() {
        sortData();
        return data.isEmpty() ? null : this.data.getLast();
    }

    public double GetMedian() {
        if (data.isEmpty()) return 0;
        sortData();
        int middle = data.size() / 2;
        if (data.size() % 2 == 0) {
            return (data.get(middle - 1).doubleValue() + data.get(middle).doubleValue()) / 2.0;
        } else {
            return data.get(middle).doubleValue();
        }
    }

    private double getPercentile(double percentile) {
        if (data.isEmpty() || percentile < 0 || percentile > 100) return 0;
        sortData();
        int index = (int) Math.ceil(percentile / 100.0 * data.size()) - 1;
        return data.get(Math.max(index, 0)).doubleValue();
    }

    public double GetP90() {
        return getPercentile(90);
    }

    public double GetP95() {
        return getPercentile(95);
    }
}
