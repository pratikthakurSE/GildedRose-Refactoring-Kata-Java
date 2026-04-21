package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @Test
    void normalItem_decreasesBeforeSellDate() {
        Item item = new Item("Normal Item", 10, 20);
        update(item);

        assertEquals(19, item.quality);
        assertEquals(9, item.sellIn);
    }

    @Test
    void normalItem_decreasesTwiceAfterSellDate() {
        Item item = new Item("Normal Item", 0, 20);
        update(item);

        assertEquals(18, item.quality);
        assertEquals(-1, item.sellIn);
    }

    @Test
    void normalItem_decreasesTwiceWhenAlreadyExpired() {
        Item item = new Item("Normal Item", -1, 20);
        update(item);

        assertEquals(18, item.quality);
        assertEquals(-2, item.sellIn);
    }

    @Test
    void agedBrie_increasesByOneBeforeSellDate() {
        Item item = new Item("Aged Brie", 5, 10);
        update(item);

        assertEquals(11, item.quality);
        assertEquals(4, item.sellIn);
    }

    @Test
    void agedBrie_increasesTwiceAfterSellDate() {
        Item item = new Item("Aged Brie", 0, 10);
        update(item);

        assertEquals(12, item.quality);
        assertEquals(-1, item.sellIn);
    }

    @Test
    void agedBrie_increasesByTwoWhenAlreadyExpired() {
        Item item = new Item("Aged Brie", -1, 10);
        update(item);

        assertEquals(12, item.quality);
        assertEquals(-2, item.sellIn);
    }

    @Test
    void agedBrie_neverExceedsQuality50() {
        Item item = new Item("Aged Brie", 0, 49);
        update(item);

        assertEquals(50, item.quality);
        assertEquals(-1, item.sellIn);
    }

    @Test
    void backstage_dropsToZeroAfterConcert() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20);
        update(item);

        assertEquals(0, item.quality);
    }

    @Test
    void backstage_increasesByTwoWhenTenDaysOrLess() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20);
        update(item);

        assertEquals(22, item.quality);
    }

    @Test
    void backstage_increasesByThreeWhenFiveDaysOrLess() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20);
        update(item);

        assertEquals(23, item.quality);
        assertEquals(4, item.sellIn);
    }

    @Test
    void backstage_neverExceedsQuality50() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49);
        update(item);

        assertEquals(50, item.quality);
        assertEquals(4, item.sellIn);
    }

    @Test
    void sulfuras_neverChanges() {
        Item item = new Item("Sulfuras, Hand of Ragnaros", 0, 80);
        update(item);

        assertEquals(80, item.quality);
        assertEquals(0, item.sellIn);
    }

    @Test
    void normalItem_qualityNeverNegative() {
        Item item = new Item("Normal Item", 5, 0);
        update(item);

        assertEquals(0, item.quality);
    }

    @Test
    void normalItem_withNullName_isHandledAsNormalItem() {
        Item item = new Item(null, 5, 10);
        update(item);

        assertEquals(9, item.quality);
        assertEquals(4, item.sellIn);
    }

    @Test
    void conjured_decreasesByTwoBeforeSellDate() {
        Item item = new Item("Conjured Mana Cake", 5, 10);
        update(item);

        assertEquals(8, item.quality);
        assertEquals(4, item.sellIn);
    }

    @Test
    void conjured_decreasesByFourAfterSellDate() {
        Item item = new Item("Conjured Mana Cake", 0, 10);
        update(item);

        assertEquals(6, item.quality);
        assertEquals(-1, item.sellIn);
    }

    @Test
    void conjured_qualityNeverNegative() {
        Item item = new Item("Conjured Mana Cake", 5, 1);
        update(item);

        assertEquals(0, item.quality);
    }

    private void update(Item item) {
        GildedRose app = new GildedRose(new Item[]{item});
        app.updateQuality();
    }
}
