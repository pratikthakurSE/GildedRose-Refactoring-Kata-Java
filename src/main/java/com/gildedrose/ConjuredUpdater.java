package com.gildedrose;

class ConjuredUpdater extends ItemUpdater {

    @Override
    void update(Item item, boolean expired) {
        adjustQuality(item, expired ? -4 : -2);
    }
}

