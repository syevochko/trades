package com.google.evochko.processing;

import com.google.evochko.model.holders.SimpleMapDailyStocksHolder;
import com.google.evochko.processing.readers.IStockDailyReader;
import com.google.evochko.processing.readers.SimpleFileStockDailyReader;
import com.google.evochko.processing.writers.IStockDailyWriter;
import com.google.evochko.processing.writers.SimpleFileStockDailyWriter;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FilesTradesAdapter extends AbstractTradesAdapter {
    private static final Logger LOGGER = Logger.getLogger(SimpleFileStockDailyReader.class.getName());

    private final String inFile;
    private final String outFile;

    public FilesTradesAdapter(String inFile, String outFile) {
        super(new SimpleMapDailyStocksHolder());
        this.inFile = inFile;
        this.outFile = outFile;
    }

    @Override
    protected IStockDailyReader createReader() throws URISyntaxException {
        Path filePath;
        File f = new File(inFile);
        if (f.exists()) {
            filePath = Paths.get(f.toURI());
        } else {
            LOGGER.log(Level.WARNING, "Can't find file by path: " + f.getAbsolutePath());
            LOGGER.log(Level.WARNING, "Try to load stocks.txt from resources...");
            filePath = Paths.get(getClass().getResource("/stocks.txt").toURI());
        }
        return new SimpleFileStockDailyReader(filePath);
    }

    @Override
    protected IStockDailyWriter createWriter() {
        return new SimpleFileStockDailyWriter(outFile, Locale.getDefault());
    }
}
