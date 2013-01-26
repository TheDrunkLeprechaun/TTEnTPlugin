package com.github.TheDrunkLeprechaun.TacticalTestificateEntrenchment;


import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TTEnTCommandExcecutor implements CommandExecutor {

	private HashMap<String, String> privateInvites = new HashMap<String, String>(), publicInvites = new HashMap<String, String>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("privateGame") && sender.hasPermission("privateGame")) {
			if(args.length <= 1) {
				sender.sendMessage("Too few arguments.");
				return false; 
			}
			else if(args.length == 2) {
				if(args[1].equalsIgnoreCase("create") && sender.hasPermission("privateGame.create")) {
					TTEnTMain.privateGames.put(args[0], new TTEnTGame());
					return true;
				}
				else if(args[1].equalsIgnoreCase("start") && sender.hasPermission("privateGame.start")) {
					TTEnTMain.privateGames.get(args[0]).start();
					TTEnTMain.activeGames.add(TTEnTMain.privateGames.get(args[0]));
					return true;
				}
				else if(args[1].equalsIgnoreCase("end") && sender.hasPermission("privateGame.end")) {
					TTEnTMain.privateGames.get(args[0]).end();
					TTEnTMain.activeGames.remove(TTEnTMain.privateGames.get(args[0]));
					return true;
				}
			}
			else if(args.length == 3) {
				if(args[1].equalsIgnoreCase("invitePlayer") && sender.hasPermission("privateGame.invitePlayer")) {
					privateInvites.put(args[2], args[0]);
					return true;
				}
				else if(args[1].equalsIgnoreCase("kickPlayer") && sender.hasPermission("privateGame.kickPlayer")) {
					TTEnTMain.privateGames.get(args[0]).players.remove(new IngamePlayer(TTEnTMain.privateGames.get(args[0]),
							Bukkit.getServer().getPlayer(args[2])));
					return true;
				}
			}
		}
		else if(cmd.getName().equalsIgnoreCase("publicGame") && sender.hasPermission("publicGame")) {
			if(args.length <= 1) {
				sender.sendMessage("Too few arguments.");
				return false; 
			}
			else if(args.length == 2) {
				if(args[1].equalsIgnoreCase("create") && sender.hasPermission("publicGame.create")) {
					TTEnTMain.publicGames.put(args[0], new TTEnTGame());
					return true;
				}
				else if(args[1].equalsIgnoreCase("start") && sender.hasPermission("publicGame.start")) {
					TTEnTMain.publicGames.get(args[0]).start();
					TTEnTMain.activeGames.add(TTEnTMain.publicGames.get(args[0]));
					return true;
				}
				else if(args[1].equalsIgnoreCase("end") && sender.hasPermission("publicGame.end")) {
					TTEnTMain.publicGames.get(args[0]).end();
					TTEnTMain.activeGames.remove(TTEnTMain.publicGames.get(args[0]));
					return true;
				}
			}
			else if(args.length == 3) {
				if(args[1].equalsIgnoreCase("invitePlayer") && sender.hasPermission("publicGame.invitePlayer")) {
					publicInvites.put(args[2], args[0]);
					return true;
				}
				else if(args[1].equalsIgnoreCase("kickPlayer") && sender.hasPermission("publicGame.kickPlayer")) {
					TTEnTMain.publicGames.get(args[0]).players.remove(new IngamePlayer(TTEnTMain.publicGames.get(args[0]),
							Bukkit.getServer().getPlayer(args[2])));
					return true;
				}
			}
		}
		else if(cmd.getName().equalsIgnoreCase("acceptInvite") && sender.hasPermission("joinGame")) {
			if(args.length != 1) {
				return false;
			}
			if(!(sender instanceof Player)) {
				sender.sendMessage("Only players can accept invites.");
				return false;
			}
			//Check if the game name is an actual game, checks if the sender has an invite, and checks if the sender is a player
			if(TTEnTMain.privateGames.get(args[0]) != null && privateInvites.get(sender.getName()) != null) {
				//Get the game represented by the game name, then add a new player
				TTEnTMain.privateGames.get(args[0]).players.add(new IngamePlayer(TTEnTMain.privateGames.get(args[0]),
						Bukkit.getPlayer(sender.getName())));
				return true;
			}
			//Now, do the same for the public games.
			if(TTEnTMain.publicGames.get(args[0]) != null && publicInvites.get(sender.getName()) != null) {
				TTEnTMain.publicGames.get(args[0]).players.add(new IngamePlayer(TTEnTMain.publicGames.get(args[0]),
						Bukkit.getPlayer(sender.getName())));
				return true;
			}
		}
		else if(cmd.getName().equalsIgnoreCase("joinGame") && sender.hasPermission("joinGame")) {
			if(sender instanceof Player) {
				TTEnTMain.publicGames.get(args[0]).players.add(new IngamePlayer(TTEnTMain.publicGames.get(args[0]), (Player) sender));
			}
			else {
				sender.sendMessage("Only players can join games.");
			}
		}
		return false; 
	}
}
