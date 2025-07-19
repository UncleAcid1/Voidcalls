package net.unkleacid.voidcalls.block.template;

import net.modificationstation.stationapi.api.util.StringIdentifiable;

public enum SlabType implements StringIdentifiable {
    TOP("top"),
    BOTTOM("bottom"),
    DOUBLE("double");

    final String slabType;

    SlabType(String slabType) {
        this.slabType = slabType;
    }

    @Override
    public String asString() {
        return this.slabType;
    }
}
