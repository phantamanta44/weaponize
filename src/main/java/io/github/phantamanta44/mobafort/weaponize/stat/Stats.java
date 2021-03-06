package io.github.phantamanta44.mobafort.weaponize.stat;

import org.bukkit.entity.Player;

public class Stats<T extends Number> {

    public static final Stats<Integer> AD = new Stats<>(StatType.AD);
    public static final Stats<Integer> BONUS_AD = new Stats<>(StatType.BONUS_AD);
    public static final Stats<Double> AS = new Stats<>(StatType.AS);
    public static final Stats<Float> CRIT_CHANCE = new Stats<>(StatType.CRIT_CHANCE);
    public static final Stats<Float> CRIT_DMG = new Stats<>(StatType.CRIT_DMG);
    public static final Stats<Float> LIFE_STEAL = new Stats<>(StatType.LIFE_STEAL);
    public static final Stats<Integer> ARM_PEN = new Stats<>(StatType.ARM_PEN);

    public static final Stats<Integer> ARM = new Stats<>(StatType.ARM);
    public static final Stats<Integer> HP = new Stats<>(StatType.HP);
    public static final Stats<Integer> HP_MAX = new Stats<>(StatType.HP_MAX);
    public static final Stats<Integer> HP_MISSING = new Stats<>(StatType.HP_MISSING);
    public static final Stats<Double> HP_REGEN = new Stats<>(StatType.HP_REGEN);
    public static final Stats<Integer> MR = new Stats<>(StatType.MR);
    public static final Stats<Integer> MOVE_SPEED = new Stats<>(StatType.MOVE_SPEED);

    public static final Stats<Integer> AP = new Stats<>(StatType.AP);
    public static final Stats<Integer> MAG_PEN = new Stats<>(StatType.MAG_PEN);
    public static final Stats<Integer> MANA = new Stats<>(StatType.MANA);
    public static final Stats<Integer> MANA_MAX = new Stats<>(StatType.MANA_MAX);
    public static final Stats<Double> MANA_REGEN = new Stats<>(StatType.MANA_REGEN);
    public static final Stats<Float> SPELL_VAMP = new Stats<>(StatType.SPELL_VAMP);
    public static final Stats<Float> CDR = new Stats<>(StatType.CDR);

    public final StatType enumType;

    private Stats(StatType enumVal) {
        this.enumType = enumVal;
    }

    private static IStatProvider provider;

    public static void bindProvider(IStatProvider newProv) {
        provider = newProv;
    }

    public static IStatProvider getProvider() {
        if (provider == null)
            bindDefaultProvider();
        return provider;
    }

    public static <T extends Number> IStat<T> getStat(Player player, Stats<T> type) {
        return getProvider().getStat(player, type);
    }

    public static boolean expendMana(Player player, int amt) {
        IStat<Integer> manaStat = getStat(player, MANA);
        if (manaStat != null && manaStat.isMutable()) {
            int mana = manaStat.getValue();
            if (mana >= amt) {
                manaStat.setValue(mana - amt);
                return true;
            }
        }
        return false;
    }

    private static void bindDefaultProvider() {
        bindProvider(new VanillaStatProvider());
    }

    public enum StatType {

        AD(Integer.class), BONUS_AD(Integer.class), AS(Double.class), CRIT_CHANCE(Float.class), CRIT_DMG(Float.class), LIFE_STEAL(Float.class),
        ARM_PEN(Integer.class), ARM(Integer.class), HP(Integer.class), HP_MAX(Integer.class), HP_MISSING(Integer.class), HP_REGEN(Double.class),
        MR(Integer.class), MOVE_SPEED(Integer.class), AP(Integer.class), MAG_PEN(Integer.class), MANA(Integer.class), MANA_MAX(Integer.class),
        MANA_REGEN(Double.class), SPELL_VAMP(Float.class), CDR(Float.class);

        public final Class<? extends Number> type;

        StatType(Class<? extends Number> type) {
            this.type = type;
        }

    }

}
