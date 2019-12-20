package com.google.evochko.model.holders;

import com.google.evochko.processing.readers.SimpleFileStockDailyReader;
import org.junit.Assert;
import org.junit.Test;

public class SimpleMapDailyStockHolderTest extends Assert {
    @Test
    public void testAll() {
        SimpleFileStockDailyReader reader = new SimpleFileStockDailyReader(null);
        IDailyStocksSummHolder dailyStocksHolder = new SimpleMapDailyStocksHolder();
        dailyStocksHolder.addStock(reader.parse("dddAPPL 165\t  \n126525.236\r"));
        dailyStocksHolder.addStock(reader.parse("DDDappL 165 126525.236"));
        assertEquals(1, dailyStocksHolder.getStockSummaries().size());
        dailyStocksHolder.addStock(reader.parse("APPL 165 126525.236"));
    }
}
