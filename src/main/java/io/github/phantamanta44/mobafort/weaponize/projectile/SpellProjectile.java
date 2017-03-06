package io.github.phantamanta44.mobafort.weaponize.projectile;

import io.github.phantamanta44.mobafort.weaponize.Weaponize;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.UUID;

public abstract class SpellProjectile {

    private final UUID source;
    private World world;
    private Vector pos, vel;
    private double radius;
    private CollisionCriteria col;
    private long spawnTime;
    private boolean dead = false;

    public SpellProjectile(Location pos, Vector vel, double radius, UUID source) {
        this(pos, vel, radius, source, CollisionCriteria.ALL);
    }

    public SpellProjectile(Location pos, Vector vel, double radius, UUID source, CollisionCriteria col) {
        this.world = pos.getWorld();
        this.pos = pos.toVector();
        this.vel = vel;
        this.radius = radius;
        this.source = source;
        this.col = col;
    }

    public UUID getSource() {
        return source;
    }

    public void setVelocity(Vector vel) {
        this.vel = vel;
    }

    public void setSpeed(double speed) {
        vel.normalize().multiply(speed);
    }

    public Vector getVelocity() {
        return vel;
    }

    public void setPosition(Vector pos) {
        this.pos = pos;
    }

    public Vector getPosition() {
        return pos;
    }

    public World getWorld() {
        return world;
    }

    public Location getLocation() {
        return new Location(world, pos.getX(), pos.getY(), pos.getZ());
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public CollisionCriteria getCollisionCriteria() {
        return col;
    }

    public long getTimeAlive() {
        return Weaponize.INSTANCE.getTick() - spawnTime;
    }

    public void kill() {
        this.dead = true;
    }

    public boolean isDead() {
        return dead;
    }

    public void dispatch() {
        ProjectileTracker.dispatch(this);
        spawnTime = Weaponize.INSTANCE.getTick();
    }

    public abstract void onHit(CollisionCriteria col, List<Entity> ents);

    public abstract void tick(long tick);

    public enum CollisionCriteria {

        NONE(0b00),
        BLOCK(0b01),
        ENTITY(0b10),
        ALL(0b11);

        private final int bitmask;

        private CollisionCriteria(int mask) {
            this.bitmask = mask;
        }

        public boolean collidesWithBlock() {
            return (bitmask & BLOCK.bitmask) != 0;
        }

        public boolean collidesWithEntity() {
            return (bitmask & ENTITY.bitmask) != 0;
        }

    }

}
