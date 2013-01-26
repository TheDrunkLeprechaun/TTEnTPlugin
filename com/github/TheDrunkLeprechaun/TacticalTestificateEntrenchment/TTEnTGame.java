package com.github.TheDrunkLeprechaun.TacticalTestificateEntrenchment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class TTEnTGame {

	public static int numGames = 0;
	
	public Location spawn, deathSpawn;

	public List<IngamePlayer> players, deadPlayers;

	public HashMap<String, Location> originalLocations = new HashMap<String, Location>();

	public HashMap<String, PlayerInventory> originalInventories = new HashMap<String, PlayerInventory>();

	public TTEnTGame() {
		spawn = new Location(TTEnTMain.worlds.get(0), numGames * 500, 5, numGames * 500);
		deathSpawn = new Location(TTEnTMain.worlds.get(1), numGames * 500, 5, numGames * 500);
		numGames++;
		players = new ArrayList<IngamePlayer>();
	}
	public void start() {
		for(IngamePlayer player: players) {
			//Get the player represented
			Player p = player.getRep();
			if(!p.isOnline()) {
				Bukkit.getServer().broadcastMessage("Player " + p.getName() + " is not online, and is being excluded from the game.");
				players.remove(player);
			}
			else {
				//Save the player's original location
				originalLocations.put(player.getRep().getName(), p.getLocation());
				//Set the players inventory to the starting inventory
				PlayerInventory pInv = p.getInventory();
				originalInventories.put(player.getRep().getName(), pInv);
				pInv.clear();
				pInv.setArmorContents(new ItemStack[]{new ItemStack(Material.IRON_HELMET), new ItemStack(Material.IRON_CHESTPLATE),
						new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)});
				pInv.addItem(new ItemStack(Material.IRON_SWORD, 1));
				pInv.addItem(new ItemStack(Material.BOW, 1));
				pInv.addItem(new ItemStack(Material.ARROW, 24));
				pInv.addItem(new ItemStack(Material.BREAD, 16));
				pInv.addItem(new ItemStack(Material.GRILLED_PORK, 8));
				pInv.addItem(new ItemStack(Material.ENDER_PEARL, 4));

				p.setGameMode(GameMode.ADVENTURE);
				p.teleport(spawn);
			}
		}
		//Spawn village and villagers
		new TTEnTMain().generateVillage(spawn, players);
	}
	public void end() {
		for(IngamePlayer player: players) {
			//Get the player represented
			Player p = player.getRep();
			p.setGameMode(Bukkit.getDefaultGameMode());
			p.teleport(originalLocations.get(player.getRep().getPlayer().getName()));
			//Re-load player inventory
		}
	}
}
