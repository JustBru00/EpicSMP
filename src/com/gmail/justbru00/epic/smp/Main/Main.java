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
import com.gmail.justbru00.epic.smp.CommandExecutors.Withdraw;
import com.gmail.justbru00.epic.smp.Listeners.Listener;

public class Main extends JavaPlugin{
	
	public static String Prefix = color("&8[&bEpic&fSMP&8] &f");	
	public static ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
	public final String PLUGIN_VERSION = this.getDescription().getVersion();
	public final List<String> PLUGIN_AUTHORS = this.getDescription().getAuthors();
	public final int CONFIG_VERSION = 2;
	public static Economy econ = null;
	public static RegisteredServiceProvider<Permission> permissionProvider;
	public static Permission permission;
	public static boolean debugMode = false;
	public final int RESOURCE_NUMBER = 11503;

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
        
        // Check for updates on spigot.
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(
                    "http://www.spigotmc.org/api/general.php").openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.getOutputStream()
                    .write(("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=" + RESOURCE_NUMBER)
                            .getBytes("UTF-8"));
            String version = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
            if (version != null) {            	
            	if (version.equalsIgnoreCase(PLUGIN_VERSION)) {
            		msgConsole("No Update Found.");
            	} else {
            		msgConsole("Found a update please download it at: https://www.spigotmc.org/resources/epicsmp.11503/");
            	}
            }
        } catch (Exception ex) {
           msgConsole("Failed to check for a update on spigot.");
        }
		
		
		msgConsole("EpicSMP Version " + PLUGIN_VERSION + " is copyright 2015 Justin Brubaker. For license info see /epicsmp license");
		msgConsole("&aEnabling plugin.");
		
		permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
		permission = permissionProvider.getProvider();
	
		Prefix = getConfigString("plugin messages.prefix");
		msgConsole("&bPrefix has been set to the one in the config.");
		
        getCommand("epicsmp").setExecutor(new EpicSMP(this));
        getCommand("buycommand").setExecutor(new BuyCommand(this));
        getCommand("withdraw").setExecutor(new Withdraw(this));
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
