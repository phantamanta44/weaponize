package io.github.phantamanta44.mobafort.weaponize.weapon;

import io.github.phantamanta44.mobafort.weaponize.util.ItemSig;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public interface IWeapon {

	ItemSig getType();

	IWeaponInstance instantiate();

	interface IWeaponInstance {

		String getName();

		List<String> getLore();

		int getStackSize();

		String getHudInfo();

		IWeapon getTemplate();

		void onInteract(PlayerInteractEvent event);

		void tick(long tick);

		void kill();

	}

}
