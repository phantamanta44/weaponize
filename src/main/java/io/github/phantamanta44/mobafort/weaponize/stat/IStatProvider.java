package io.github.phantamanta44.mobafort.weaponize.stat;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface IStatProvider {

    <T extends Number> IStat<T> getStat(Player player, Stats<T> stat);

}
