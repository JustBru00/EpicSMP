package com.gmail.justbru00.epic.smp.Main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	public static String Prefix = "&8[&bEpic&fSMP&8]";

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		return false;
	}

	@Override
	public void onDisable() {

	}

	@Override
	public void onEnable() {
		enablePlugin();
	}

	
	public void enablePlugin(){
	
	}
	

}
