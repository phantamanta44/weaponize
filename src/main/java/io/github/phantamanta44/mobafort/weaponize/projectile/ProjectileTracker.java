package io.github.phantamanta44.mobafort.weaponize.projectile;

import io.github.phantamanta44.mobafort.lib.math.RayTrace;
import io.github.phantamanta44.mobafort.weaponize.Weaponize;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectileTracker {

	private static final List<SpellProjectile> projs = new LinkedList<>();

	public static void init() {
		Weaponize.INSTANCE.registerTickHandler(tick -> {
			projs.removeIf(p -> {
				p.tick(tick);
				checkCollision(p);
				return p.isDead();
			});
		});
	}

	private static void checkCollision(SpellProjectile p) {
		Vector newPos = p.getPosition().clone().add(p.getVelocity());
		double dist = newPos.distance(p.getPosition());
		Iterator<Location> iter = new RayTrace(p.getLocation(), dist, (int)Math.ceil(dist / p.getRadius())).iterator();
		boolean block = p.getCollisionCriteria().collidesWithBlock(), entity = p.getCollisionCriteria().collidesWithEntity();
		while (iter.hasNext()) {
			Location next = iter.next();
			if (block) {
				if (p.getWorld().getBlockAt(next).getType().isSolid()) {
					p.setPosition(next.toVector());
					p.onHit(SpellProjectile.CollisionCriteria.BLOCK, null);
					return;
				}
			}
			if (entity) {
				double r = p.getRadius(), rSquared = Math.pow(r, 2);
				List<Entity> entities = p.getWorld().getNearbyEntities(next, r, r, r).stream()
						.filter(e -> !e.getUniqueId().equals(p.getSource()))
						.collect(Collectors.toList());
				if (entities.size() > 0) {
					p.setPosition(next.toVector());
					p.onHit(SpellProjectile.CollisionCriteria.ENTITY, entities);
					return;
				}
			}
		}
		p.setPosition(newPos);
	}

	public static void dispatch(SpellProjectile proj) {
		projs.add(proj);
	}

}
