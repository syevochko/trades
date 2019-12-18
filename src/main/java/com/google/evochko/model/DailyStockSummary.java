package com.google.evochko.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DailyStockSummary {
    private final String symbol;
    private double openPrice;
    private double closePrice;
    private double minPrice;
    private double maxPrice;
    private long total = 0;
    private BigDecimal totalVolume = BigDecimal.ZERO;
    private RoundingMode roundMode = RoundingMode.HALF_UP;

    public DailyStockSummary(String symbol) {
        this.symbol = symbol;
    }

    public DailyStockSummary withRoundingMode(RoundingMode mode) {
        roundMode = mode;
        return this;
    }

    // TODO need synchronization
    public boolean addStock(Stock stock) {
        if (stock != null) {
            if (symbol.equalsIgnoreCase(stock.getSymbol())) {
                if (total == 0) {
                    openPrice = stock.getPrice();
                    minPrice = stock.getPrice();
                    maxPrice = stock.getPrice();
                } else if (minPrice > stock.getPrice()) {
                    minPrice = stock.getPrice();
                } else if (maxPrice < stock.getPrice()) {
                    maxPrice = stock.getPrice();
                }
                closePrice = stock.getPrice();
                totalVolume = totalVolume.add(stock.getVolume());
                total++;
                return true;
            }
        }
        return false;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public long getTotal() {
        return total;
    }

    public BigDecimal getTotalVolume() {
        return totalVolume;
    }

    public double getAvgPrice() {
        return total == 0 ? BigDecimal.ZERO.doubleValue() :
                totalVolume.divide(BigDecimal.valueOf(total), roundMode).doubleValue();
    }

    @Override
    public String toString() {
        return symbol + ": " + openPrice + "; " + closePrice + "; "
                + minPrice + "; " + maxPrice + "; " + total + "; " + totalVolume.toString();

    }
}
