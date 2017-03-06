package io.github.phantamanta44.mobafort.weaponize.stat;

import org.bukkit.entity.Player;

public abstract class AbstractStat<T extends Number> implements IStat<T> {

    protected Player player;
    protected Stats<T> type;

    public AbstractStat(Player player, Stats<T> type) {
        this.player = player;
        this.type = type;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public Stats<T> getStatType() {
        return type;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public void setValue(T val) {
        throw new UnsupportedOperationException();
    }

}
