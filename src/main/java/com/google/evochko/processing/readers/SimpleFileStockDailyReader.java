package com.google.evochko.processing.readers;

import com.google.evochko.model.DailyStockSummary;
import com.google.evochko.model.Stock;
import com.google.evochko.processing.readers.strategies.ReadErrorHandleStrategy;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Stream;

public class SimpleFileStockDailyReader implements IStockDailyReader<String> {
    private static final int DATA_PER_ROW = 3;
    private static final Logger LOGGER = Logger.getLogger(SimpleFileStockDailyReader.class.getName());

    private final URI fileName;
    private final Map<String, DailyStockSummary> dailyStockHolder;
    private ReadErrorHandleStrategy errorHandleStrategy;

    public SimpleFileStockDailyReader(URI fileName, Map<String, DailyStockSummary> dailyStockHolder) {
        this.fileName = fileName;
        this.dailyStockHolder = dailyStockHolder;
    }

    @Override
    public void readData() {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(s -> {
                Stock stock = readStock(s);
                DailyStockSummary dailyStock = dailyStockHolder.computeIfAbsent(stock.getSymbol(), DailyStockSummary::new);
                dailyStock.addStock(stock);
            });
        } catch (Exception e) {
            LOGGER.logp(Level.SEVERE, this.getClass().getSimpleName(), "readData", "Error with file: " + fileName, e);
        }
    }

    @Override
    public Stock readStock(String obj) {
        if (obj != null && obj.length() > 0) {
            try {
                String s = normalize(obj);
                String[] splitted = s.trim().split("\\s+", DATA_PER_ROW);
                if (splitted.length > DATA_PER_ROW) {
                    LOGGER.log(Level.WARNING, "Object has more data than specified: " + obj);
                } else if (splitted.length < DATA_PER_ROW) {
                    LOGGER.log(Level.WARNING, "Stock wasn't created - object has less data than specified: " + obj);
                    return null;
                }

                double price = Double.parseDouble(splitted[1]);
                BigDecimal volume = BigDecimal.valueOf(Double.parseDouble(splitted[2]));
                return new Stock(splitted[0].toUpperCase(), price, volume);

            } catch (PatternSyntaxException | NumberFormatException | NullPointerException e) {
                LOGGER.logp(Level.SEVERE, this.getClass().getSimpleName(), "readStock", "Stock wasn't created: " + obj, e);
            }
        }
        return null;
    }

    private String normalize(String obj) {
        return obj.replaceAll("[^\\.\\s\\w]+", "");
    }
}
