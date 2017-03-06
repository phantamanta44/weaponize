package io.github.phantamanta44.mobafort.weaponize.weapon;

import io.github.phantamanta44.mobafort.lib.item.ItemSig;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Stream;

public class WeaponRegistry {

    private static Collection<IWeapon> registry;

    public static void init() {
        registry = new HashSet<>();
    }

    public static void register(IWeapon weapon) {
        registry.add(weapon);
    }

    public static IWeapon get(ItemStack stack) {
        return registry.stream()
                .filter(e -> e.getType().matches(stack))
                .findAny()
                .orElse(null);
    }

    public static IWeapon get(ItemSig sig) {
        return registry.stream()
                .filter(e -> e.getType().equals(sig))
                .findAny()
                .orElse(null);
    }

    public static Stream<IWeapon> stream() {
        return registry.stream();
    }

}
