package io.github.phantamanta44.mobafort.weaponize.event;

import io.github.phantamanta44.mobafort.weaponize.weapon.IWeapon;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EventSpellCast extends Event {

    private static final HandlerList hl = new HandlerList();

    public static HandlerList getHandlerList() {
        return hl;
    }

    private Player player;
    private IWeapon.IWeaponInstance spell;
    private boolean cancelled;

    public EventSpellCast(Player player, IWeapon.IWeaponInstance spell) {
        this.player = player;
        this.spell = spell;
        this.cancelled = false;
    }

    public Player getPlayer() {
        return player;
    }

    public IWeapon.IWeaponInstance getSpell() {
        return spell;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return hl;
    }

}
