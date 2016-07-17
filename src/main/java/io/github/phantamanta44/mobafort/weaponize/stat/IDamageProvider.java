package io.github.phantamanta44.mobafort.weaponize.stat;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public interface IDamageProvider {

	void damageEntity(Damage dmg, Player src, LivingEntity target);

	void healEntity(double amt, Player src, LivingEntity target);

}
