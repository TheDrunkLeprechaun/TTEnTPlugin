package com.github.TheDrunkLeprechaun.TacticalTestificateEntrenchment;

import org.bukkit.Material;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import org.bukkit.inventory.ItemStack;

public class TTEnTListener implements Listener{
	@EventHandler(priority = EventPriority.HIGH)
	public void entityDead(EntityDeathEvent e) {
		if(e.getEntity() instanceof Villager) {
			//TODO Code for dead villagers
		}
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void entityDamaged(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			int i = 0;
			for(TTEnTGame game: TTEnTMain.activeGames) {
				for(IngamePlayer p: game.players) {
					if(p.rep == player.getName() && player.getHealth() - e.getDamage() <= 0) {
						e.setCancelled(true);
						player.setHealth(20);
						player.getInventory().remove(new ItemStack(Material.SKULL_ITEM, 1));
						TTEnTMain.worlds.get(0).dropItem(player.getLocation(), new ItemStack(Material.SKULL_ITEM, 1));
						TTEnTMain.activeGames.get(i).deadPlayers.add(p);
						player.teleport(game.deathSpawn);
						return;
					}
				}
				i++;
			}
		}
	}
	public void playerPickUpItem(PlayerPickupItemEvent e) {
		if(e.getItem().getItemStack().equals(new ItemStack(Material.SKULL_ITEM, 1))) {
			
		}
	}
}
