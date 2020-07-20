package com.google.evochko.model.holders;

import com.google.evochko.processing.readers.IStockDailyReader;
import com.google.evochko.processing.readers.SimpleFileStockDailyReader;
import com.google.evochko.processing.writers.IStockDailyWriter;
import com.google.evochko.processing.writers.SimpleFileStockDailyWriter;
import org.junit.Assert;
import org.junit.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    @Test
    public void testMultiReading() throws URISyntaxException {
        Path p = Paths.get(this.getClass().getResource("/stocks.txt").toURI());

        IDailyStocksSummHolder dailyStocksHolder = new SimpleMapDailyStocksHolder();

//        ExecutorService executor = Executors.newFixedThreadPool(5);
        
        IStockDailyReader reader = new SimpleFileStockDailyReader(p);
        reader.readData(dailyStocksHolder);


        IStockDailyWriter writer = new SimpleFileStockDailyWriter(null, null);
        dailyStocksHolder.getStockSummaries().stream().forEach(s -> System.out.println(writer.prepareOutString(s)));

    }
}
