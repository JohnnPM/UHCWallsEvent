/*
 * Author: 598Johnn897
 * 
 * Date: Nov 25, 2014
 * Package: uhc.walls.event.hypixel
 *
 */
package uhc.walls.event.hypixel;

import java.util.UUID;

import net.hypixel.api.HypixelAPI;
import net.hypixel.api.reply.PlayerReply;
import net.hypixel.api.util.Callback;
import net.minecraft.util.com.google.gson.JsonObject;
import uhc.walls.event.lib.References;

/**
 * Uses the hypixel public api to get data <br>
 * for the UHC Walls event plugin. <br>
 * <url>http://hypixel.net/threads/event-uhc-walls.209966/</url> <br>
 * 
 * @author 598Johnn897
 * @since UHCWalls-0.0.1-SNAPSHOT
 * @see HypixelAPI
 */
public class HypixelAccessor
{
	private static HypixelAccessor instance;
	public static HypixelAccessor get()
	{
		if(instance == null) instance = new HypixelAccessor();
		return instance;
	}
	
	private JsonObject playerData;

	public JsonObject getPlayerData()
	{ 
		HypixelAPI.getInstance()
			.setApiKey(UUID.fromString(References.HYPIXEL_API_KEY));
		
		HypixelAPI.getInstance().getPlayer("598Johnn897",
				null,
				new Callback<PlayerReply>(PlayerReply.class)
				{
					@Override
					public void callback(Throwable failCause, PlayerReply result)
					{
						if (failCause != null)
						{
							failCause.printStackTrace();
						} else
						{
							playerData = result.getPlayer();
						}
						HypixelAPI.getInstance().finish();
					}
				});

		return playerData;
	}
}
