package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemUpdaterBehaviorTest {

    @Test
    void resolve_returnsNonNullUpdaterForKnownItems() {
        assertNotNull(ItemUpdaterFactory.resolve("Aged Brie"));
        assertNotNull(ItemUpdaterFactory.resolve("Backstage passes to a TAFKAL80ETC concert"));
        assertNotNull(ItemUpdaterFactory.resolve("Conjured Mana Cake"));
        assertNotNull(ItemUpdaterFactory.resolve("Sulfuras, Hand of Ragnaros"));
    }

    @Test
    void resolve_returnsNormalUpdaterForUnknownItem() {
        assertDayUpdate("Unknown Item", 5, 10, 4, 9);
    }

    @Test
    void resolve_returnsNormalUpdaterForNull() {
        assertDayUpdate(null, 5, 10, 4, 9);
    }

    @Test
    void normal_decreasesQualityByOneBeforeSellDate() {
        assertDayUpdate("Normal Item", 5, 20, 4, 19);
    }

    @Test
    void normal_decreasesQualityByTwoAfterSellDate() {
        assertDayUpdate("Normal Item", 0, 20, -1, 18);
    }

    @Test
    void normal_qualityFloorIsZero() {
        assertDayUpdate("Normal Item", 5, 0, 4, 0);
    }

    @Test
    void normal_qualityFloorIsZeroAfterExpiry() {
        assertDayUpdate("Normal Item", 0, 1, -1, 0);
    }

    // Aged Brie

    @Test
    void agedBrie_increasesQualityByOneBeforeSellDate() {
        assertDayUpdate("Aged Brie", 5, 10, 4, 11);
    }

    @Test
    void agedBrie_increasesQualityByTwoAfterSellDate() {
        assertDayUpdate("Aged Brie", 0, 10, -1, 12);
    }

    @Test
    void agedBrie_qualityCapIs50() {
        assertDayUpdate("Aged Brie", 5, 50, 4, 50);
    }

    @Test
    void agedBrie_qualityCapIs50AfterExpiry() {
        assertDayUpdate("Aged Brie", 0, 49, -1, 50);
    }

    // Backstage passes
    @Test
    void backstage_dropsToZeroAfterConcert() {
        assertDayUpdate("Backstage passes to a TAFKAL80ETC concert", 0, 20, -1, 0);
    }

    @Test
    void backstage_increasesByOneWhenMoreThanTenDays() {
        assertDayUpdate("Backstage passes to a TAFKAL80ETC concert", 11, 20, 10, 21);
    }

    @Test
    void backstage_increasesByTwoWhenTenDaysOrLess() {
        assertDayUpdate("Backstage passes to a TAFKAL80ETC concert", 9, 20, 8, 22);
    }

    @Test
    void backstage_increasesByThreeWhenFiveDaysOrLess() {
        assertDayUpdate("Backstage passes to a TAFKAL80ETC concert", 4, 20, 3, 23);
    }

    @Test
    void backstage_qualityCapIs50() {
        assertDayUpdate("Backstage passes to a TAFKAL80ETC concert", 4, 49, 3, 50);
    }

    @Test
    void backstage_boundaryAtTenDays_increasesByTwo() {
        // sellIn 10 → 9, falls into "10 days or less" zone
        assertDayUpdate("Backstage passes to a TAFKAL80ETC concert", 10, 20, 9, 22);
    }

    @Test
    void backstage_boundaryAtSixDays_staysAtTwo() {
        // sellIn 6 → 5, still in "10 days or less" zone, NOT in "5 days or less"
        assertDayUpdate("Backstage passes to a TAFKAL80ETC concert", 6, 20, 5, 22);
    }

    @Test
    void backstage_boundaryAtFiveDays_increasesByThree() {
        // sellIn 5 → 4, enters "5 days or less" zone
        assertDayUpdate("Backstage passes to a TAFKAL80ETC concert", 5, 20, 4, 23);
    }

    //Conjured
    @Test
    void conjured_decreasesByTwoBeforeSellDate() {
        assertDayUpdate("Conjured Mana Cake", 5, 10, 4, 8);
    }

    @Test
    void conjured_decreasesByFourAfterSellDate() {
        assertDayUpdate("Conjured Mana Cake", 0, 10, -1, 6);
    }

    @Test
    void conjured_qualityFloorIsZero() {
        assertDayUpdate("Conjured Mana Cake", 5, 1, 4, 0);
    }

    @Test
    void conjured_qualityFloorIsZeroAfterExpiry() {
        assertDayUpdate("Conjured Mana Cake", 0, 3, -1, 0);
    }

    //Sulfuras (legendary)

    @Test
    void sulfuras_neverChangesQualityOrSellIn() {
        assertDayUpdate("Sulfuras, Hand of Ragnaros", 0, 80, 0, 80);
    }

    @Test
    void sulfuras_neverChangesEvenWhenAlreadyExpired() {
        assertDayUpdate("Sulfuras, Hand of Ragnaros", -1, 80, -1, 80);
    }

    //Helper
    private void assertDayUpdate(String name, int sellIn, int initialQuality, int expectedSellIn, int expectedQuality) {
        Item item = new Item(name, sellIn, initialQuality);
        ItemUpdaterFactory.resolve(name).updateForDay(item);
        assertEquals(expectedSellIn, item.sellIn,
            () -> "Expected sellIn " + expectedSellIn + " for [" + name + "]");
        assertEquals(expectedQuality, item.quality,
            () -> "Expected quality " + expectedQuality + " for [" + name + "]");
    }
}

