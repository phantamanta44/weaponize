package io.github.phantamanta44.mobafort.weaponize.weapon;

import io.github.phantamanta44.mobafort.lib.item.ItemSig;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class WeaponRegistry {

	private static Map<ItemSig, IWeapon> weaponMap;

	public static void init() {
		weaponMap = new HashMap<>();
	}

	public static void register(IWeapon weapon) {
		weaponMap.put(weapon.getType(), weapon);
	}

	public static IWeapon get(ItemStack stack) {
		return weaponMap.entrySet().stream()
				.filter(e -> e.getKey().matches(stack))
				.map(Map.Entry::getValue)
				.findAny()
				.orElse(null);
	}

	private static IWeapon get(ItemSig sig) {
		return weaponMap.entrySet().stream()
				.filter(e -> e.getKey().equals(sig))
				.map(Map.Entry::getValue)
				.findAny()
				.orElse(null);
	}

}
