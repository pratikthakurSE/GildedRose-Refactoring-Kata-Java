package com.gildedrose;

class BackstageUpdater extends ItemUpdater {

    @Override
    void update(Item item, boolean expired) {
        if (expired) {
            item.quality = 0;
            return;
        }

        int boost = 1;
        if (item.sellIn < 10) boost++;
        if (item.sellIn < 5) boost++;
        adjustQuality(item, boost);
    }
}

