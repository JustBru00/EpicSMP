package com.gmail.justbru00.epic.smp.CommandExecutors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.gmail.justbru00.epic.smp.Main.Main;

public class EpicSMP implements CommandExecutor {
	
	Main main = new Main();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {	
		
		if (command.getName().equalsIgnoreCase("epicsmp")) {
			if (sender.hasPermission(main.getConfigString("commands.epicsmp.permission"))) {
				if (args[0].equalsIgnoreCase("help")) {
					sender.sendMessage(Main.Prefix + Main.color(main.getConfigString("commands.epicsmp.help")));
					return true;
				} else if (args[0].equalsIgnoreCase("license")) {
					sender.sendMessage(Main.Prefix + Main.color(main.getConfigString("commands.epicsmp.license")));
					return true;
				} else {
					sender.sendMessage(Main.Prefix + Main.color(main.getConfigString("commands.epicsmp.not enough args")));
					return true;
				}
			} else {
				sender.sendMessage(Main.Prefix + Main.color(main.getConfigString("plugin messages.no permission")));
				return true;
			}
		}
		return false;
	}

}
