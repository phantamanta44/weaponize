package io.github.phantamanta44.mobafort.weaponize.stat;

import org.bukkit.entity.Player;

public interface IStat<T extends Number> {

	T getValue();

	Player getPlayer();

	Stats<T> getStatType();

	boolean isMutable();

	void setValue(T val);

}
