package com.google.evochko.model;

public class Stock {
    private final String symbol;
    private final double price;
    private final double volume;

    public Stock(String symbol, double price, double volume) {
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

    public double getVolume() {
        return volume;
    }
}
