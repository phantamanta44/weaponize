package io.github.phantamanta44.mobafort.weaponize.stat;

public interface IStatted {

    <T extends Number> T getStat(Stats<T> stat);

}
