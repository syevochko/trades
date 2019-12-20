package com.google.evochko.processing.writers;

import com.google.evochko.model.DailyStockSummary;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collection;
import java.util.Locale;

public class SimpleFileStockDailyWriter implements IStockDailyWriter<DailyStockSummary> {
    private final String fileName;
    private final Locale locale;
    private final DecimalFormat decimalFormat;

    public SimpleFileStockDailyWriter(String fileName, Locale locale) {
        this.fileName = fileName;
        this.locale = locale;
        decimalFormat = locale == null ?
                new DecimalFormat("#.####") :
                new DecimalFormat("#.####", new DecimalFormatSymbols(locale));
    }

    @Override
    public String prepareOutString(DailyStockSummary s) {
        return String.format(locale, getOutputFormat(),
                s.getSymbol(),
                decimalFormat.format(s.getOpenPrice()),
                decimalFormat.format(s.getClosePrice()),
                decimalFormat.format(s.getMinPrice()),
                decimalFormat.format(s.getMaxPrice()),
                decimalFormat.format(s.getAvgPrice()),
                decimalFormat.format(s.getTotalVolume())
        );
    }

    @Override
    public String writeOutFile(Collection<DailyStockSummary> dailyStockSummaries) throws IOException {
        if (!dailyStockSummaries.isEmpty()) {
            Path path = Paths.get(fileName);
            try (BufferedWriter writer = Files.newBufferedWriter(path)) {
                writer.write(
                        String.format(locale, getOutputFormat(), "Stock", "Open", "Close", "Min", "Max", "Average", "Vol")
                );
                for (DailyStockSummary dss : dailyStockSummaries) {
                    writer.write(prepareOutString(dss));
                }
            }
            return path.toString();
        }
        return null;
    }

    protected String getOutputFormat() {
        return "%4s\t%-10s\t%-10s\t%-10s\t%-10s\t%-10s\t%-10s\r\n";
    }
}
