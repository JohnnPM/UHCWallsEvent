/*
 * Author: 598Johnn897
 * 
 * Date: Oct 25, 2014
 * Package: fl.hypixel.event
 *
 */
package fl.hypixel.event;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import lombok.Getter;

import org.apache.commons.lang.Validate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import fl.hypixel.event.commands.CommandFramework;
import fl.hypixel.event.lib.References;
import fl.hypixel.event.util.EventUtil;

/**
 * Main class for Fallen Legends Event. 
 * <p>
 * <b>------------------------</b>
 * <p>
 * Created: Oct 25, 2014 <br>
 * Time: 9:42:18 PM <br>
 * Year: 2014 <p>
 *
 * By: 598Johnn897 <p>
 * 
 * Project: Fallen Legends Guild Event Server Plugin <br>
 * File: FLEventMain.java <br>
 * Package: fl.hypixel.event <p>
 * 
 * @author 598Johnn897
 * 
 * @since 0.0.1-SNAPSHOT
 * 
 * @see JavaPlugin
 */
@SuppressWarnings("all") public class FLEventMain extends JavaPlugin 
{
	/**
	 * The instance of the plugin, Initialized in the
	 * onEnable method indside of this class.
	 * 
	 * @author 598Johnn897
	 * 
	 * @since 0.0.1-SNAPSHOT
	 */
	private static FLEventMain instance;
	
	/**
	 * The method to get the instance of the class
	 * which is {@link #instance}.
	 * 
	 * @since 0.0.1-SNAPSHOT
	 * 
	 * @return The instance of the main class
	 */
	public static final FLEventMain get() 
	{
		Validate.notNull(instance);
		return instance;
	}
	
	/**
	 * The command framework for the plugin. Registers
	 * commands and adds them to the help menu. Completely
	 * automated.
	 * 
	 * @author 598Johnn897
	 * 
	 * @since 0.0.1-SNAPSHOT
	 * 
	 * @see CommandFramework
	 */
	@Getter private CommandFramework cmdFramework;
	
	/**
	 * The logger instance for this class. This
	 * will handle the logging to the console from
	 * this file/main core class.
	 * 
	 * @since ProjectParty-0.0.1-SNAPSHOT
	 * 
	 * @author 598Johnn897
	 * 
	 * @see Logger#getLogger(String)
	 */
	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
	
	/**
	 * The file instance for the config
	 * 
	 * @author 598Johnn897
	 * 
	 * @since 0.0.1-SNAPSHOT
	 */
	private File configFile;
	
	/**
	 * The config instance for the plugin
	 * 
	 * @author 598Johnn897
	 * 
	 * @since 0.0.1-SNAPSHOT
	 */
	@Getter private Config fLConfig;
	
	/**
	 * Variables to keep track of the end time and
	 * start times and logs them when the plugin is
	 * disabled or enabled.
	 * 
	 * @author 598Johnn897
	 * 
	 * @since 0.0.1-SNAPSHOT
	 */
	long start, end;
	
	@Override public void onLoad()
	{
		log(References.LOADING_MSG);
		
		start = System.currentTimeMillis();
		
		instance = this;
	}
	
	@Override public void onEnable()
	{
		log(References.ENABLE_START_MSG);
		
		try 
		{
			Validate.notNull(instance);
			
			EventUtil.registerEvents(this);
			
			cmdFramework = new CommandFramework(this);
			cmdFramework.registerCommands();
			cmdFramework.registerHelp();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			end = start - System.currentTimeMillis();
			log(String.format(References.ENABLED_MSG, end));
		}
	}
	
	@Override public void onDisable()
	{
		try
		{
			ChatLogger.writeToFile();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			end = start - System.currentTimeMillis();
			log(String.format(References.DISABLED_MSG, TimeUnit.MILLISECONDS.toMinutes(end)));
		}
	}
	
