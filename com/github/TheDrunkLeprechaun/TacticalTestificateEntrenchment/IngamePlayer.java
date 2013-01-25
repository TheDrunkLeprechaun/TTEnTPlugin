package com.github.TheDrunkLeprechaun.TacticalTestificateEntrenchment;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class IngamePlayer {
	
	public Location spawn;
	public Location spawnHead;
	public String rep;
	
	public IngamePlayer(TTEnTGame game, Player rep) {
		spawn = game.spawn;
		spawnHead = game.spawn;
		this.rep = rep.getName();
	}
	public void spawn() {
		Bukkit.getServer().getPlayer(this.rep).teleport(this.spawn);
	}
}
