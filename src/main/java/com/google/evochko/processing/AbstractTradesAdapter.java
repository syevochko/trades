package com.google.evochko.processing;

import com.google.evochko.model.holders.IDailyStocksSummHolder;
import com.google.evochko.processing.readers.IStockDailyReader;
import com.google.evochko.processing.writers.IStockDailyWriter;

import java.util.logging.Logger;

public abstract class AbstractTradesAdapter {
    private static final Logger LOGGER = Logger.getLogger(AbstractTradesAdapter.class.getSimpleName());

    protected IDailyStocksSummHolder dailyStockHolder;
    protected IStockDailyReader reader;
    protected IStockDailyWriter writer;

    public AbstractTradesAdapter(IDailyStocksSummHolder dailyStockHolder) {
        this.dailyStockHolder = dailyStockHolder;
    }

    public String process() throws Exception {
        reader = createReader();
        writer = createWriter();
        reader.readData(dailyStockHolder);
        return writer.writeOut(dailyStockHolder.getStockSummaries());
    }

    public IDailyStocksSummHolder geDailyStockHolder() {
        return dailyStockHolder;
    }

    protected abstract IStockDailyReader createReader() throws Exception;

    protected abstract IStockDailyWriter createWriter();
}
