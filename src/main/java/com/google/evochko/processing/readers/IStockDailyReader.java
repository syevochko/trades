package com.google.evochko.processing.readers;

import com.google.evochko.model.Stock;
import com.google.evochko.model.holders.IDailyStocksSummHolder;

public interface IStockDailyReader<T> {

    void readData(IDailyStocksSummHolder dailyStockHolder);

    Stock parse(T obj);

}
