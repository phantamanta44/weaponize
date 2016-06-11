package io.github.phantamanta44.mobafort.weaponize.util;

import com.comphenix.packetwrapper.WrapperPlayServerChat;
import com.comphenix.packetwrapper.WrapperPlayServerTitle;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.entity.Player;

public class TitleUtils {

	public static void sendTitle(Player player, String text) {
		packetTime(20, 32, 20).sendPacket(player);
		packetTitle(text).sendPacket(player);
	}

	public static void sendSubtitle(Player player, String text) {
		packetTime(20, 32, 20).sendPacket(player);
		packetSubtitle(text).sendPacket(player);
		packetTitle("").sendPacket(player);
	}

	public static void sendShortInfo(Player player, String text) {
		WrapperPlayServerChat pkt = new WrapperPlayServerChat();
		pkt.setMessage(WrappedChatComponent.fromText(text));
		pkt.setPosition((byte)2);
		pkt.sendPacket(player);
	}

	private static WrapperPlayServerTitle packetTitle(String text) {
		WrapperPlayServerTitle pkt = new WrapperPlayServerTitle();
		pkt.setAction(EnumWrappers.TitleAction.TITLE);
		pkt.setTitle(WrappedChatComponent.fromText(text));
		return pkt;
	}

	private static WrapperPlayServerTitle packetSubtitle(String text) {
		WrapperPlayServerTitle pkt = new WrapperPlayServerTitle();
		pkt.setAction(EnumWrappers.TitleAction.SUBTITLE);
		pkt.setTitle(WrappedChatComponent.fromText(text));
		return pkt;
	}

	private static WrapperPlayServerTitle packetTime(int in, int dur, int out) {
		WrapperPlayServerTitle pkt = new WrapperPlayServerTitle();
		pkt.setAction(EnumWrappers.TitleAction.TIMES);
		pkt.setFadeIn(in);
		pkt.setFadeOut(dur);
		pkt.setStay(out);
		return pkt;
	}

}
