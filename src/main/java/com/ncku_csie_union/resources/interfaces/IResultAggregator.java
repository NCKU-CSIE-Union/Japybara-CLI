package com.ncku_csie_union.resources.interfaces;

import java.util.ArrayList;

public interface IResultAggregator {
    public void RegisterResultCollector(IResultCollector collector);
    public void RegisterResultCollector(ArrayList<IResultCollector> newCollectors);
    public void ShowReport();
}
