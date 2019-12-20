package com.google.evochko.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DailyStockSummary {
    private static final int SCALING = 8;

    private final String symbol;
    private double openPrice;
    private double closePrice;
    private double minPrice;
    private double maxPrice;
    private BigDecimal totalStockNumber = BigDecimal.ZERO;
    private BigDecimal totalVolume = BigDecimal.ZERO;
    private RoundingMode roundMode = RoundingMode.HALF_EVEN;

    public DailyStockSummary(String symbol) {
        this.symbol = symbol;
    }

    public boolean addStock(Stock stock) {
        if (stock != null) {
            if (symbol.equalsIgnoreCase(stock.getSymbol())) {
                if (totalStockNumber.signum() == 0) {
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
                totalStockNumber = totalStockNumber.add(stock.getVolume().divide(BigDecimal.valueOf(stock.getPrice()), SCALING, roundMode));
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

    public BigDecimal getTotalStockNumber() {
        return totalStockNumber;
    }

    public BigDecimal getTotalVolume() {
        return totalVolume;
    }

    public BigDecimal getAvgPrice() {
        return totalStockNumber.signum() == 0 ? BigDecimal.ZERO :
                totalVolume.divide(totalStockNumber, SCALING, roundMode);
    }

    @Override
    public String toString() {
        return symbol + ": " + openPrice + "; " + closePrice + "; "
                + minPrice + "; " + maxPrice + "; " + totalStockNumber + "; " + totalVolume.toString();

    }
}
