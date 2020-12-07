package com.gmail.justbru00.epic.smp.util;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.justbru00.epic.smp.Main.Main;

public class ItemMaker {

	@SuppressWarnings("deprecation")
	public static ItemStack createItemStack(String nocolordisplayname, String material, String lore, String lore2) {
		Material m;
		
	try {	
		m = Material.getMaterial(material);
	} catch (Exception e) {
		Main.console.sendMessage(Main.Prefix + Main.color("&cAn error happened when getting the material: " + material + ". Changed it to paper instead."));
		m = Material.PAPER;
	}
	
		ItemStack is = new ItemStack(m);		
		ItemMeta im = is.getItemMeta();
		
		im.setDisplayName(Main.color(nocolordisplayname));
		
		ArrayList<String> lorelist = new ArrayList<String>();
		lorelist.add(Main.color(lore));
		lorelist.add(Main.color(lore2));
		
		im.setLore(lorelist);
		
		is.setItemMeta(im);
		
		return is;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack createItemStack(String nocolordisplayname, String material) {
		Material m;
		
	try {	
		m = Material.getMaterial(material);
	} catch (Exception e) {
		Main.console.sendMessage(Main.Prefix + Main.color("&cAn error happened when getting the material: " + material + ". Changed it to paper instead."));
		m = Material.PAPER;
	}
	
		ItemStack is = new ItemStack(m);		
		ItemMeta im = is.getItemMeta();
		
		im.setDisplayName(Main.color(nocolordisplayname));

		
		is.setItemMeta(im);
		
		return is;
	}
}
