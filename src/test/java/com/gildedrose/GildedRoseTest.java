package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration tests for GildedRose orchestration.
 * Verifies that updateQuality() iterates items and delegates correctly.
 * Per-item behavior rules are covered exhaustively in ItemUpdaterBehaviorTest.
 */
class GildedRoseTest {

    @Test
    void updateQuality_iteratesAllItemsInArray() {
        Item normal = new Item("Normal Item", 5, 10);
        Item brie = new Item("Aged Brie", 5, 10);
        Item sulfuras = new Item("Sulfuras, Hand of Ragnaros", 5, 80);

        new GildedRose(new Item[]{normal, brie, sulfuras}).updateQuality();

        assertEquals(9, normal.quality);       // decreased
        assertEquals(11, brie.quality);        // increased
        assertEquals(80, sulfuras.quality);    // unchanged
        assertEquals(5, sulfuras.sellIn);      // sellIn frozen for legendary
    }

    @Test
    void updateQuality_handlesEmptyArray() {
        new GildedRose(new Item[]{}).updateQuality();
        // No exception = pass
    }

    @Test
    void updateQuality_canBeCalledMultipleDaysInSequence() {
        Item item = new Item("Normal Item", 2, 10);
        GildedRose app = new GildedRose(new Item[]{item});

        app.updateQuality();  // day 1
        app.updateQuality();  // day 2
        app.updateQuality();  // day 3 (expired, -2)

        assertEquals(-1, item.sellIn);
        assertEquals(6, item.quality);  // 10 - 1 - 1 - 2
    }
}

