package com.google.evochko.processing.readers;

import com.google.evochko.model.Stock;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class SimpleFileStockDailyReaderTest extends Assert {
    @Test
    public void testAll() {
        IStockDailyReader reader = new SimpleFileStockDailyReader(null);
        Stock s = reader.parse("''*APPL--=(\t165\t  126525\r");
        assertEquals("APPL", s.getSymbol());
        assertEquals(165, s.getPrice(), 0.0);
        assertEquals(0, BigDecimal.valueOf(126525).setScale(2, BigDecimal.ROUND_HALF_EVEN).compareTo(s.getVolume()));

        s = reader.parse("dddAPPL 165\t  \n126525.236\r");
        assertEquals("DDDAPPL", s.getSymbol());
        assertEquals(165, s.getPrice(), 0.0);
        assertEquals(0, BigDecimal.valueOf(126525.236).setScale(3, BigDecimal.ROUND_HALF_EVEN).compareTo(s.getVolume()));
    }
}
