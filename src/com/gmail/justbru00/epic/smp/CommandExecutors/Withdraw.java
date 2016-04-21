/**
 *     EpicSMP Plugin
    Copyright (C) 2015  Justin A. Brubaker

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.


	Contact me at justbru00@gmail.com
 */
package com.gmail.justbru00.epic.smp.CommandExecutors;

import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import com.gmail.justbru00.epic.smp.Main.Main;
import com.gmail.justbru00.epic.smp.util.ItemMaker;

public class Withdraw implements CommandExecutor{
	
	Main main;
	
	public Withdraw(Main main) {
		this.main = main;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command,	String label, String[] args) {
		if (command.getName().equalsIgnoreCase("withdraw")) {			
			if (sender instanceof Player) {
				if (args.length == 1) {
					Player player = (Player) sender;
					if (!player.hasPermission(main.getConfigString("commands.withdraw.permission"))) {
						player.sendMessage(main.getConfigString("plugin messages.no permission"));
						return true;
					}
					double withdrawAmount = 0;
					try {
					    withdrawAmount = Double.parseDouble(args[0]);
					} catch(Exception e){
						sender.sendMessage(Main.Prefix + main.getConfigString("commands.withdraw.use a number"));
						return true;
					}
					EconomyResponse r = Main.econ.withdrawPlayer(player, withdrawAmount);
		            if(r.transactionSuccess()) {
		                player.sendMessage(String.format(Main.Prefix + "Withdrew %s and now you have %s", Main.econ.format(r.amount), Main.econ.format(r.balance)));
		                PlayerInventory pi = player.getInventory();
		                pi.addItem(ItemMaker.createItemStack(Main.color("&4&l$" + args[0]), "PAPER", Main.color("&7Right Click in the air to deposit this."), Main.Prefix));
		                
		                return true;		                
		            } else {
		                player.sendMessage(String.format(Main.Prefix + Main.color("&4An error occured: %s"), r.errorMessage));	
		                return true;
		            }			
				} else {
					sender.sendMessage(Main.Prefix + main.getConfigString("commands.withdraw.too many or too little args"));
				}
			} else {
				sender.sendMessage(Main.Prefix + main.getConfigString("commands.withdraw.sender not player"));
				return true;
			}
		}
		return false;
	}

}
