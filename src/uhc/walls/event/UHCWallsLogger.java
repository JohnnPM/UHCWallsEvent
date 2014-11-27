package uhc.walls.event;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;

/**
 * The UHCWalls logger for when the plugin logs <br>
 * to the console there is a prefix included with <br>
 * it so it is understood that the message came from <br>
 * the plugin. <br>
 * <url>http://hypixel.net/threads/event-uhc-walls.209966/</url> <p>
 * Made for the UHC Walls event, Hope to see you there! <br>
 * - Johnn
 * 
 * @author 598Johnn897
 * @since UHCWalls-0.0.1-SNAPSHOT
 * @see Logger
 */
public class UHCWallsLogger extends Logger
{
    private String name;

    /**
     * The UHCWalls logger for when the plugin logs <br>
     * to the console there is a prefix included with <br>
     * it so it is understood that the message came from <br>
     * the plugin. <br>
     * <url>http://hypixel.net/threads/event-uhc-walls.209966/</url> <p>
     * Made for the UHC Walls event, Hope to see you there! <br>
     * - Johnn <p>
     * From Bukkit API 1.7.10
     * 
     * @author 598Johnn897
     * @since UHCWalls-0.0.1-SNAPSHOT
     * @see Logger
     * @param context the plugin the logger is for
     */
    public UHCWallsLogger(Plugin context) 
    {
        super(context.getClass().getCanonicalName(), null);
        String prefix = context.getDescription().getPrefix();
        name =  prefix != null 
        		? new StringBuilder().append("[").append(prefix).append("] ").toString() 
        				: "[" + context.getDescription().getName() + "] ";
        setParent(context.getServer().getLogger());
        setLevel(Level.ALL);
    }

    @Override public void log(LogRecord logRecord) 
    {
        logRecord.setMessage(name + logRecord.getMessage());
        super.log(logRecord);
    }
}
