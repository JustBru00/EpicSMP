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
package com.gmail.justbru00.epic.smp.Main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.justbru00.epic.smp.CommandExecutors.BuyCommand;
import com.gmail.justbru00.epic.smp.CommandExecutors.EpicSMP;
import com.gmail.justbru00.epic.smp.CommandExecutors.Withdraw;
import com.gmail.justbru00.epic.smp.Listeners.Listener;
import com.gmail.justbru00.epic.smp.util.Messager;

@SuppressWarnings("unused")
public class Main extends JavaPlugin{
	
	public static String Prefix = color("&8[&bEpic&fSMP&8] &f");	
	public static Logger log = Bukkit.getLogger();
	public static ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
	public final String PLUGIN_VERSION = this.getDescription().getVersion();
	public final List<String> PLUGIN_AUTHORS = this.getDescription().getAuthors();
	public final int CONFIG_VERSION = 3;
	public static Economy econ = null;
	public static RegisteredServiceProvider<Permission> permissionProvider;
	public static Permission permission;
	public static boolean debugMode = false;
	public final int RESOURCE_NUMBER = 11503;
	public boolean useFlyCost = true;
	public List<Player> kickFromFlying = new ArrayList<Player>();
	private static final int BSTATS_METRICS_ID = 4566;
	

	@Override
	public void onDisable() {
		disablePlugin();
	}

	@Override
	public void onEnable() {
		enablePlugin();
	}

	/**
	 * Enables Plugin
	 * 
	 */
	public void enablePlugin() {	 	
		
		this.saveDefaultConfig();
		
		getServer().getPluginManager().registerEvents(new Listener(this), this);
		
        if (!setupEconomy() ) {
            console.sendMessage(color(String.format("%s &cDisabled due to Vault NOT FOUND!", Prefix)));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }			
		
		msgConsole("EpicSMP Version " + PLUGIN_VERSION + " is copyright 2020 Justin Brubaker. For license info see /epicsmp license");
		msgConsole("&aEnabling plugin.");
		
		permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
		permission = permissionProvider.getProvider();
	
		Prefix = getConfigString("plugin messages.prefix");
		msgConsole("&bPrefix has been set to the one in the config.");
		
        getCommand("epicsmp").setExecutor(new EpicSMP(this));
        getCommand("buycommand").setExecutor(new BuyCommand(this));
        getCommand("withdraw").setExecutor(new Withdraw(this));

        BStats metrics = new BStats(this, BSTATS_METRICS_ID);
        
		msgConsole("&bPlugin has been enabled.");
	}
	
	/**
	 * Disables Plugin
	 */
	public void disablePlugin(){
		msgConsole("Plugin has been disabled.");
	}

	public String getConfigString(String path){
		return color(getConfig().getString(path));
	}
	
	/**
	 * @deprecated Use Messager
	 * @param msg
	 */
	public static void msgConsole(String msg) {
		Messager.msgConsole(msg);
	}
	
	/** 
	 * @deprecated Use Messager
	 * @param uncoloredtext | Text to Color
	 * @return | Returns Text Colored
	 */
	public static String color(String uncoloredtext) {
		return Messager.color(uncoloredtext);
	}
	


	
	private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}
