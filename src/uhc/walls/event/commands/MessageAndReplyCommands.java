/*
 * Author: 598Johnn897
 * 
 * Date: Nov 8, 2014
 * Package: fl.hypixel.event.commands
 *
 */
package uhc.walls.event.commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import uhc.walls.event.commands.CommandFramework.Command;
import uhc.walls.event.commands.CommandFramework.CommandArgs;
import uhc.walls.event.commands.CommandFramework.CommandListener;
import uhc.walls.event.util.ColorUtil;

/**
 * 
 * <p>
 * <b>------------------------</b>
 * <p>
 * Created: Nov 8, 2014 <br>
 * Time: 8:23:01 PM <br>
 * Year: 2014
 * <p>
 * 
 * By: 598Johnn897
 * <p>
 * 
 * Project: FallenLegendsEvent <br>
 * File: MessageAndReplyCommands.java <br>
 * Package: fl.hypixel.event.commands
 * <p>
 * 
 * @author 598Johnn897
 */
public class MessageAndReplyCommands implements CommandListener {

	private HashMap<String, String> replyList = new HashMap<String, String>();

	@Command(command = "msg", aliases = { "tell", "w", "whisper", "message",
			"pm", "dm" })
	public void msg(CommandArgs info) {
		if (info.isPlayer()) {
			Player player = info.getPlayer();
			if (info.getArgs().length == 0) {
				player.sendMessage(ColorUtil
						.formatColors("<red>Invalid Usage! /msg <player> <message...>"));
			} else if (info.getArgs().length == 1) {
				player.sendMessage(ColorUtil
						.formatColors("<red>No message? /msg <player> <message...>"));
			} else if (info.getArgs().length >= 1) {
				Player playerTo = Bukkit.getPlayer(info.getArgs()[0]);

				if (playerTo != null
						&& !playerTo.getName().equals(player.getName())) {
					playerTo.sendMessage(ColorUtil
							.formatColors("<pink>From <gray>"
									+ info.getPlayer().getDisplayName()
									+ "<gray>: " + info.getFinalArg(1)));
					player.sendMessage(ColorUtil.formatColors("<pink>To "
							+ playerTo.getName() + "<gray>: "
							+ info.getFinalArg(1)));

					replyList.put(player.getName(), playerTo.getName());
					replyList.put(playerTo.getName(), player.getName());
				} else {
					player.sendMessage(ColorUtil
							.formatColors("<red>Player is not online!"));
				}
			}
		} else {
			info.getSender().sendMessage("too lazy to allow console to do it");
		}
	}

	@Command(command = "r", aliases = { "reply" })
	public void reply(CommandArgs info) {
		if (info.isPlayer()) {
			if (info.getArgs().length >= 1) {
				Player player = info.getPlayer();

				Player playerTo = Bukkit.getPlayer(replyList.get(player
						.getName()));

				if (playerTo != null) {
					player.sendMessage(ColorUtil.formatColors("<pink>To "
							+ playerTo.getName() + "<gray>: "
							+ info.getFinalArg(0)));
					playerTo.sendMessage(ColorUtil
							.formatColors("<pink>From <gray>"
									+ player.getDisplayName() + "<gray>: "
									+ info.getFinalArg(0)));
				} else {
					player.sendMessage(ColorUtil
							.formatColors("<red>There is no one to reply to!"));
				}
			} else {
				info.getPlayer().sendMessage(ColorUtil.formatColors("<red>Invalid Usage! /r <message..>"));
			}
		} else {
			info.getSender().sendMessage("too lazy to allow console to do it");
		}
	}
}
