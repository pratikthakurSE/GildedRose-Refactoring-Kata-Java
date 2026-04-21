package com.gildedrose;

class GildedRose {

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
        ItemUpdater updater = ItemUpdaterFactory.resolve(item.name);
        updater.updateForDay(item);
    }
}
