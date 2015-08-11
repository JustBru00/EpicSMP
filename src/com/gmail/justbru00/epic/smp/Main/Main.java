package com.gmail.justbru00.epic.smp.Main;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.justbru00.epic.smp.CommandExecutors.BuyCommand;
import com.gmail.justbru00.epic.smp.CommandExecutors.EpicSMP;
import com.gmail.justbru00.epic.smp.Listeners.Listener;

public class Main extends JavaPlugin{
	
	public static String Prefix = "&8[&bEpic&fSMP&8]";
	public static ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
	public final String PLUGIN_VERSION = this.getDescription().getVersion();
	public final List<String> PLUGIN_AUTHORS = this.getDescription().getAuthors();
	public final int CONFIG_VERSION = 1;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		return false;
	}

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
	public void enablePlugin(){
		checkConfigVersion();
		msgConsole("EpicSMP Version " + PLUGIN_VERSION + " is copyright 2015 Justin Brubaker. For license info see /epicsmp license");
		msgConsole("&aEnabling plugin.");
	
		Prefix = getConfigString("plugin messages.prefix");
		msgConsole("&bPrefix has been set to the one in the config.");
		
        getCommand("epicsmp").setExecutor(new EpicSMP());
        getCommand("buycommand").setExecutor(new BuyCommand());
		msgConsole("&bCommand Executors have been set.");
		
		getServer().getPluginManager().registerEvents(new Listener(), this);
		
		msgConsole("&bPlugin has been enabled.");
	}
	
	/**
	 * Disables Plugin
	 */
	public void disablePlugin(){
		
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
	public void checkConfigVersion() {
		if(getConfig().getInt("config version") != CONFIG_VERSION) {
			msgConsole("&c**** WARNING ****");
			msgConsole("&cConfig is OUTDATED or the config version was changed. Please delete it and restart your server.");
			msgConsole("&cPlugin will now disable itself.");
			msgConsole("&c**** WARNING ****");
			Bukkit.getServer().getPluginManager().disablePlugin(this);
		}
	}
}
