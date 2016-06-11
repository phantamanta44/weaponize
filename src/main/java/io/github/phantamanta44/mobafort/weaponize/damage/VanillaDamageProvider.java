package io.github.phantamanta44.mobafort.weaponize.damage;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableMap;
import io.github.phantamanta44.mobafort.weaponize.util.MathUtils;
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
		double amt = dmg.getBaseDmg() / 25D;
		for (Map.Entry<DamageStat, Double> stat : dmg.getDamages())
			amt += getStat(src, stat.getKey()) * stat.getValue();
		if (dmg.getType() == Damage.DamageType.TRUE)
			target.setHealth(MathUtils.clamp(target.getHealth() - amt, 0D, target.getMaxHealth()));
		else {
			EntityDamageByEntityEvent dmgEvent = new EntityDamageByEntityEvent(src, target, EntityDamageEvent.DamageCause.CUSTOM,
					new EnumMap<>(ImmutableMap.of(EntityDamageEvent.DamageModifier.BASE, amt)),
					new EnumMap<>(ImmutableMap.of(EntityDamageEvent.DamageModifier.BASE, Functions.constant(0D))));
			Bukkit.getServer().getPluginManager().callEvent(dmgEvent);
			if (!dmgEvent.isCancelled()) {
				target.damage(dmgEvent.getDamage(), src);
				target.setLastDamageCause(dmgEvent);
			}
		}
	}

	@Override
	public void healEntity(double amt, Player src, LivingEntity target) {
		target.setHealth(MathUtils.clamp(target.getHealth() + (amt / 5D), 0D, target.getMaxHealth()));
	}

	@Override
	public double getStat(Player src, DamageStat stat) {
		switch (stat) {
			case AD:
				return 3D;
			case CRIT_DMG:
				return 2D;
			case HP:
				return src.getHealth();
			case HP_MAX:
				return src.getMaxHealth();
			case HP_MISSING:
				return src.getMaxHealth() - src.getHealth();
			case MANA:
				return src.getLevel();
			default:
				return 0;
		}
	}

}
