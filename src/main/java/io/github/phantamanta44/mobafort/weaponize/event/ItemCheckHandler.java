package io.github.phantamanta44.mobafort.weaponize.event;

import io.github.phantamanta44.mobafort.weaponize.Weaponize;
import io.github.phantamanta44.mobafort.weaponize.weapon.IWeapon;
import io.github.phantamanta44.mobafort.weaponize.weapon.WeaponRegistry;
import io.github.phantamanta44.mobafort.weaponize.weapon.WeaponTracker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class ItemCheckHandler implements Listener {

    //@EventHandler
    public void onItemPickup(PlayerPickupItemEvent event) {
        new CheckTask(event.getPlayer().getInventory(), event.getPlayer().getUniqueId()).runTaskLater(Weaponize.INSTANCE, 1L);
    }

    //@EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        new CheckTask(event.getClickedInventory(), event.getWhoClicked().getUniqueId()).runTaskLater(Weaponize.INSTANCE, 1L);
    }

    public static class CheckTask extends BukkitRunnable {

        private final Inventory inv;
        private final UUID id;

        public CheckTask(Inventory inv, UUID id) {
            this.inv = inv;
            this.id = id;
        }

        @Override
        public void run() {
            if (inv != null) {
                inv.iterator().forEachRemaining(i -> {
                    if (i != null && id != null) {
                        IWeapon weapon = WeaponRegistry.get(i);
                        if (weapon != null) {
                            Player p = Bukkit.getServer().getPlayer(id);
                            if (p != null
                                    && WeaponTracker.get(p.getUniqueId()).stream().noneMatch(inst -> inst.getTemplate() == weapon))
                                WeaponTracker.put(p, weapon);
                        }
                    }
                });
            }
        }

    }

}
