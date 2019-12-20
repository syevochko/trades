package com.google.evochko.model;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class DailyStockSummaryTest extends Assert {
    @Test
    public void testAll() {
        DailyStockSummary dss = new DailyStockSummary("APPL");
        dss.addStock(new Stock("APPL", 10, BigDecimal.valueOf(1000)));
        assertEquals(0, BigDecimal.valueOf(100).compareTo(dss.getTotalStockNumber()));
        dss.addStock(new Stock("APPL1", 25, BigDecimal.valueOf(1500)));
        assertEquals(10, dss.getClosePrice(), 0.0);
        dss.addStock(new Stock("APPL", 18, BigDecimal.valueOf(30000)));
        assertEquals(18, dss.getClosePrice(), 0.0);
        dss.addStock(new Stock("APPL", 9.9, BigDecimal.valueOf(1000)));
        assertEquals(9.9, dss.getMinPrice(), 0.0);
        dss.addStock(new Stock("APPL", 16, BigDecimal.valueOf(2500)));
        assertEquals(18, dss.getMaxPrice(), 0.0);
        assertEquals(0, BigDecimal.valueOf(34500).compareTo(dss.getTotalVolume()));
        assertEquals(0, BigDecimal.valueOf(17.04607).compareTo(dss.getAvgPrice().setScale(5, BigDecimal.ROUND_HALF_EVEN)));
    }
}
