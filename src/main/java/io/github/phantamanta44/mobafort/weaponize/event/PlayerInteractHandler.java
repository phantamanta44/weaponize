package io.github.phantamanta44.mobafort.weaponize.event;

import io.github.phantamanta44.mobafort.weaponize.weapon.WeaponTracker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractHandler implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (event.getItem() != null) {
			WeaponTracker.get(event.getPlayer().getUniqueId()).stream()
					.filter(i -> i.getTemplate().getType().matches(event.getItem()))
					.forEach(i -> i.onInteract(event));
		}
	}

}
