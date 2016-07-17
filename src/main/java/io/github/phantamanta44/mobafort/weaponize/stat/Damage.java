package io.github.phantamanta44.mobafort.weaponize.stat;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class Damage {

	private static IDamageProvider provider;

	public static void bindProvider(IDamageProvider newProv) {
		provider = newProv;
	}

	public static void heal(double dmg, Player src, LivingEntity target) {
		provider.healEntity(dmg, src, target);
	}

	public static IDamageProvider getProvider() {
		if (provider == null)
			bindDefaultProvider();
		return provider;
	}

	private static void bindDefaultProvider() {
		bindProvider(new VanillaDamageProvider());
	}

	private double baseDmg;
	private Collection<Map.Entry<Stats, Double>> damages;
	private DamageType type;

	public Damage(double dmg, DamageType type) {
		this.baseDmg = dmg;
		this.type = type;
		this.damages = new ArrayList<>();
	}

	public Damage withDmg(Stats stat, double multiplier) {
		damages.add(new AbstractMap.SimpleImmutableEntry<>(stat, multiplier));
		return this;
	}

	public void deal(Player src, LivingEntity target) {
		getProvider().damageEntity(this, src, target);
	}

	public double getBaseDmg() {
		return baseDmg;
	}

	public Collection<Map.Entry<Stats, Double>> getDamages() {
		return damages;
	}

	public DamageType getType() {
		return type;
	}

	public enum DamageType {

		PHYSICAL, MAGIC, TRUE

	}

}
