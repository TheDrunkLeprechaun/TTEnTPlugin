package com.github.TheDrunkLeprechaun.TacticalTestificateEntrenchment;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

	public TTEnTMain() {
		onEnable();
	}

	@Override
	public void onEnable() {
		worlds = getServer().getWorlds();

		getServer().getPluginManager().registerEvents(new TTEnTListener(), this);

		getCommand("privateGame").setExecutor(new TTEnTCommandExcecutor());
		getCommand("publicGame").setExecutor(new TTEnTCommandExcecutor());
		getCommand("joinGame").setExecutor(new TTEnTCommandExcecutor());

		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
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
		}
		, 10L);
	}
	public static void main(String[] args) {
		new TTEnTMain();
	}
}
