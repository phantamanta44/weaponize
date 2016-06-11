package io.github.phantamanta44.mobafort.weaponize.util;

import org.apache.commons.lang.mutable.MutableInt;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;

public class StackDecrementTask extends BukkitRunnable {

	private Map<UUID, MutableInt> parMap;
	private UUID key;
	private MutableInt val;

	public StackDecrementTask(Map<UUID, MutableInt> map, UUID id, MutableInt cnt) {
		this.parMap = map;
		this.key = id;
		this.val = cnt;
	}

	@Override
	public void run() {
		val.decrement();
		if (val.intValue() <= 0)
			parMap.remove(key);
	}

}
