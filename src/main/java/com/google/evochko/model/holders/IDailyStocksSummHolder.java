package com.google.evochko.model.holders;

import com.google.evochko.model.DailyStockSummary;
import com.google.evochko.model.Stock;

import java.util.Collection;

public interface IDailyStocksSummHolder {
    boolean addStock(Stock stock);

    Collection<DailyStockSummary> getStockSummaries();
}
