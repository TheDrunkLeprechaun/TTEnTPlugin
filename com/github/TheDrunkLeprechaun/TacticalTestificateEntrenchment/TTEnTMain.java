package com.github.TheDrunkLeprechaun.TacticalTestificateEntrenchment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import org.bukkit.craftbukkit.v1_4_5.CraftWorld;
import net.minecraft.server.v1_4_5.ChunkProviderGenerate;
import net.minecraft.server.v1_4_5.StructureBoundingBox;
import net.minecraft.server.v1_4_5.StructureStart;

public final class TTEnTMain extends org.bukkit.plugin.java.JavaPlugin {

	public static List<World> worlds;

	public static HashMap<String, TTEnTGame> privateGames = new HashMap<String, TTEnTGame>();

	public static HashMap<String, TTEnTGame> publicGames = new HashMap<String, TTEnTGame>();

	public static List<TTEnTGame> activeGames = new ArrayList<TTEnTGame>();

	public volatile boolean logic = true;
	
	public static String createGameCmd, startGameCmd, endGameCmd, invitePlayerCmd, kickPlayerCmd;

	@Override
	public void onEnable() {
		Properties props = new Properties();

		try {
			props.load(new FileInputStream(new File("TTEnT.properties")));
			props.getProperty("CREATE_GAME_CMD");
			props.getProperty("START_GAME_CMD");
			props.getProperty("END_GAME_CMD");
			props.getProperty("INVITE_PLAYER_CMD");
			props.getProperty("KICK_PLAYER_CMD");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		worlds = getServer().getWorlds();
		
		getServer().getPluginManager().registerEvents(new TTEnTListener(), this);

		getCommand("privateGame").setExecutor(new TTEnTCommandExcecutor());
		getCommand("publicGame").setExecutor(new TTEnTCommandExcecutor());
		getCommand("joinGame").setExecutor(new TTEnTCommandExcecutor());
		getCommand("acceptInvite").setExecutor(new TTEnTCommandExcecutor());
		
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				while(logic) {
					logic();
				}
			}
		});
	}
	@Override
	public void onDisable() {
		//Insert logic to be performed when the plugin is disabled
		getServer().getPluginManager().disablePlugin(this);
		logic = false;
	}
	public void logic() {
		// TODO Add logic that needs to be run constantly
		
	}
	public void generateVillage(final Location location, final List<IngamePlayer> players) {
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public void run() {
				try { 
					int radius = 50;
					Block block = location.getBlock();

					CraftWorld craftworld = (CraftWorld) worlds.get(0);
					ChunkProviderGenerate chunkProvider = (ChunkProviderGenerate)craftworld.getHandle().worldProvider.getChunkProvider();

					Field randField = ChunkProviderGenerate.class.getDeclaredField("k");
					randField.setAccessible(true);
					Random random = (Random)randField.get(chunkProvider);

					Class clazz = Class.forName("net.minecraft.server.v1_4_R1.WorldGenVillageStart");
					Constructor constructor = clazz.getConstructor(new Class[] { World.class, Random.class, Integer.TYPE, Integer.TYPE, Integer.TYPE });
					constructor.setAccessible(true);
					StructureStart start = (StructureStart)constructor.newInstance(new Object[] { craftworld.getHandle(), random, Integer.valueOf(block.getChunk().getX()), Integer.valueOf(block.getChunk().getZ()), Integer.valueOf(0) });

					int i = (block.getChunk().getX() << 4) + 8;
					int j = (block.getChunk().getZ() << 4) + 8;

					start.a(craftworld.getHandle(), random, new StructureBoundingBox(i - radius, j - radius, i + radius, j + radius));

				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 10L);
	}
}
