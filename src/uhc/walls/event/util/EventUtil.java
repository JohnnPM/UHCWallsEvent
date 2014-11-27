/*
 * Author: 598Johnn897
 * 
 * Date: Aug 17, 2014
 * Package: us.project.party.util
 *
 */
package uhc.walls.event.util;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Utility class for events.
 * <p>
 * Created: Aug 17, 2014 <br>
 * Time: 5:58:31 PM <br>
 * Year: 2014
 * <p>
 * Project: ProjectParty <br>
 * File: EventUtil.java <br>
 * Package: us.project.party.util
 * 
 * @since ProjectParty-0.0.1-SNAPSHOT
 * 
 * @author 598Johnn897
 */
public class EventUtil {

	/**
	 * Searches all the files in the project using
	 * {@link ClassEnumerator#getClassesFromThisJar(Object)} and if the class
	 * implements {@link Listener} it will register using PluginManager's
	 * registerEvents.
	 * 
	 * @since ProjectParty-0.0.1-SNAPSHOT
	 * 
	 * @author 598Johnn897
	 * 
	 * @see ClassEnumerator#getClassesFromThisJar(Object)
	 * @see Bukkit#getPluginManager()
	 * 
	 * @param plugin
	 *            The plugin to register the events with.
	 */
	public static void registerEvents(Plugin plugin) {
		Class<?>[] classes = ClassEnumerator.getInstance()
				.getClassesFromThisJar(plugin);
		if (classes == null || classes.length == 0) {
			return;
		}
		for (Class<?> c : classes) {
			try {
				if (Listener.class.isAssignableFrom(c) && !c.isInterface()
						&& !c.isEnum() && !c.isAnnotation()) {
					if (JavaPlugin.class.isAssignableFrom(c)) {
						if (plugin.getClass().equals(c)) {
							Bukkit.getPluginManager().registerEvents(
									(Listener) plugin, plugin);
						}
					} else {
						Bukkit.getPluginManager().registerEvents(
								(Listener) c.newInstance(), plugin);
					}
				}
			} catch (InstantiationException e) {
				plugin.getLogger().log(
						Level.INFO,
						c.getSimpleName()
								+ " does not use the default constructor.");
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				plugin.getLogger().log(
						Level.INFO,
						c.getSimpleName()
								+ " does not use the default constructor.");
				e.printStackTrace();
			}
		}
	}
}
