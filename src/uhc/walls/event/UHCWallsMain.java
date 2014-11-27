/*
 * Author: 598Johnn897
 * 
 * Date: Nov 25, 2014
 * Package: uhc.walls.event
 *
 */
package uhc.walls.event;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import lombok.Getter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import uhc.walls.event.command.CommandFramework;
import uhc.walls.event.lib.References;
import uhc.walls.event.util.EventUtil;

/**
 * The main class for the UHC Walls event plugin. <br>
 * Developed with Spigot 1.7.10 <br>
 * Date: November 25th, 2014 <br>
 * <url>http://hypixel.net/threads/event-uhc-walls.209966/</url> <br>
 * If you have any ideas for the event or are able to help <br>
 * and want to help just tell me! 
 * <p>
 * Thank you and Hope to see you at the event!<br>
 * - Johnn
 * 
 * @since UHCWalls-0.0.1-SNAPSHOT
 * @author 598Johnn897
 * @see JavaPlugin
 */
@SuppressWarnings("all") public class UHCWallsMain extends JavaPlugin 
{
	public UHCWallsMain instance;
	public final UHCWallsMain get() 
	{
        if (instance == null) instance = this; // K
        return instance;
	}
	
	@Getter public UHCWallsLogger uhcwLogger 
		= new UHCWallsLogger(this);
	
	@Getter public CommandFramework cmdFramework;
	
	Long start, end;

	@Override public void onLoad()
	{
		log(References.LOADING_MSG);
		start = System.currentTimeMillis();
		instance = this; // KTHXBAI
	}
	
	@Override public void onEnable() 
	{
		log(References.ENABLING_MSG);
		
		cmdFramework = new CommandFramework(get());
		cmdFramework.registerCommands();
		cmdFramework.registerHelp();
		
		EventUtil.registerEvents(get());
		
		end = System.currentTimeMillis() - start;
		log(References.ENABLED_MSG, end);
	}
	
	@Override public void onDisable()
	{
		log(References.DISABLING_MSG);
		log(References.DISBALED_MSG, TimeUnit.MILLISECONDS.toMinutes(
						System.currentTimeMillis() - start));
	}
	
	@Override public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		return cmdFramework.handleCommand(sender, label, cmd, args);
	}
	
	final void log(String msg, Object... args) { uhcwLogger.log(Level.INFO, String.format(msg, args)); } 
}
