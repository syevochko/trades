package com.google.evochko.processing.writers;

import com.google.evochko.model.DailyStockSummary;

import java.io.IOException;
import java.util.Collection;

public interface IStockDailyWriter<T extends DailyStockSummary> {
    String prepareOutString(T dailyStockSummary);

    String writeOut(Collection<T> dailyStockSummaries) throws IOException;
}
