/*
 * Author: 598Johnn897
 * 
 * Date: Nov 8, 2014
 * Package: fl.hypixel.event
 *
 */
package uhc.walls.event;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;

/**
 * The logger instance for the plugin.
 * <p>
 * <b>------------------------</b>
 * <p>
 * Created: Nov 8, 2014 <br>
 * Time: 1:10:27 PM <br>
 * Year: 2014 <p>
 *
 * By: 598Johnn897 <p>
 * 
 * Project: FallenLegendsEvent <br>
 * File: FLEventLogger.java <br>
 * Package: fl.hypixel.event <p>
 * 
 * @author 598Johnn897
 */
public class FLEventLogger extends Logger
{
    private String pluginName;

    /**
     * Creates a new PluginLogger that extracts the name from a plugin.
     *
     * @param context A reference to the plugin
     * 
     * @since ProjectParty-0.0.1-SNAPSHOT
     */
    public FLEventLogger(Plugin context) {
        super(context.getClass().getCanonicalName(), null);
        String prefix = context.getDescription().getPrefix();
        pluginName = 
        		prefix != null 
        		? new StringBuilder().append("[").append(prefix).append("] ").toString() 
        				: "[" + context.getDescription().getName() + "] ";
        setParent(context.getServer().getLogger());
        setLevel(Level.ALL);
    }

    @Override
    public void log(LogRecord logRecord) {
        logRecord.setMessage(pluginName + logRecord.getMessage());
        super.log(logRecord);
    }

}
