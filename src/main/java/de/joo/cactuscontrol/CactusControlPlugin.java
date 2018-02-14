package de.joo.cactuscontrol;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Johannes on 03.02.2018.
 */
public class CactusControlPlugin extends JavaPlugin {
    private Map<Location, Integer> counterMap = new HashMap<>();

    @Override
    public void onEnable() {
        getCommand("cactuscontrol").setExecutor(new CactusCommand(this));
        getServer().getPluginManager().registerEvents(new CactusEventListener(this), this);
    }

    public void incrementLocation(Location location) {
        if(counterMap.containsKey(location)) {
            counterMap.put(location, counterMap.get(location)+1);
        } else {
            counterMap.put(location, 1);
        }
    }

    public Map<Location, Integer> getCounterMap() {
        return this.counterMap;
    }
}
