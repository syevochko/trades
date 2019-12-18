package com.google.evochko.model;

import java.math.BigDecimal;

public class Stock {
    private final String symbol;
    private final double price;
    private final BigDecimal volume;

    public Stock(String symbol, double price, BigDecimal volume) {
        this.symbol = symbol;
        this.price = price;
        this.volume = volume;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public BigDecimal getVolume() {
        return volume;
    }
}
