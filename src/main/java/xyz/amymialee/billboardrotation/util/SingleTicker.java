package xyz.amymialee.billboardrotation.util;

public interface SingleTicker {
    default void tick() {
        this.billboardrotation$setShouldUpdate(true);
    }

    boolean billboardrotation$shouldUpdate();
    void billboardrotation$setShouldUpdate(boolean shouldUpdate);
}
