package com.github.TheDrunkLeprechaun.TacticalTestificateEntrenchment;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import org.bukkit.inventory.ItemStack;

public class TTEnTListener implements Listener {
	
	private List<ResPlayer> skullHolders = new ArrayList<ResPlayer>();
	
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
				int it = 0;
				for(IngamePlayer p: game.players) {
					if(p.getRep().getName() == player.getName() && player.getHealth() - e.getDamage() <= 0) {
						e.setCancelled(true);
						player.setHealth(20);
						player.getInventory().remove(new ItemStack(Material.SKULL_ITEM, 1));
						TTEnTMain.worlds.get(0).dropItem(player.getLocation(), new ItemStack(Material.SKULL_ITEM, 1));
						TTEnTMain.activeGames.get(i).deadPlayers.add(p);
						TTEnTMain.activeGames.get(i).players.get(it).setSpawnHeadLocation(player.getLocation());
						player.teleport(game.deathSpawn);
						return;
					}
					it++;
				}
				i++;
			}
		}
	}
	@EventHandler(priority = EventPriority.HIGH)
	public void playerPickUpItem(PlayerPickupItemEvent e) {
		if(e.getItem().getItemStack().equals(new ItemStack(Material.SKULL_ITEM, 1))) {
			for(TTEnTGame game: TTEnTMain.activeGames) {
				for(IngamePlayer p: game.players) {
					e.getItem().getLocation().equals(p.getSpawnHeadLocation());
					p.getRep().sendMessage("Player " + e.getPlayer().getName() + " has picked up your skull and is bringing it back to ressurect you.");
					e.getPlayer().sendMessage("You have picked up " + p.getRep().getName() + "'s skull. Bring it back to spawn to ressurect them.");
					int i = 0;
					for(ResPlayer r: skullHolders) {
						if(r.equals(e.getPlayer())) {
							skullHolders.get(i).addRes(p.getRep());
						}
						i++;
					}
				}
			}
		}
	}
	public void playerPlaceBlock(PlayerInteractEvent e) {
		if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getPlayer().getItemInHand().equals(new ItemStack(Material.SKULL_ITEM, 1))) {
			for(TTEnTGame game: TTEnTMain.activeGames) {
				if(e.getClickedBlock().getLocation().equals(game.spawn)) {
					
				}
			}
		}
	}
}
