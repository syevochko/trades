package com.google.evochko;

import com.google.evochko.model.DailyStockSummary;
import com.google.evochko.processing.readers.IStockDailyReader;
import com.google.evochko.processing.readers.SimpleFileStockDailyReader;
import com.google.evochko.processing.writers.SimpleFileStockDailyWriter;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class TradesRunner {
    private static final Logger LOGGER = Logger.getLogger(TradesRunner.class.getSimpleName());

    private Map<String, DailyStockSummary> dailyStockHolder;

    public TradesRunner() {
        dailyStockHolder = new TreeMap<>();
    }

    public static void main(String[] args) throws URISyntaxException {
        try {
            LogManager.getLogManager().readConfiguration(TradesRunner.class.getResourceAsStream("/logging.properties"));

            TradesRunner runner = new TradesRunner();
            URI fileUrl = runner.getClass().getResource("/stocks.txt").toURI();
            runner.startDailyStockSummarize(fileUrl);
            SimpleFileStockDailyWriter stockDailyWriter = new SimpleFileStockDailyWriter("results11.txt", Locale.getDefault());
            LOGGER.log(Level.INFO, "results was written to file: " + stockDailyWriter.writeOutFile(runner.dailyStockHolder.values()));
        } catch (Exception e) {
            LOGGER.throwing(TradesRunner.class.getSimpleName(), "main", e);
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
