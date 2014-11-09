/*
 * Author: 598Johnn897
 * 
 * Date: Nov 9, 2014
 * Package: uhc.walls.event
 *
 */
package uhc.walls.event;

import java.util.UUID;

import org.bukkit.entity.Player;

import net.hypixel.api.HypixelAPI;
import net.hypixel.api.reply.PlayerReply;
import net.hypixel.api.util.Callback;

/**
 * 
 * <p>
 * <b>------------------------</b>
 * <p>
 * Created: Nov 9, 2014 <br>
 * Time: 3:13:54 PM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: FallenLegendsEvent <br>
 * File: HypixelAPIAccess.java <br>
 * Package: uhc.walls.event
 * <p>
 * 
 * @author 598Johnn897
 */
public class HypixelAPIAccess {

	private static String rank;
	
	public static String getHypixelRank(Player player) {
		
		HypixelAPI.getInstance().setApiKey(
				UUID.fromString("7232b736-46bc-4e32-8024-201d19d6bbfa"));
		
		HypixelAPI.getInstance().getPlayer(player.getName(), null,
				new Callback<PlayerReply>(PlayerReply.class) {
					@Override
					public void callback(Throwable failCause, PlayerReply result) {
						if (failCause != null) {
							failCause.printStackTrace();
						} else {
							rank = result.getPlayer().get("rank").toString();
						}
						HypixelAPI.getInstance().finish();
					}
				});
		return rank;
	}

}
