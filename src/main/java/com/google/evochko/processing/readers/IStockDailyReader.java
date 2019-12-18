package com.google.evochko.processing.readers;

import com.google.evochko.model.Stock;

public interface IStockDailyReader<T> {

    void readData();

    Stock readStock(T obj);

}
