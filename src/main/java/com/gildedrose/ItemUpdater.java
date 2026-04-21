package com.gildedrose;

abstract class ItemUpdater {

    private static final int MIN_QUALITY = 0;
    private static final int MAX_QUALITY = 50;

    /**
     * Update item based on expiry status.
     * Subclasses implement the specific logic.
     */
    abstract void update(Item item, boolean expired);

    /**
     * Template method for one full daily update.
     * Handles sellIn lifecycle first, then delegates quality update.
     */
    final void updateForDay(Item item) {
        boolean expired = decrementSellInAndCheckExpired(item);
        update(item, expired);
    }

    /**
     * By default, items decrease sellIn each day.
     * Legendary updaters can override and return false.
     */
    boolean decreasesSellIn() {
        return true;
    }

    private boolean decrementSellInAndCheckExpired(Item item) {
        if (!decreasesSellIn()) {
            return false;
        }
        item.sellIn--;
        return item.sellIn < 0;
    }

    /**
     * Adjust item quality by delta, clamped to [0, 50].
     * Protected for use by all subclasses.
     */
    protected final void adjustQuality(Item item, int delta) {
        item.quality = Math.min(MAX_QUALITY, Math.max(MIN_QUALITY, item.quality + delta));
    }
}

