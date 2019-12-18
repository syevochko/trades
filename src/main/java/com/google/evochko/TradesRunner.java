package com.google.evochko;

import com.google.evochko.model.DailyStockSummary;
import com.google.evochko.processing.readers.IStockDailyReader;
import com.google.evochko.processing.readers.SimpleFileStockDailyReader;
import com.google.evochko.processing.writers.SimpleFileStockDailyWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class TradesRunner {
    private static final Logger LOGGER = LogManager.getLogger(TradesRunner.class);

    private Map<String, DailyStockSummary> dailyStockHolder;

    public TradesRunner() {
        dailyStockHolder = new TreeMap<>();
    }

    public static void main(String[] args) throws URISyntaxException {
        try {
            TradesRunner runner = new TradesRunner();
            URI fileUrl = runner.getClass().getResource("/stocks.txt").toURI();
            runner.startDailyStockSummarize(fileUrl);
            SimpleFileStockDailyWriter stockDailyWriter = new SimpleFileStockDailyWriter("results11.txt", Locale.getDefault());
            stockDailyWriter.writeOut(runner.dailyStockHolder.values());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void printMap() {
        for (Map.Entry<String, DailyStockSummary> e : dailyStockHolder.entrySet()) {
            System.out.println(e.getKey() + " -> " + e.getValue().toString());
        }
    }

    public void startDailyStockSummarize(URI fileName) {
        IStockDailyReader fileProcessor = new SimpleFileStockDailyReader(fileName, dailyStockHolder);
        fileProcessor.readData();
    }
}
