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

import java.util.List;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.justbru00.epic.smp.CommandExecutors.BuyCommand;
import com.gmail.justbru00.epic.smp.CommandExecutors.EpicSMP;
import com.gmail.justbru00.epic.smp.Listeners.Listener;

public class Main extends JavaPlugin{
	
	public static String Prefix = color("&8[&bEpic&fSMP&8] &f");	
	public static ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
	public final String PLUGIN_VERSION = this.getDescription().getVersion();
	public final List<String> PLUGIN_AUTHORS = this.getDescription().getAuthors();
	public final int CONFIG_VERSION = 1;
	public static Economy econ = null;
	public static RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
	public static Permission permission = permissionProvider.getProvider();
	public static boolean debugMode = false;


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
	 */
	public void enablePlugin() {	 	
		
		this.saveDefaultConfig();
		
		getServer().getPluginManager().registerEvents(new Listener(), this);
		
        if (!setupEconomy() ) {
            console.sendMessage(color(String.format("%s &cDisabled due to Vault NOT FOUND!", Prefix)));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
		
		if (!checkConfigVersion()) {
			msgConsole("&c**** WARNING ****");
			msgConsole("&cConfig is OUTDATED or the config version was changed. Please delete it and restart your server.");
			msgConsole("&cEPICSMP MAY NOT FUNCTION AS EXPECTED!!!");
			msgConsole("&cPlugin will now disable.");
			msgConsole("&c**** WARNING ****");	
			Bukkit.getServer().getPluginManager().disablePlugin(this);
		}
		
		
		msgConsole("EpicSMP Version " + PLUGIN_VERSION + " is copyright 2015 Justin Brubaker. For license info see /epicsmp license");
		msgConsole("&aEnabling plugin.");
	
		Prefix = getConfigString("plugin messages.prefix");
		msgConsole("&bPrefix has been set to the one in the config.");
		
        getCommand("epicsmp").setExecutor(new EpicSMP(this));
        getCommand("buycommand").setExecutor(new BuyCommand(this));
		msgConsole("&bCommand Executors have been set.");		
		
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
	
	public static void msgConsole(String msg) {
		console.sendMessage(Prefix + color(msg));
	}
	
	/** 
	 * @param uncoloredtext | Text to Color
	 * @return | Returns Text Colored
	 */
	public static String color(String uncoloredtext) {
		return ChatColor.translateAlternateColorCodes('&', uncoloredtext);
	}
	
	/**
	 * 
	 * @return | true if config version is ok
	 * @return | false if config version is not ok
	 */
	public boolean checkConfigVersion() {
		if(getConfig().getInt("config version") != CONFIG_VERSION) {
			return false;
		}
		return true;
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
