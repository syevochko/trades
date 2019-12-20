package com.google.evochko.model.holders;

import com.google.evochko.model.DailyStockSummary;
import com.google.evochko.model.Stock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class SimpleMapDailyStocksHolder implements IDailyStocksSummHolder {
    private final Map<String, DailyStockSummary> dailyStockHolder = new TreeMap<>();

    @Override
    public boolean addStock(Stock stock) {
        if (stock != null) {
            DailyStockSummary dailyStock = dailyStockHolder.computeIfAbsent(stock.getSymbol(), DailyStockSummary::new);
            dailyStock.addStock(stock);
        }
        return false;
    }

    @Override
    public Collection<DailyStockSummary> getStockSummaries() {
        return new ArrayList<>(dailyStockHolder.values());
    }
}
