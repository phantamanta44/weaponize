package io.github.phantamanta44.mobafort.weaponize;

import io.github.phantamanta44.mobafort.weaponize.event.ItemCheckHandler;
import io.github.phantamanta44.mobafort.weaponize.event.PlayerInteractHandler;
import io.github.phantamanta44.mobafort.weaponize.projectile.ProjectileTracker;
import io.github.phantamanta44.mobafort.weaponize.weapon.WeaponRegistry;
import io.github.phantamanta44.mobafort.weaponize.weapon.WeaponTracker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.LinkedList;
import java.util.function.LongConsumer;

public class Weaponize extends JavaPlugin {

	public static Weaponize INSTANCE;

	private BukkitRunnable tickTask;
	private Collection<LongConsumer> tickHandlers = new LinkedList<>();
	private long tick;

	@Override
	public void onEnable() {
		INSTANCE = this;
		WeaponRegistry.init();
		WeaponTracker.init();
		ProjectileTracker.init();
		tickTask = new TickTask();
		tickTask.runTaskTimer(this, 0L, 1L);
		Bukkit.getServer().getPluginManager().registerEvents(new ItemCheckHandler(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerInteractHandler(), this);
	}

	@Override
	public void onDisable() {
		tickTask.cancel();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("wpn")) {
			if (!sender.hasPermission("weaponize.admin")) {
				sender.sendMessage(ChatColor.RED + "You cannot use this command.");
				return true;
			}
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "Only a player can use this command.");
				return true;
			}
			Player p = (Player)sender;
			WeaponTracker.clear(p);
			new ItemCheckHandler.CheckTask(p.getInventory(), p.getUniqueId()).run();
			sender.sendMessage("Updated instances.");
		}
		return false;
	}

	public void registerTickHandler(LongConsumer handler) {
		tickHandlers.add(handler);
	}

	public long getTick() {
		return tick;
	}

	private class TickTask extends BukkitRunnable {

		@Override
		public void run() {
			tickHandlers.forEach(h -> h.accept(tick));
			tick++;
		}

	}

}
