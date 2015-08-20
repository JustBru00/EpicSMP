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

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.gmail.justbru00.epic.smp.Main.Main;
import com.gmail.justbru00.epic.smp.util.ItemMaker;

public class BuyCommand implements CommandExecutor{

	Main main;
	
	public BuyCommand(Main main) {
		this.main = main; 
	}
	
	// EG. Main.permission.playerAdd(player, permission);
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (command.getName().equalsIgnoreCase("buycommand")) {			
			if (sender instanceof Player) {				
				Player player = (Player) sender; 
				if (!player.hasPermission(main.getConfigString("commands.buycommand.permission"))) {
					player.sendMessage(Main.Prefix + main.getConfigString("plugin messages.no permission"));
				}
				double money = 100;
				money = Main.econ.getBalance(player);
				if (money <= 99999999) {
					player.openInventory(GUI(player, money));
					return true;
				} else {
					player.openInventory(GUI(player, 99999999));
					return true;
				}
			} else {
				sender.sendMessage(Main.Prefix + main.getConfigString("commands.buycommand.not correct sender"));
				return true;
			}
		}
		
		return false;
	}
	
	public Inventory GUI(Player player, double money) {
		Inventory inv = Bukkit.createInventory(null, 54, Main.color("&cBalance: $" + money));
		
		if (checkPermissions(player, "commands.buycommand.commands.one.permissions")) {
			inv.addItem(ItemMaker.createItemStack(main.getConfigString("commands.buycommand.commands.one.name"), main.getConfigString("commands.buycommand.commands.one.material"), Main.color("&fClick to purchase."), 
					Main.color("&7Cost: " + main.getConfig().getInt("commands.buycommand.commands.one.price"))));
		}
		
		return inv;
	}
	
	/**
	 * @pram player	| Player to check.
	 * @param path | path to config list
	 * @return | returns true if found a permission the player doesn't have. returns false if player allready has all permissions.
	 */
	public boolean checkPermissions(Player player,String path) {
		@SuppressWarnings("unchecked")
		List<String> permissionsList = (List<String>) main.getConfig().getList(path);
		
		int i = 0;
		
		while (i < permissionsList.size()) {
			if (!player.hasPermission(permissionsList.get(i))) {
				if (Main.debugMode) {
					Bukkit.broadcastMessage("Checked list location: #" + i);
				}
				return true;
			}
			i++;
		}
		
		return false;
	}
}
