package com.gildedrose;

class NormalUpdater extends ItemUpdater {

    @Override
    void update(Item item, boolean expired) {
        adjustQuality(item, expired ? -2 : -1);
    }
}

