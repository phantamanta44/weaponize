package io.github.phantamanta44.mobafort.weaponize.util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.Iterator;

public class RayTrace implements Iterable<Location> {

	private World world;
	private Vector origin, diff;
	private int parts;

	public RayTrace(Location loc, double len, int segs) {
		world = loc.getWorld();
		origin = loc.toVector();
		diff = loc.getDirection().multiply(len / (double)segs);
		parts = segs;
	}

	public RayTrace(LivingEntity ent, double len, int segs) {
		world = ent.getWorld();
		origin = ent.getEyeLocation().toVector();
		diff = ent.getLocation().getDirection().multiply(len / (double)segs);
		parts = segs;
	}

	@Override
	public Iterator<Location> iterator() {
		return new RayTraceIterator();
	}

	private class RayTraceIterator implements Iterator<Location> {

		private int i = 0;

		@Override
		public boolean hasNext() {
			return i < parts;
		}

		@Override
		public Location next() {
			Vector pos = origin.clone().add(diff.clone().multiply(i++));
			return new Location(world, pos.getX(), pos.getY(), pos.getZ());
		}

	}

}
