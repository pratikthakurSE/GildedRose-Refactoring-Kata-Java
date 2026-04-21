package com.gildedrose;

class SulfurasUpdater extends ItemUpdater {

    @Override
    boolean decreasesSellIn() {
        return false;
    }

    @Override
    void update(Item item, boolean expired) {
        // Legendary items never change — quality and sellIn remain frozen
    }
}

