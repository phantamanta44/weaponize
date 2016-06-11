package io.github.phantamanta44.mobafort.weaponize.weapon;

import io.github.phantamanta44.mobafort.weaponize.Weaponize;
import io.github.phantamanta44.mobafort.weaponize.util.TitleUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class WeaponTracker {

	private static final Map<UUID, Collection<IWeapon.IWeaponInstance>> playerMap = new HashMap<>();
	private static final Map<IWeapon.IWeaponInstance, ? extends IWeapon.IWeaponData> dataMap = new HashMap<>();

	@SuppressWarnings("unchecked")
	public static void init() {
		Weaponize.INSTANCE.registerTickHandler(tick -> {
			Collection<UUID> toRemove = new ArrayList<>();
			playerMap.forEach((u, l) -> {
				Player p = Bukkit.getServer().getPlayer(u);
				if (p == null) {
					toRemove.add(u);
					return;
				}
				l.forEach(i -> {
					i.tick(tick);
					Arrays.stream(p.getInventory().getContents())
							.filter(s -> s != null && i.getTemplate().getType().matches(s))
							.forEach(s -> {
								s.setAmount(i.getStackSize());
								ItemMeta meta = s.getItemMeta();
								meta.setDisplayName(i.getName());
								meta.setLore(i.getLore());
								s.setItemMeta(meta);
							});
					if (tick % 2 == 0 && i.getTemplate().getType().matches(p.getInventory().getItemInMainHand())) {
						String info = i.getHudInfo();
						if (info != null)
							TitleUtils.sendShortInfo(p, info);
						else
							TitleUtils.sendShortInfo(p, "");
					}
				});
			});
			toRemove.forEach(playerMap::remove);
		});
	}

	public static void put(Player player, IWeapon weapon) {
		get(player.getUniqueId()).add(weapon.instantiate());
	}

	public static void clear(Player player) {
		get(player.getUniqueId()).removeIf(w -> {
			w.kill();
			return true;
		});
	}

	public static Collection<IWeapon.IWeaponInstance> get(UUID id) {
		Collection<IWeapon.IWeaponInstance> val = playerMap.get(id);
		if (val == null) {
			val = new ArrayList<>();
			playerMap.put(id, val);
		}
		return val;
	}

}
