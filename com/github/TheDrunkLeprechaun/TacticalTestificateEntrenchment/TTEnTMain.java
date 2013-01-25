package com.github.TheDrunkLeprechaun.TacticalTestificateEntrenchment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.World;

public final class TTEnTMain extends org.bukkit.plugin.java.JavaPlugin {

	public static List<World> worlds;

	public static HashMap<String, TTEnTGame> privateGames = new HashMap<String, TTEnTGame>();

	public static HashMap<String, TTEnTGame> publicGames = new HashMap<String, TTEnTGame>();
	
	public static List<TTEnTGame> activeGames = new ArrayList<TTEnTGame>();

	public TTEnTMain() {
		onEnable();
	}

	@Override
	@SuppressWarnings({ "deprecation" })
	public void onEnable() {
		worlds = getServer().getWorlds();

		getServer().getPluginManager().registerEvents(new TTEnTListener(), this);
		
		getCommand("privateGame").setExecutor(new TTEnTCommandExcecutor());
		getCommand("publicGame").setExecutor(new TTEnTCommandExcecutor());
		getCommand("joinGame").setExecutor(new TTEnTCommandExcecutor());

		getServer().getScheduler().scheduleAsyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				logic();
			}
		}, 5L);
	}
	@Override
	public void onDisable() {
		// TODO Insert logic to be performed when the plugin is disabled
	}
	public void logic() {
		// TODO Add logic that needs to be run constantly
//		for(TTEnTGame game: activeGames) {
//			
//		}
	}
	public static void main(String[] args) {
		new TTEnTMain();
	}
}
