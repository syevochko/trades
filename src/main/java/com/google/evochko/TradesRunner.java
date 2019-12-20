package com.google.evochko;

import com.google.evochko.processing.AbstractTradesAdapter;
import com.google.evochko.processing.FilesTradesAdapter;

import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class TradesRunner {
    private static final Logger LOGGER = Logger.getLogger(TradesRunner.class.getSimpleName());

    public static void main(String[] args) throws URISyntaxException {
        try {
            LogManager.getLogManager().readConfiguration(TradesRunner.class.getResourceAsStream("/logging.properties"));

            String inFile = "stocks.txt";
            String outFile = "results.txt";
            if (args == null || args.length != 2) {
                LOGGER.log(Level.INFO, "You can pointed input and output files as first and second arguments in command line.\n"
                        + "Since files are not specified, file stocks.txt from resources will be used as input");
            } else {
                inFile = args[0];
                outFile = args[1];
            }

            AbstractTradesAdapter tradesAdapter = new FilesTradesAdapter(inFile, outFile);
            String rc = tradesAdapter.process();
            LOGGER.log(Level.INFO, "results was written to file: " + rc);
        } catch (Exception e) {
            LOGGER.throwing(TradesRunner.class.getSimpleName(), "main", e);
        }
    }
}
