package com.github.TheDrunkLeprechaun.TacticalTestificateEntrenchment;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TTEnTCommandExcecutor implements CommandExecutor{
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
					TTEnTMain.privateGames.get(args[0]).players.add(new IngamePlayer(TTEnTMain.privateGames.get(args[0]),
							Bukkit.getServer().getPlayer(args[2])));
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
					TTEnTMain.publicGames.get(args[0]).players.add(new IngamePlayer(TTEnTMain.publicGames.get(args[0]),
							Bukkit.getServer().getPlayer(args[2])));
					return true;
				}
				else if(args[1].equalsIgnoreCase("kickPlayer") && sender.hasPermission("publicGame.kickPlayer")) {
					TTEnTMain.publicGames.get(args[0]).players.remove(new IngamePlayer(TTEnTMain.publicGames.get(args[0]),
							Bukkit.getServer().getPlayer(args[2])));
					return true;
				}
			}
		}
		else if(cmd.getName().equalsIgnoreCase("acceptInvite")) {
			//TODO add code for sending and accepting invites
		}
		else if(cmd.getName().equalsIgnoreCase("joinGame")) {
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
