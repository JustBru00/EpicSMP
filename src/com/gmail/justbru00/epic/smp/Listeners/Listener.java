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
package com.gmail.justbru00.epic.smp.Listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.justbru00.epic.smp.Main.Main;
import com.gmail.justbru00.epic.smp.util.ItemMaker;
import com.gmail.justbru00.epic.smp.util.Messager;

import net.milkbowl.vault.economy.EconomyResponse;

public class Listener implements org.bukkit.event.Listener {

	Main main;
	String[] itemnumberlist = { "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven",
			"twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty",
			"twentyone", "twentytwo", "twentythree", "twentyfour", "twentyfive", "twentysix", "twentyseven" };

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		final Player player = e.getPlayer();

		if (player == null) {
			return;
		}

		if (e.getAction() != Action.RIGHT_CLICK_AIR) {
			return;
		}

		if (player.getInventory().getItemInMainHand().getItemMeta().getLore() == null) {
			return;
		}

		ArrayList<String> testLore = new ArrayList<String>();
		testLore.add(Messager.color("&7Right click in the air to deposit this."));
		testLore.add(Messager.color(Main.Prefix));

		PlayerInventory inv = player.getInventory();
		ItemStack itemInMainHand = inv.getItemInMainHand();
		
		if (!itemInMainHand.hasItemMeta() && itemInMainHand.getType() != Material.PAPER) {
			return;
		}
		
		ItemMeta itemInMainHandMeta = itemInMainHand.getItemMeta();
		
		if (!itemInMainHandMeta.hasLore()) {
			return;
		}
		
		List<String> itemInMainHandLore = itemInMainHandMeta.getLore();		
		
		if (itemInMainHandLore.equals(testLore)) {
			if (e.getAction() == Action.RIGHT_CLICK_AIR) {				
					double depositamount = 0.0;
					String plainMoney = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()
							.replace(Messager.color("&4&l$"), " ");
					ChatColor.stripColor(plainMoney);
					depositamount = Double.parseDouble(plainMoney);
					EconomyResponse r = Main.econ.depositPlayer(player, depositamount);
					if (r.transactionSuccess()) {
						player.sendMessage(String.format(Main.Prefix + "Deposited %s and now you have %s",
								Main.econ.format(r.amount), Main.econ.format(r.balance)));
						int amountInHand = itemInMainHand.getAmount();
						if (amountInHand == 1) {
							e.setCancelled(true);
							player.getInventory().remove(itemInMainHand);
						} else {
							itemInMainHand.setAmount(--amountInHand);
						}
					} else {
						player.sendMessage(
								String.format(Main.Prefix + Messager.color("&4An error occured: %s"), r.errorMessage));
					}
				}
			
		}
	}

	public Listener(Main main) {
		this.main = main;
	}

	// Sends plugin developer (Justin Brubaker) a msg when he joins the server.
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (e.getPlayer().getUniqueId().toString().equals("28f9bb08-b33c-4a7d-b098-ebf271383966")) {
			e.getPlayer().sendMessage(Messager.color(Main.Prefix + "&bThis Server Uses EpicSMP."));
		}
	}

	@SuppressWarnings({ "unchecked" })
	@EventHandler
	public void InventoryClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();

		if (e.getInventory() == null) {
			return;
		} else if (e.getCurrentItem() == null) {
			return;
		}

		if (e.getView().getTitle().contains(Messager.color("&4YOU ARE OP."))) {
			e.setCancelled(true);
			return;
		}

		// Confirm GUI checks :D
		if (e.getView().getTitle().contains(Messager.color("&bConfirm Purchase"))) {

			if (e.getCurrentItem() == null || e.getCurrentItem() == new ItemStack(Material.AIR)) {
				return;
			}

			// Cancel the action
			e.setCancelled(true);

			if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Messager.color("&cCANCEL"))) {
				p.closeInventory();
			}

			if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Messager.color("&aOK"))) {

				int number = Integer.parseInt(e.getInventory().getItem(22).getItemMeta().getDisplayName());
				EconomyResponse r = Main.econ.withdrawPlayer(p,
						main.getConfig().getInt("commands.buycommand.commands." + itemnumberlist[number] + ".price"));

				if (r.transactionSuccess()) {
					p.sendMessage(String.format(Main.Prefix + "Withdrew %s. Your balance is now %s",
							Main.econ.format(r.amount), Main.econ.format(r.balance)));

					List<String> permissionsList = (List<String>) main.getConfig()
							.getList("commands.buycommand.commands." + itemnumberlist[number] + ".permissions");

					int i = 0;

					while (i < permissionsList.size()) {

						Main.permission.playerAdd(p, permissionsList.get(i));

						i++;
					}

					p.closeInventory();
				} else {
					p.sendMessage(
							String.format(Main.Prefix + Messager.color("&4An error occured: %s"), r.errorMessage));
					p.closeInventory();
				}
			}
		}

		if (e.getView().getTitle().contains(Messager.color("&cBalance: $"))) {
			// If item they clicked on is a empty space or Air do nothing.
			if (e.getCurrentItem() == null || e.getCurrentItem() == new ItemStack(Material.AIR)) {
				return;
			}

			// Cancel the action
			e.setCancelled(true);

			// Start Item Checks
			int i = 0;
			while (i < itemnumberlist.length) {
				if (Main.debugMode) {
					Bukkit.broadcastMessage("Checking ArrayList #" + i);
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Messager
						.color(main.getConfigString("commands.buycommand.commands." + itemnumberlist[i] + ".name")))) {

					int itemcost = main.getConfig()
							.getInt("commands.buycommand.commands." + itemnumberlist[i] + ".price");
					String loc = String.valueOf(i);

					Inventory inv = Bukkit.createInventory(null, 27, Messager.color("&bConfirm Purchase"));

					ItemStack ok = ItemMaker.createItemStack("&aOK", "EMERALD_BLOCK");
					ItemStack cancel = ItemMaker.createItemStack("&cCANCEL", "REDSTONE_BLOCK");
					ItemStack cost = ItemMaker.createItemStack("&7Cost: &c" + itemcost, "GOLD_INGOT");
					ItemStack indexnumber = ItemMaker.createItemStack(loc, "COMMAND_BLOCK_MINECART",
							"&cFor plugin usage.", "&cPlease ignore.");

					inv.setItem(0, ok);
					inv.setItem(1, ok);
					inv.setItem(2, ok);
					inv.setItem(9, ok);
					inv.setItem(10, ok);
					inv.setItem(11, ok);
					inv.setItem(18, ok);
					inv.setItem(19, ok);
					inv.setItem(20, ok);

					inv.setItem(6, cancel);
					inv.setItem(7, cancel);
					inv.setItem(8, cancel);
					inv.setItem(15, cancel);
					inv.setItem(16, cancel);
					inv.setItem(17, cancel);
					inv.setItem(24, cancel);
					inv.setItem(25, cancel);
					inv.setItem(26, cancel);

					inv.setItem(4, cost);
					inv.setItem(12, cost);
					inv.setItem(14, cost);
					inv.setItem(22, indexnumber);

					inv.setItem(13, e.getCurrentItem());

					p.openInventory(inv);

					break;
				}
				i++;
			}
			return;
		} // end of buycommand inventory checks

	} // end of click event
}
