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

import java.text.DecimalFormat;
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
	
	private static DecimalFormat moneyFormat = new DecimalFormat("###,###,###.00");

	Main main;
	
	public BuyCommand(Main main) {
		this.main = main; 
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (command.getName().equalsIgnoreCase("buycommand")) {			
			if (sender instanceof Player) {				
				Player player = (Player) sender; 
				if (!player.hasPermission(main.getConfigString("commands.buycommand.permission"))) {
					player.sendMessage(Main.Prefix + main.getConfigString("plugin messages.no permission"));
					return true;
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
	
	@SuppressWarnings("deprecation")
	public Inventory GUI(Player player, double money) {
		String moneyFormatted = moneyFormat.format(money);
		Inventory inv = Bukkit.createInventory(null, 27, Main.color("&cBalance: $" + moneyFormatted));
		
		if (player.isOp()) {
			player.sendMessage(Main.Prefix + Main.color("&cYou are OP. GUI MAY NOT WORK AS EXPECTED. TO TEST DEOP YOURSELF."));
			Inventory opinv = Bukkit.createInventory(null, 9, Main.color("&4YOU ARE OP."));			
			opinv.setItem(4, ItemMaker.createItemStack("&4You are OP", "BARRIER", "&bThe GUI WILL NOT WORK RIGHT", "&bWHEN YOU ARE OP."));
			return opinv;
		}
		// One
		if (checkPermissions(player, "commands.buycommand.commands.one.permissions")) {
			if (!main.getConfigString("commands.buycommand.commands.one.name").equalsIgnoreCase("null")) {
				inv.addItem(ItemMaker.createItemStack(main.getConfigString("commands.buycommand.commands.one.name"), main.getConfigString("commands.buycommand.commands.one.material"), Main.color("&fClick to purchase."), 
						Main.color("&7Cost: " + main.getConfig().getInt("commands.buycommand.commands.one.price"))));
			}			
		}
		// Two
		if (checkPermissions(player, "commands.buycommand.commands.two.permissions")) {
			if (!main.getConfigString("commands.buycommand.commands.two.name").equalsIgnoreCase("null")) {
			inv.addItem(ItemMaker.createItemStack(main.getConfigString("commands.buycommand.commands.two.name"), main.getConfigString("commands.buycommand.commands.two.material"), Main.color("&fClick to purchase."), 
					Main.color("&7Cost: " + main.getConfig().getInt("commands.buycommand.commands.two.price"))));
			}
		}
		// Three
		if (checkPermissions(player, "commands.buycommand.commands.three.permissions")) {
			if (!main.getConfigString("commands.buycommand.commands.three.name").equalsIgnoreCase("null")) {
			inv.addItem(ItemMaker.createItemStack(main.getConfigString("commands.buycommand.commands.three.name"), main.getConfigString("commands.buycommand.commands.three.material"), Main.color("&fClick to purchase."), 
					Main.color("&7Cost: " + main.getConfig().getInt("commands.buycommand.commands.three.price"))));
			}
		}
		// Four
		if (checkPermissions(player, "commands.buycommand.commands.four.permissions")) {
			if (!main.getConfigString("commands.buycommand.commands.four.name").equalsIgnoreCase("null")) {
			inv.addItem(ItemMaker.createItemStack(main.getConfigString("commands.buycommand.commands.four.name"), main.getConfigString("commands.buycommand.commands.four.material"), Main.color("&fClick to purchase."), 
					Main.color("&7Cost: " + main.getConfig().getInt("commands.buycommand.commands.four.price"))));
			}
		}
		// Five
		if (checkPermissions(player, "commands.buycommand.commands.five.permissions")) {
			if (!main.getConfigString("commands.buycommand.commands.five.name").equalsIgnoreCase("null")) {
			inv.addItem(ItemMaker.createItemStack(main.getConfigString("commands.buycommand.commands.five.name"), main.getConfigString("commands.buycommand.commands.five.material"), Main.color("&fClick to purchase."), 
					Main.color("&7Cost: " + main.getConfig().getInt("commands.buycommand.commands.five.price"))));
			}
		}
		// Six
		if (checkPermissions(player, "commands.buycommand.commands.six.permissions")) {
			if (!main.getConfigString("commands.buycommand.commands.six.name").equalsIgnoreCase("null")) {
			inv.addItem(ItemMaker.createItemStack(main.getConfigString("commands.buycommand.commands.six.name"), main.getConfigString("commands.buycommand.commands.six.material"), Main.color("&fClick to purchase."), 
					Main.color("&7Cost: " + main.getConfig().getInt("commands.buycommand.commands.six.price"))));
			}
		}
		// Seven
		if (checkPermissions(player, "commands.buycommand.commands.seven.permissions")) {
			if (!main.getConfigString("commands.buycommand.commands.seven.name").equalsIgnoreCase("null")) {
			inv.addItem(ItemMaker.createItemStack(main.getConfigString("commands.buycommand.commands.seven.name"), main.getConfigString("commands.buycommand.commands.seven.material"), Main.color("&fClick to purchase."), 
					Main.color("&7Cost: " + main.getConfig().getInt("commands.buycommand.commands.seven.price"))));
			}
		}
		// Eight
		if (checkPermissions(player, "commands.buycommand.commands.eight.permissions")) {
			if (!main.getConfigString("commands.buycommand.commands.eight.name").equalsIgnoreCase("null")) {
			inv.addItem(ItemMaker.createItemStack(main.getConfigString("commands.buycommand.commands.eight.name"), main.getConfigString("commands.buycommand.commands.eight.material"), Main.color("&fClick to purchase."), 
					Main.color("&7Cost: " + main.getConfig().getInt("commands.buycommand.commands.eight.price"))));
			}
		}
		// Nine
		if (checkPermissions(player, "commands.buycommand.commands.nine.permissions")) {
			if (!main.getConfigString("commands.buycommand.commands.nine.name").equalsIgnoreCase("null")) {			
			inv.addItem(ItemMaker.createItemStack(main.getConfigString("commands.buycommand.commands.nine.name"), main.getConfigString("commands.buycommand.commands.nine.material"), Main.color("&fClick to purchase."), 
					Main.color("&7Cost: " + main.getConfig().getInt("commands.buycommand.commands.nine.price"))));
			}
		}
		// Ten
		if (checkPermissions(player, "commands.buycommand.commands.ten.permissions")) {
			if (!main.getConfigString("commands.buycommand.commands.ten.name").equalsIgnoreCase("null")) {
			inv.addItem(ItemMaker.createItemStack(main.getConfigString("commands.buycommand.commands.ten.name"), main.getConfigString("commands.buycommand.commands.ten.material"), Main.color("&fClick to purchase."), 
					Main.color("&7Cost: " + main.getConfig().getInt("commands.buycommand.commands.ten.price"))));
			}
		}
		// Eleven
		if (checkPermissions(player, "commands.buycommand.commands.eleven.permissions")) {
			if (!main.getConfigString("commands.buycommand.commands.eleven.name").equalsIgnoreCase("null")) {
			inv.addItem(ItemMaker.createItemStack(main.getConfigString("commands.buycommand.commands.eleven.name"), main.getConfigString("commands.buycommand.commands.eleven.material"), Main.color("&fClick to purchase."), 
					Main.color("&7Cost: " + main.getConfig().getInt("commands.buycommand.commands.eleven.price"))));
			}
		}
		// Twelve
		if (checkPermissions(player, "commands.buycommand.commands.twelve.permissions")) {
			if (!main.getConfigString("commands.buycommand.commands.twelve.name").equalsIgnoreCase("null")) {
			inv.addItem(ItemMaker.createItemStack(main.getConfigString("commands.buycommand.commands.twelve.name"), main.getConfigString("commands.buycommand.commands.twelve.material"), Main.color("&fClick to purchase."), 
					Main.color("&7Cost: " + main.getConfig().getInt("commands.buycommand.commands.twelve.price"))));
			}
		}
		// Thirteen
		if (checkPermissions(player, "commands.buycommand.commands.thirteen.permissions")) {
			if (!main.getConfigString("commands.buycommand.commands.thirteen.name").equalsIgnoreCase("null")) {
			inv.addItem(ItemMaker.createItemStack(main.getConfigString("commands.buycommand.commands.thirteen.name"), main.getConfigString("commands.buycommand.commands.thirteen.material"), Main.color("&fClick to purchase."), 
					Main.color("&7Cost: " + main.getConfig().getInt("commands.buycommand.commands.thirteen.price"))));
			}
		}
		// Fourteen
		if (checkPermissions(player, "commands.buycommand.commands.fourteen.permissions")) {
			if (!main.getConfigString("commands.buycommand.commands.fourteen.name").equalsIgnoreCase("null")) {
			inv.addItem(ItemMaker.createItemStack(main.getConfigString("commands.buycommand.commands.fourteen.name"), main.getConfigString("commands.buycommand.commands.fourteen.material"), Main.color("&fClick to purchase."), 
					Main.color("&7Cost: " + main.getConfig().getInt("commands.buycommand.commands.fourteen.price"))));
			}
		}
		// Fifteen
		if (checkPermissions(player, "commands.buycommand.commands.fifteen.permissions")) {
			if (!main.getConfigString("commands.buycommand.commands.fifteen.name").equalsIgnoreCase("null")) {
			inv.addItem(ItemMaker.createItemStack(main.getConfigString("commands.buycommand.commands.fifteen.name"), main.getConfigString("commands.buycommand.commands.fifteen.material"), Main.color("&fClick to purchase."), 
					Main.color("&7Cost: " + main.getConfig().getInt("commands.buycommand.commands.fifteen.price"))));
			}
		}
		// Sixteen
		if (checkPermissions(player, "commands.buycommand.commands.sixteen.permissions")) {
			if (!main.getConfigString("commands.buycommand.commands.sixteen.name").equalsIgnoreCase("null")) {
			inv.addItem(ItemMaker.createItemStack(main.getConfigString("commands.buycommand.commands.sixteen.name"), main.getConfigString("commands.buycommand.commands.sixteen.material"), Main.color("&fClick to purchase."), 
					Main.color("&7Cost: " + main.getConfig().getInt("commands.buycommand.commands.sixteen.price"))));
			}
		}
		// Seventeen
		if (checkPermissions(player, "commands.buycommand.commands.seventeen.permissions")) {
			if (!main.getConfigString("commands.buycommand.commands.seventeen.name").equalsIgnoreCase("null")) {
			inv.addItem(ItemMaker.createItemStack(main.getConfigString("commands.buycommand.commands.seventeen.name"), main.getConfigString("commands.buycommand.commands.seventeen.material"), Main.color("&fClick to purchase."), 
					Main.color("&7Cost: " + main.getConfig().getInt("commands.buycommand.commands.seventeen.price"))));
			}
		}
		// Eighteen
		if (checkPermissions(player, "commands.buycommand.commands.eighteen.permissions")) {
			if (!main.getConfigString("commands.buycommand.commands.eighteen.name").equalsIgnoreCase("null")) {
			inv.addItem(ItemMaker.createItemStack(main.getConfigString("commands.buycommand.commands.eighteen.name"), main.getConfigString("commands.buycommand.commands.eighteen.material"), Main.color("&fClick to purchase."), 
					Main.color("&7Cost: " + main.getConfig().getInt("commands.buycommand.commands.eighteen.price"))));
			}
		}
		// Nineteen
		if (checkPermissions(player, "commands.buycommand.commands.nineteen.permissions")) {
			if (!main.getConfigString("commands.buycommand.commands.nineteen.name").equalsIgnoreCase("null")) {
			inv.addItem(ItemMaker.createItemStack(main.getConfigString("commands.buycommand.commands.nineteen.name"), main.getConfigString("commands.buycommand.commands.nineteen.material"), Main.color("&fClick to purchase."), 
					Main.color("&7Cost: " + main.getConfig().getInt("commands.buycommand.commands.nineteen.price"))));
			}
		}
		// Twenty
		if (checkPermissions(player, "commands.buycommand.commands.twenty.permissions")) {
			if (!main.getConfigString("commands.buycommand.commands.twenty.name").equalsIgnoreCase("null")) {
			inv.addItem(ItemMaker.createItemStack(main.getConfigString("commands.buycommand.commands.twenty.name"), main.getConfigString("commands.buycommand.commands.twenty.material"), Main.color("&fClick to purchase."), 
					Main.color("&7Cost: " + main.getConfig().getInt("commands.buycommand.commands.twenty.price"))));
			}
		}
		// Twenty-one
		if (checkPermissions(player, "commands.buycommand.commands.twentyone.permissions")) {
			if (!main.getConfigString("commands.buycommand.commands.twentyone.name").equalsIgnoreCase("null")) {
			inv.addItem(ItemMaker.createItemStack(main.getConfigString("commands.buycommand.commands.twentyone.name"), main.getConfigString("commands.buycommand.commands.twentyone.material"), Main.color("&fClick to purchase."), 
					Main.color("&7Cost: " + main.getConfig().getInt("commands.buycommand.commands.twentyone.price"))));
			}
		}
		// Twenty-two
		if (checkPermissions(player, "commands.buycommand.commands.twentytwo.permissions")) {
			if (!main.getConfigString("commands.buycommand.commands.twentytwo.name").equalsIgnoreCase("null")) {
			inv.addItem(ItemMaker.createItemStack(main.getConfigString("commands.buycommand.commands.twentytwo.name"), main.getConfigString("commands.buycommand.commands.twentytwo.material"), Main.color("&fClick to purchase."), 
					Main.color("&7Cost: " + main.getConfig().getInt("commands.buycommand.commands.twentytwo.price"))));
			}
		}
		// Twenty-three
		if (checkPermissions(player, "commands.buycommand.commands.twentythree.permissions")) {
			if (!main.getConfigString("commands.buycommand.commands.twentythree.name").equalsIgnoreCase("null")) {
			inv.addItem(ItemMaker.createItemStack(main.getConfigString("commands.buycommand.commands.twentythree.name"), main.getConfigString("commands.buycommand.commands.twentythree.material"), Main.color("&fClick to purchase."), 
					Main.color("&7Cost: " + main.getConfig().getInt("commands.buycommand.commands.twentythree.price"))));
			}
		}
		// Twenty-four
		if (checkPermissions(player, "commands.buycommand.commands.twentyfour.permissions")) {
			if (!main.getConfigString("commands.buycommand.commands.twentyfour.name").equalsIgnoreCase("null")) {
			inv.addItem(ItemMaker.createItemStack(main.getConfigString("commands.buycommand.commands.twentyfour.name"), main.getConfigString("commands.buycommand.commands.twentyfour.material"), Main.color("&fClick to purchase."), 
					Main.color("&7Cost: " + main.getConfig().getInt("commands.buycommand.commands.twentyfour.price"))));
			}
		}
		// Twenty-five
		if (checkPermissions(player, "commands.buycommand.commands.twentyfive.permissions")) {
			if (!main.getConfigString("commands.buycommand.commands.twentyfive.name").equalsIgnoreCase("null")) {
			inv.addItem(ItemMaker.createItemStack(main.getConfigString("commands.buycommand.commands.twentyfive.name"), main.getConfigString("commands.buycommand.commands.twentyfive.material"), Main.color("&fClick to purchase."), 
					Main.color("&7Cost: " + main.getConfig().getInt("commands.buycommand.commands.twentyfive.price"))));
			}
		}
		// Twenty-six
		if (checkPermissions(player, "commands.buycommand.commands.twentysix.permissions")) {
			if (!main.getConfigString("commands.buycommand.commands.twentysix.name").equalsIgnoreCase("null")) {
			inv.addItem(ItemMaker.createItemStack(main.getConfigString("commands.buycommand.commands.twentysix.name"), main.getConfigString("commands.buycommand.commands.twentysix.material"), Main.color("&fClick to purchase."), 
					Main.color("&7Cost: " + main.getConfig().getInt("commands.buycommand.commands.twentysix.price"))));
			}
		}
		// Twenty-seven
		if (checkPermissions(player, "commands.buycommand.commands.twentyseven.permissions")) {
			if (!main.getConfigString("commands.buycommand.commands.twentyseven.name").equalsIgnoreCase("null")) {
			inv.addItem(ItemMaker.createItemStack(main.getConfigString("commands.buycommand.commands.twentyseven.name"), main.getConfigString("commands.buycommand.commands.twentyseven.material"), Main.color("&fClick to purchase."), 
					Main.color("&7Cost: " + main.getConfig().getInt("commands.buycommand.commands.twentyseven.price"))));
			}
		}
		return inv;
	}
	
	/**
	 * @pram player	| Player to check.
	 * @param path | path to config list
	 * @return | returns true if found a permission the player doesn't have. returns false if player already has all permissions.
	 */
	public boolean checkPermissions(Player player, String path) {
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
