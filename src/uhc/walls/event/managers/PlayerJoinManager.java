/*
 * Author: 598Johnn897
 * 
 * Date: Nov 8, 2014
 * Package: fl.hypixel.event.managers
 *
 */
package uhc.walls.event.managers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import uhc.walls.event.HypixelAPIAccess;
import uhc.walls.event.util.ColorUtil;
import uhc.walls.event.util.TagUtil;

/**
 * 
 * <p>
 * <b>------------------------</b>
 * <p>
 * Created: Nov 8, 2014 <br>
 * Time: 7:08:11 PM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: FallenLegendsEvent <br>
 * File: PlayerJoinManager.java <br>
 * Package: fl.hypixel.event.managers
 * <p>
 * 
 * @author 598Johnn897
 */
public class PlayerJoinManager implements Listener {

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {

	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (event.getPlayer().getName().equals("DualSchix")) {
			TagUtil.setTagPrefix(event.getPlayer(),
					ColorUtil.formatColors("<green>[MOOSE<gold>+<green>] "));
		} else if (event.getPlayer().getName().equals("598Johnn897")
				|| event.getPlayer().getName().equals("TeaCup18")) {
			TagUtil.setTagPrefix(event.getPlayer(),
					ColorUtil.formatColors("<aqua>[LLAMA<red>+<aqua>] "));
		} else if (event.getPlayer().getName().equals("ohyeah1357")) {
			TagUtil.setTagPrefix(event.getPlayer(),
					ColorUtil.formatColors("<green>[POTATO<gold>+<green>] "));
		} else if (event.getPlayer().getName().equals("YahooYoshi")) {
			TagUtil.setTagPrefix(event.getPlayer(),
					ColorUtil.formatColors("<aqua>[RAGER] "));
		} else if (event.getPlayer().getName().equals("cookiefrendz529")) {
			TagUtil.setTagPrefix(event.getPlayer(),
					ColorUtil.formatColors("<aqua>[COOKIE<red>+<aqua>] "));
		} else if (event.getPlayer().getName().equals("_ben_is_awesome_")) {
			TagUtil.setTagPrefix(event.getPlayer(), "<aqua>[NUBB<red>+<aqua>] ");
		} else {
			switch (HypixelAPIAccess.getHypixelRank(event.getPlayer())
					.replaceAll("\"", "")) {
			case "DEFAULT":
				TagUtil.setTagPrefix(event.getPlayer(),
						ColorUtil.formatColors("<gray>"));
				break;
			case "VIP":
				TagUtil.setTagPrefix(event.getPlayer(),
						ColorUtil.formatColors("<green>[VIP] "));
				break;
			case "VIP_PLUS":
				TagUtil.setTagPrefix(event.getPlayer(),
						ColorUtil.formatColors("<green>[VIP<gold>+<green>] "));
				break;
			case "MVP":
				TagUtil.setTagPrefix(event.getPlayer(),
						ColorUtil.formatColors("<aqua>[MVP] "));
				break;
			case "MVP_PLUS":
				TagUtil.setTagPrefix(event.getPlayer(),
						ColorUtil.formatColors("<aqua>[MVP<red>+<aqua>] "));
				break;
			default:
				TagUtil.setTagPrefix(event.getPlayer(),
						ColorUtil.formatColors("<gray>"));
			}
		}
	}

}
