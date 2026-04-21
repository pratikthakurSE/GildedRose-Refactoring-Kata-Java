package com.gildedrose;

import java.util.Map;
import java.util.Set;

class GildedRose {

    @FunctionalInterface
    private interface ItemUpdater {
        void update(Item item, boolean expired);
    }

    private static final int MIN_QUALITY = 0;
    private static final int MAX_QUALITY = 50;

    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";

    private static final Set<String> LEGENDARY = Set.of(SULFURAS);

    private static final ItemUpdater NORMAL =
        (item, expired) -> adjustQuality(item, expired ? -2 : -1);

    private static final Map<String, ItemUpdater> UPDATERS = Map.of(
        "Aged Brie",                                 (item, expired) -> adjustQuality(item, expired ? +2 : +1),
        "Backstage passes to a TAFKAL80ETC concert", GildedRose::updateBackstage,
        "Conjured Mana Cake",                        (item, expired) -> adjustQuality(item, expired ? -4 : -2)
    );

    private final Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            updateItem(item);
        }
    }

    private void updateItem(Item item) {
        if (item.name != null && LEGENDARY.contains(item.name)) return;

        item.sellIn--;
        boolean expired = item.sellIn < 0;

        ItemUpdater updater = item.name != null
            ? UPDATERS.getOrDefault(item.name, NORMAL)
            : NORMAL;

        updater.update(item, expired);
    }

    private static void updateBackstage(Item item, boolean expired) {
        if (expired) {
            item.quality = 0;
            return;
        }
        int boost = 1;
        if (item.sellIn < 10) boost++;
        if (item.sellIn < 5)  boost++;
        adjustQuality(item, boost);
    }

    private static void adjustQuality(Item item, int delta) {
        item.quality = Math.min(MAX_QUALITY, Math.max(MIN_QUALITY, item.quality + delta));
    }
}
