package com.github.TheDrunkLeprechaun.TacticalTestificateEntrenchment;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class ResPlayer {

	private List<Player> resPlayers = new ArrayList<Player>();

	private IngamePlayer player;

	public ResPlayer(IngamePlayer player, Player res) {
		this.player = player;
		resPlayers.add(res);
	}
	public void addRes(Player newRes) {
		resPlayers.add(newRes);
	}
	@Override
	public boolean equals(Object o) {
		if(o instanceof ResPlayer) {
			ResPlayer other = (ResPlayer) o;
			if(other.player.getRep().getName() == this.player.getRep().getName())
				return true;
			else
				return false;
		}
		else if(o instanceof Player) {
			Player other = (Player) o;
			if(other.getName() == this.player.getRep().getName())
				return true;
			else
				return false;
		}
		return false;
	}
}