	/**
	 * Overrides the
	 * {@link JavaPlugin#onCommand(CommandSender, Command, String, String[])}
	 * and returns the frameworks
	 * {@link CommandFramework#handleCommand(CommandSender, String, Command, String[])}
	 * so the framework can handler all the commands in the plugin.
	 * 
	 * @since ProjectParty-0.0.1-SNAPSHOT
	 * 
	 * @author 598Johnn897
	 * 
	 * @param sender
	 *            The sender of the command
	 * @param cmd
	 *            The command
	 * @param label
	 *            The commands label
	 * @param args
	 *            The arguments supplied with the command
	 *           	  
	 * @see JavaPlugin#onCommand(CommandSender, Command, String, String[])
	 * @see CommandFramework#handleCommand(CommandSender, String, Command,
	 *      String[])
	 */
	@Override public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) 
	{
		// !--- Handle the command with the framework ---! //
		return cmdFramework.handleCommand(sender, label, cmd, args);
	}
	
	/**
	 * Simple thing to log a message to the 
	 * console. 
	 * 
	 * @since ProjectParty-0.0.1-SNAPSHOT
	 * 
	 * @author 598Johnn897
	 * 
	 * @see Logger#log(Level, String)
	 * 
	 * @param msg 
	 * 			The message to log to the console
	 */
	private final void log(String msg)
	{
		logger.log(Level.INFO, msg);
	}
	
	/**
	 * Simple way to log to the console. Uses
	 * {@link String#format(String, Object...)} to
	 * replace the objects.
	 * 
	 * @since ProjectParty-0.0.1-SNAPSHOT
	 * 
	 * @author 598Johnn897
	 * 
	 * @param msg
	 * 			The message to log to the console
	 * @param etc
	 * 			The Objects to use in the log message
	 * 
	 * @see Logger#log(Level, String)
	 * @see String#format(String, Object...)
	 */
	private final void log(String msg, Object... etc)
	{
		logger.log(Level.INFO, String.format(msg, etc));
	}
	
	/**
	 * Logs a warning to the console in
	 * case something goes wrong :).
	 * 
	 * @since ProjectParty-0.0.1-SNAPSHOT
	 * 
	 * @author 598Johnn897
	 * 
	 * @param msg
	 * 			The message to log to the console.
	 * 
	 * @see Level#WARNING
	 * @see Logger#log(Level, String)
	 */
	private final void logWarn(String msg)
	{
		logger.log(Level.WARNING, msg);
	}
	
	/**
	 * Logs a warning to the console using
	 * {@link String#format(String, Object...)} to
	 * replace the objects in the message.
	 * 
	 * @since ProjectParty-0.0.1-SNAPSHOT
	 * 
	 * @author 598Johnn897
	 * 
	 * @param msg
	 * 			The message to log to the console.
	 * @param etc
	 * 			The objects to be replaced in the message.
	 * 
	 * @see String#format(String, Object...)
	 * @see Level#WARNING
	 * @see Logger#log(Level, String)
	 */
	private final void logWarn(String msg, String... etc)
	{
		logger.log(Level.WARNING, String.format(msg, etc));
	}
	
	/**
	 * Logs an error to the console.
	 * 
	 * @since ProjectParty-0.0.1-SNAPSHOT
	 * 
	 * @author 598Johnn897
	 * 
	 * @param msg
	 * 			The message to log to the console
	 * 
	 * @see Level#SEVERE
	 * @see Logger#log(Level, String)
	 */
	private final void logError(String msg)
	{
		logger.log(Level.SEVERE, msg);
	}
	
	/**
	 * Log an error to the console. Uses
	 * {@link String#format(String, Object...)} to
	 * replaces the objects in the message.
	 * 
	 * @since ProjectParty-0.0.1-SNAPSHOT
	 * 
	 * @author 598Johnn897
	 * 
	 * @param msg
	 * 			The message to log to the console.
	 * @param etc
	 * 			The Objects to be replaced in the message
	 */
	private final void logError(String msg, String... etc)
	{
		logger.log(Level.SEVERE, String.format(msg, etc));
	}
	
	/**
	 * Sends a debug message to the console. Sends
	 * the message with a [DEBUG] tag on it.
	 * 
	 * @since ProjectParty-0.0.1-SNAPSHOT
	 * 
	 * @author 598Johnn897
	 * 
	 * @param msg
	 * 			The message to log to the console.
	 * 
	 * @see Logger#log(Level, String)
	 * @see Level#INFO
	 */
	private final void debug(String msg)
	{
		logger.log(Level.INFO, "[DEBUG] " + msg);
	}
	
	/**
	 * Sends a debug message to the console. Sends
	 * the message with a [DEBUG] tag on it. Replaces
	 * the objects in the defined place in the string with 
	 * {@link String#format(String, Object...)}
	 * 
	 * @since ProjectParty-0.0.1-SNAPSHOT
	 * 
	 * @author 598Johnn897
	 * 
	 * @param msg
	 * 			The message to log to the console.
	 * 
	 * @param etc
	 * 			The objects to replace in the string
	 * 
	 * @see Logger#log(Level, String)
	 * @see Level#INFO
	 */
	private final void debug(String msg, String... etc)
	{
		logger.log(Level.INFO, "[DEBUG] " + String.format(msg, etc));	
	}
	
}
