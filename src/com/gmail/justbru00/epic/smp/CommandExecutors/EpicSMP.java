/**
 *     EpicSMP a minecraft plugin that adds helpful SMP server commands.
    Copyright (C) 2015  Justin A. Brubaker

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

	Contact me at: justbru00@gmail.com
 */
package com.gmail.justbru00.epic.smp.CommandExecutors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.gmail.justbru00.epic.smp.Main.Main;

public class EpicSMP implements CommandExecutor {
	
	Main main;
	
	public EpicSMP(Main main) {
		this.main = main; 
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {	
		
		// Command /epicsmp code start...
		if (command.getName().equalsIgnoreCase("epicsmp")) {
			
			// Check for permission.
			if (sender.hasPermission(main.getConfigString("commands.epicsmp.permission"))) {
				
				// Make sure args length is more than 0.
				if (args.length == 0) {
					sender.sendMessage(Main.Prefix + Main.color(main.getConfigString("commands.epicsmp.not enough args")));
					return true;
				}
				
				// Check args value.
				if (args[0].equalsIgnoreCase("help")) {
					sender.sendMessage(Main.Prefix + Main.color(main.getConfigString("commands.epicsmp.help")));
					return true;
				} else if (args[0].equalsIgnoreCase("license")) {
					sender.sendMessage(Main.Prefix + Main.color(main.getConfigString("commands.epicsmp.license")));
					return true;
				} else if (args[0].equalsIgnoreCase("reload")) {
					// ISSUE #14
					main.reloadConfig();
					Main.Prefix = main.getConfigString("plugin messages.prefix");
					sender.sendMessage(Main.Prefix + Main.color(main.getConfigString("commands.epicsmp.reload")));
					return true;
					// END ISSUE #14
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
