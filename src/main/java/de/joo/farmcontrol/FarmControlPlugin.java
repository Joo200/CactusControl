package de.joo.farmcontrol;

import de.joo.farmcontrol.Commands.CactusCommand;
import de.joo.farmcontrol.Commands.FishCommand;
import de.joo.farmcontrol.Listener.CactusEventListener;
import de.joo.farmcontrol.Listener.FishEventListener;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class FarmControlPlugin extends JavaPlugin {
    private Map<Location, Integer> counterMapCactus = new HashMap<>();
    private Map<Location, Integer> counterMapFish = new HashMap<>();

    @Override
    public void onEnable() {
        getCommand("cactuscontrol").setExecutor(new CactusCommand(this));
        getCommand("fishcontrol").setExecutor(new FishCommand(this));
        getServer().getPluginManager().registerEvents(new CactusEventListener(this), this);
        getServer().getPluginManager().registerEvents(new FishEventListener(this), this);
    }

    public void incrementCactusLocation(Location location) {
        counterMapCactus.entrySet().stream().filter(entry -> location.distance(entry.getKey()) <= 20).findFirst().ifPresentOrElse(entry -> entry.setValue(entry.getValue()+1), () -> counterMapCactus.put(location, 1));
    }

    public Map<Location, Integer> getCactusCounterMap() {
        return this.counterMapCactus;
    }

    public void incrementFishLocation(Location location) {
        if(counterMapFish.containsKey(location)) {
            counterMapFish.put(location, counterMapFish.get(location)+1);
        } else {
            counterMapFish.put(location, 1);
        }
    }

    public Map<Location, Integer> getFishCounterMap() {
        return this.counterMapFish;
    }
}
