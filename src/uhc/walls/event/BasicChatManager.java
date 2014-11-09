/*
 * Author: 598Johnn897
 * 
 * Date: Oct 28, 2014
 * Package: fl.hypixel.event
 *
 */
package uhc.walls.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import uhc.walls.event.util.ColorUtil;

/**
 * Manages chat for the server
 * <p>
 * <b>------------------------</b>
 * <p>
 * Created: Oct 28, 2014 <br>
 * Time: 3:25:26 PM <br>
 * Year: 2014 <p>
 *
 * By: 598Johnn897 <p>
 * 
 * Project: Fallen Legends Guild Event Server Plugin <br>
 * File: BasicChatManager.java <br>
 * Package: fl.hypixel.event <p>
 * 
 * @author 598Johnn897
 */
public class BasicChatManager implements Listener 
{
	
	/**
	 * The chat event
	 * 
	 * @author 598Johnn897
	 * 
	 * @since 0.0.1-SNAPSHOT
	 * 
	 * @param event The chat event variable
	 */
	@EventHandler public void onChat(AsyncPlayerChatEvent event)
	{
		String doemessage = event.getMessage().replaceAll("(i?)doe", "female deer");
		event.setMessage(doemessage);
		
		event.setFormat(ColorUtil.formatColors("%s<gray>: <white>%s"));
		
		
	}
}
