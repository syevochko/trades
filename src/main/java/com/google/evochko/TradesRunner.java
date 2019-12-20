package com.google.evochko;

import com.google.evochko.processing.AbstractTradesAdapter;
import com.google.evochko.processing.FilesTradesAdapter;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class TradesRunner {
    private static final Logger LOGGER = Logger.getLogger(TradesRunner.class.getSimpleName());

    public static void main(String[] args) throws URISyntaxException {
        try {
            LogManager.getLogManager().readConfiguration(TradesRunner.class.getResourceAsStream("/logging.properties"));

            AbstractTradesAdapter tradesAdapter = new FilesTradesAdapter("stocks.txt", "results11.txt");
            String rc = tradesAdapter.process();
            LOGGER.log(Level.INFO, "results was written to file: " + rc);
        } catch (Exception e) {
            LOGGER.throwing(TradesRunner.class.getSimpleName(), "main", e);
        }
    }
}
