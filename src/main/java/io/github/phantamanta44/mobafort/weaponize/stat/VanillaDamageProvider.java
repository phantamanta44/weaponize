package io.github.phantamanta44.mobafort.weaponize.stat;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableMap;
import io.github.phantamanta44.mobafort.lib.math.MathUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.EnumMap;
import java.util.Map;

class VanillaDamageProvider implements IDamageProvider {

	@Override
	public void damageEntity(Damage dmg, Player src, LivingEntity target) {
		double amt = dmg.getBaseDmg();
		for (Map.Entry<Stats, Double> stat : dmg.getDamages())
			amt += Stats.getStat(src, stat.getKey()).getValue().doubleValue() * stat.getValue();
		if (dmg.getType() == Damage.DamageType.TRUE)
			target.setHealth(MathUtils.clamp(target.getHealth() - amt / 30D, 0D, target.getMaxHealth()));
		else {
			EntityDamageByEntityEvent dmgEvent = new EntityDamageByEntityEvent(src, target, EntityDamageEvent.DamageCause.CUSTOM,
					new EnumMap<>(ImmutableMap.of(EntityDamageEvent.DamageModifier.BASE, amt / 30D)),
					new EnumMap<>(ImmutableMap.of(EntityDamageEvent.DamageModifier.BASE, Functions.constant(0D))));
			Bukkit.getServer().getPluginManager().callEvent(dmgEvent);
			if (!dmgEvent.isCancelled()) {
				target.damage(dmgEvent.getDamage(), src);
				target.setLastDamageCause(dmgEvent);
			}
		}
	}

	@Override
	public void damageEntity(Damage dmg, IStatted src, LivingEntity target) {
		double amt = dmg.getBaseDmg();
		for (Map.Entry<Stats, Double> stat : dmg.getDamages())
			amt += src.getStat(stat.getKey()).doubleValue() * stat.getValue();
		if (dmg.getType() == Damage.DamageType.TRUE)
			target.setHealth(MathUtils.clamp(target.getHealth() - amt / 30D, 0D, target.getMaxHealth()));
		else {
			EntityDamageEvent dmgEvent = new EntityDamageEvent(target, EntityDamageEvent.DamageCause.CUSTOM,
					new EnumMap<>(ImmutableMap.of(EntityDamageEvent.DamageModifier.BASE, amt / 30D)),
					new EnumMap<>(ImmutableMap.of(EntityDamageEvent.DamageModifier.BASE, Functions.constant(0D))));
			Bukkit.getServer().getPluginManager().callEvent(dmgEvent);
			if (!dmgEvent.isCancelled()) {
				target.damage(dmgEvent.getDamage());
				target.setLastDamageCause(dmgEvent);
			}
		}
	}

	@Override
	public void healEntity(double amt, Player src, LivingEntity target) {
		target.setHealth(MathUtils.clamp(target.getHealth() + (amt / 30D), 0D, target.getMaxHealth()));
	}

}
