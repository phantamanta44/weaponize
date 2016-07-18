package io.github.phantamanta44.mobafort.weaponize.stat;

import org.bukkit.entity.Player;

import java.util.function.Consumer;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
class VanillaStatProvider implements IStatProvider {

	@Override
	public <T extends Number> IStat<T> getStat(Player player, Stats<T> stat) {
		switch (stat.enumType) {
			case AD:
				return new ImmutableStatImpl(player, Stats.AD, 60);
			case BONUS_AD:
				return new ImmutableStatImpl(player, Stats.BONUS_AD, 0);
			case AS:
				return new ImmutableStatImpl(player, Stats.AS, 0.66D);
			case CRIT_CHANCE:
				return new ImmutableStatImpl(player, Stats.CRIT_CHANCE, 0F);
			case CRIT_DMG:
				return new ImmutableStatImpl(player, Stats.CRIT_DMG, 0F);
			case LIFE_STEAL:
				return new ImmutableStatImpl(player, Stats.LIFE_STEAL, 0F);
			case ARM_PEN:
				return new ImmutableStatImpl(player, Stats.ARM_PEN, 0);
			case ARM:
				return new ImmutableStatImpl(player, Stats.ARM, 0);
			case HP:
				return new MutableStatImpl(player, Stats.HP, () -> (int)(player.getHealth() * 30D), n -> player.setHealth(n.doubleValue() / 30D));
			case HP_MAX:
				return new ImmutableStatImpl(player, Stats.HP_MAX, (int)(player.getMaxHealth() * 30D));
			case HP_MISSING:
				return new ImmutableStatImpl(player, Stats.HP_MISSING, getStat(player, Stats.HP_MAX).getValue() - getStat(player, Stats.HP).getValue());
			case HP_REGEN:
				return new ImmutableStatImpl(player, Stats.HP_REGEN, 0D);
			case MR:
				return new ImmutableStatImpl(player, Stats.MR, 0);
			case MOVE_SPEED:
				return new ImmutableStatImpl(player, Stats.MOVE_SPEED, 0);
			case AP:
				return new ImmutableStatImpl(player, Stats.AP, 15);
			case MAG_PEN:
				return new ImmutableStatImpl(player, Stats.MAG_PEN, 0);
			case MANA:
				return new MutableStatImpl(player, Stats.MANA, player::getLevel, v -> player.setLevel(v.intValue()));
			case MANA_MAX:
				return new ImmutableStatImpl(player, Stats.MANA_MAX, 500);
			case MANA_REGEN:
				return new ImmutableStatImpl(player, Stats.MANA_REGEN, 0);
			case SPELL_VAMP:
				return new ImmutableStatImpl(player, Stats.SPELL_VAMP, 0F);
		}
		throw new IllegalArgumentException();
	}

	public static class ImmutableStatImpl extends AbstractStat {

		private Number value;

		public ImmutableStatImpl(Player player, Stats<?> type, Number value) {
			super(player, type);
			this.value = value;
		}

		@Override
		public Number getValue() {
			return value;
		}

	}

	public static class MutableStatImpl extends AbstractStat {

		private final Supplier<Number> getter;
		private final Consumer<Number> setter;

		public MutableStatImpl(Player player, Stats<?> type, Supplier<Number> getter, Consumer<Number> setter) {
			super(player, type);
			this.getter = getter;
			this.setter = setter;
		}

		@Override
		public boolean isMutable() {
			return true;
		}

		@Override
		public Number getValue() {
			return getter.get();
		}

		@Override
		public void setValue(Number val) {
			setter.accept(val);
		}

	}

}
