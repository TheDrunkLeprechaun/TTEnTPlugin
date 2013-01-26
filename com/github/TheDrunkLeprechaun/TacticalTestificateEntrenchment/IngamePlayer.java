package com.github.TheDrunkLeprechaun.TacticalTestificateEntrenchment;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class IngamePlayer {
	
	private Location spawn;
	private Location spawnHead;
	private String rep;
	
	public IngamePlayer(TTEnTGame game, Player rep) {
		spawn = game.spawn;
		setSpawnHeadLocation(game.spawn);
		this.rep = rep.getName();
	}
	public void spawn() {
		getRep().teleport(this.spawn);
	}
	public Location getSpawn() {
		return spawn;
	}
	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}
	public Location getSpawnHeadLocation() {
		return spawnHead;
	}
	public void setSpawnHeadLocation(Location spawnHead) {
		this.spawnHead = spawnHead;
	}
	public Player getRep() {
		return Bukkit.getPlayer(rep);
	}
}
