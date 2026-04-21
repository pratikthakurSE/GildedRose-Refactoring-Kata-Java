package com.gildedrose;

import java.util.Map;

final class ItemUpdaterFactory {

    private static final String AGED_BRIE = "Aged Brie";
    private static final String BACKSTAGE = "Backstage passes to a TAFKAL80ETC concert";
    private static final String CONJURED = "Conjured Mana Cake";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";

    private static final ItemUpdater NORMAL_UPDATER = new NormalUpdater();

    private static final Map<String, ItemUpdater> UPDATERS = Map.of(
        AGED_BRIE, new AgedBrieUpdater(),
        BACKSTAGE, new BackstageUpdater(),
        CONJURED, new ConjuredUpdater(),
        SULFURAS, new SulfurasUpdater()
    );

    private ItemUpdaterFactory() {
    }

    /**
     * Resolve and return the appropriate ItemUpdater for the given item name.
     * Falls back to NORMAL_UPDATER for unknown items or null names.
     */
    static ItemUpdater resolve(String itemName) {
        return itemName == null
            ? NORMAL_UPDATER
            : UPDATERS.getOrDefault(itemName, NORMAL_UPDATER);
    }
}

