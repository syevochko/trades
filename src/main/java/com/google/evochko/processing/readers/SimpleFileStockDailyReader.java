package com.google.evochko.processing.readers;

import com.google.evochko.model.Stock;
import com.google.evochko.model.holders.IDailyStocksSummHolder;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Stream;

public class SimpleFileStockDailyReader implements IStockDailyReader<String> {
    private static final int DATA_PER_ROW = 3;
    private static final Logger LOGGER = Logger.getLogger(SimpleFileStockDailyReader.class.getName());

    private final Path filePath;

    public SimpleFileStockDailyReader(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public void readData(IDailyStocksSummHolder dailyStockHolder) {
        try (Stream<String> stream = Files.lines(filePath)) {
            stream.forEach(s -> dailyStockHolder.addStock(parse(s)));
        } catch (Exception e) {
            LOGGER.logp(Level.SEVERE, this.getClass().getSimpleName(), "readData", "Error with file: " + filePath, e);
        }
    }

    @Override
    public Stock parse(String obj) {
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
                LOGGER.logp(Level.SEVERE, this.getClass().getSimpleName(), "parse", "Stock wasn't created: " + obj, e);
            }
        }
        return null;
    }

    private String normalize(String obj) {
        return obj.replaceAll("[^\\.\\s\\w]+", "");
    }
}
