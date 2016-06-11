package io.github.phantamanta44.mobafort.weaponize.util;

import org.bukkit.ChatColor;

public class StringUtils {

	public static String genResourceBar(int amt, int max, int length) {
		String bar = "|";
		for (int i = 0; i < length; i++)
			bar += "|";
		int ind = (int)Math.floor(((float)amt / (float)max) * (float)bar.length());
		bar = bar.substring(0, ind) + ChatColor.DARK_GRAY + bar.substring(ind, bar.length());
		return String.format("%s[%s%s%s]", ChatColor.GRAY, ChatColor.BLUE, bar, ChatColor.GRAY);
	}

	public static String genTimeBar(int amt, int max) {
		return String.format("%.1fs %s", (float)amt / 20F, StringUtils.genResourceBar(max - amt, max, 60));
	}

}
