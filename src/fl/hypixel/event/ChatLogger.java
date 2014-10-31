/*
 * Author: 598Johnn897
 * 
 * Date: Aug 17, 2014
 * Package: us.project.party.statistics
 *
 */
package fl.hypixel.event;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fl.hypixel.event.lib.References;
import fl.hypixel.event.util.FileUtil;

/**
 * Chat Statistics for project Party
 * <p>
 * Created: Aug 17, 2014 <br>
 * Time: 9:14:22 PM <br>
 * Year: 2014
 * <p>
 * Project: ProjectParty <br>
 * File: ChatStatistics.java <br>
 * Package: us.project.party.statistics
 * 
 * @since ProjectParty-0.0.1-SNAPSHOT
 * 
 * @author 598Johnn897
 */
public class ChatLogger implements Listener 
{

	private static FLEventMain plugin = FLEventMain.get();
	
	/**
	 * Chat is temporaily stored inside of an arraylist 
	 * until the plugin is disabled then it is
	 * written to file with <code>writeToFile()</code>
	 * 
	 * @since ProjectParty-0.0.1-SNAPSHOT
	 * 
	 * @author 598Johnn897
	 */
	private static ArrayList<String> chat = new ArrayList<String>();
	{
		chat.ensureCapacity(1000000);
	}

	/**
	 * The chat event listener...
	 * 
	 * @since ProjectParty-0.0.1-SNAPSHOT
	 * 
	 * @author 598Johnn897
	 * 
	 * @see EventHandler
	 * @see AsyncPlayerChatEvent
	 */
	@EventHandler public void onChat(AsyncPlayerChatEvent event) 
	{
		Player player = event.getPlayer();
		String message = event.getMessage();
		
		saveToMap(player, message);
	}

	/**
	 * Saves the chat message to the map {@link #chat}
	 * 
	 * @since ProjectParty-0.0.1-SNAPSHOT
	 * 
	 * @author 598Johnn897
	 * 
	 * @see HashMap
	 */
	private void saveToMap(Player player, String message) 
	{
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("h:mm:ss a");
		String formattedDate = format.format(date);
		
		chat.add("["+ formattedDate + "]: " + player.getName() + ": " + message);
	}

	/**
	 * This write the chat logs stored in the {@link #chat} ArrayList to a file
	 * of the current date. Then after it finishes it will clear the ArrayList
	 * {@link #chat};
	 * 
	 * @since ProjectParty-0.0.1-SNAPSHOT
	 * 
	 * @author 598Johnn897
	 * 
	 * @see File
	 * @see Date
	 * @see SimpleDateFormat 
	 */
	public static void writeToFile() 
	{
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("MMMM-dd-yyyy");
		String dateString = format.format(date);

		File file = new File(FLEventMain.get().getDataFolder() + References.CHAT_FOLDER,
				"chat-" + dateString + References.CHAT_FILE_EXT);
		File chatDir = new File(plugin.getDataFolder() + References.CHAT_FOLDER);

		try 
		{
			if (!chatDir.exists()) chatDir.mkdir();

			if (!file.exists()) file.createNewFile();

			for (String s : chat) FileUtil.writeTo(file, s);

		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			chat.clear();
		}
	}
}
